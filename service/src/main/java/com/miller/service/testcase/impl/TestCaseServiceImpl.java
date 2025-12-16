package com.miller.service.testcase.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.constant.ExecutionStatusEnum;
import com.miller.entity.constant.ExecutionTypeEnum;
import com.miller.entity.constant.ProjectTypeEnum;
import com.miller.entity.constant.RunTeatCaseTypeEnum;
import com.miller.entity.report.resp.AutoCaseExecutionDailyDataDTO;
import com.miller.entity.report.resp.AutoCaseExecutionDailySummaryDTO;
import com.miller.entity.testcase.TestCaseEntity;
import com.miller.mapper.tesetcase.TestCaseMapper;
import com.miller.service.framework.notification.dingtalk.DingTalkUtils;
import com.miller.service.report.AutoExecutionRecordService;
import com.miller.service.testcase.TestCaseService;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.clz.ClassFindService;
import com.miller.service.framework.launcher.TestCaseRunnerLauncher;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/3 11:23:37
 */
@Slf4j
@Service
public class TestCaseServiceImpl extends ServiceImpl<TestCaseMapper, TestCaseEntity> implements TestCaseService {
    @Autowired
    private TestCaseMapper testCaseMapper;
    @Autowired
    private ClassFindService classFindService;

    @Autowired
    private TestCaseRunnerLauncher testCaseRunnerLauncher;

    @Autowired
    private AutoExecutionRecordService autoExecutionRecordService;

    /**
     * 运行测试用例
     *
     * @param packageNameList 包名列表
     * @return 测试执行计划ID，通过ID查询测试结果
     */
    @Override
    public String runTestCase(List<String> packageNameList, RunTeatCaseTypeEnum runTeatCaseType) {
        // 执行用例
        TestExecutionSummary summary = syncRunTestCase(packageNameList);

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderSuccess = new StringBuilder();
        long costTime = (summary.getTimeFinished() - summary.getTimeStarted()) / 1000;
        long testsFoundCount = summary.getTestsFoundCount();
        long testsSucceededCount = summary.getTestsSucceededCount();
        long testsFailedCount = summary.getTestsFailedCount();
        long testsSkippedCount = summary.getTestsSkippedCount() + summary.getTestsAbortedCount();

        double passRate = Math.round(((double) testsSucceededCount / testsFoundCount) * 100 * 100) / 100.0;

        if (runTeatCaseType.getCode() == RunTeatCaseTypeEnum.TASK.getCode()) {
            // 按人员维度发送钉钉通知报告
            DingTalkUtils.sendMarkdownMessage("自动化执行通知", messageHandler());

//            stringBuilder.append("#### C组-自动化定时执行结果汇总").append(" \n ");
//            stringBuilder.append("- **共**: " + testsFoundCount + "个").append(" \n ");
//            stringBuilder.append("- **成功**: " + testsSucceededCount + "个").append(" \n ");
//            stringBuilder.append("- **失败**: " + testsFailedCount + "个 ");
//            if (testsFailedCount > 0) {
//                stringBuilder.append("[查看失败详情](https://automation.hungrypanda.it:2096/#/auto-case/daily-case-summary)").append(" \n ");
//            } else {
//                stringBuilder.append(" \n ");
//            }
//            stringBuilder.append("- **跳过**: " + testsSkippedCount + "个").append(" \n ");
//            stringBuilder.append("- **通过率**: " + passRate + "%").append(" \n ");
//            stringBuilder.append("- **花费时间**: " + costTime + "秒").append(" \n ");
//            // 如果是定时任务执行，发送钉钉通知到主群
//            DingTalkUtils.sendMarkdownMessage("自动化执行通知", stringBuilder.toString());

        }
        if (runTeatCaseType.getCode() == RunTeatCaseTypeEnum.PLATFORM.getCode()) {

            stringBuilder.append("#### C组-自动化平台手动执行结果汇总").append(" \n ");
            stringBuilder.append("- **共**: " + testsFoundCount + "个").append(" \n ");
            stringBuilder.append("- **成功**: " + testsSucceededCount + "个").append(" \n ");
            stringBuilder.append("- **失败**: <font color=red>" + testsFailedCount + "</font>个 ");
            stringBuilder.append("- **跳过**: " + testsSkippedCount + "个").append(" \n ");
            stringBuilder.append("- **通过率**: <font color=green>" + passRate + "%</font>").append(" \n ");
            stringBuilder.append("- **花费时间**: " + costTime + "秒").append(" \n ");
            // 如果是平台手动执行，发送钉钉通知到副群
            DingTalkUtils.sendMarkdownMessageTest("自动化执行通知", stringBuilder.toString());
        }
        if (runTeatCaseType.getCode() == RunTeatCaseTypeEnum.DEBUG.getCode()) {
            {
                stringBuilder.append("#### C组-DEBUG手动执行结果汇总").append(" \n ");
                stringBuilder.append("- **共**: " + testsFoundCount + "个").append(" \n ");
                stringBuilder.append("- **成功**: " + testsSucceededCount + "个").append(" \n ");
                stringBuilder.append("- **失败**: <font color=red> " + testsFailedCount + "</font>个 ");
                stringBuilder.append("- **跳过**: " + testsSkippedCount + "个").append(" \n ");
                stringBuilder.append("- **通过率**: <font color=green>" + passRate + "% </font>").append(" \n ");
                stringBuilder.append("- **花费时间**: " + costTime + "秒").append(" \n ");
                // 如果是调试执行发送钉钉通知到副群
                DingTalkUtils.sendMarkdownMessageTest("自动化执行通知", stringBuilder.toString());
            }

        }
        // todo 执行结果
        return stringBuilder.toString();
    }

    /**
     * 异步执行测试用例
     *
     * @param packageNameList 包名列表
     */
    private synchronized TestExecutionSummary syncRunTestCase(List<String> packageNameList) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<SummaryGeneratingListener> callable = () -> {
            log.info("进入 Callable 的 call 方法");
            // 获取包下所有类, 通过注解过滤，需要执行的测试用例
            List<Class<?>> packageClass = new ArrayList<>();
            packageNameList.forEach(item -> {
                packageClass.addAll(classFindService.getPackageClass(item)
                        .stream().filter(clz -> clz.isAnnotationPresent(Scenario.class)).toList());
            });
            if (packageClass.isEmpty()) {
                throw new RuntimeException("该路径下没有需要执行的测试用例");
            }


            List<DiscoverySelector> discoverySelectorList = packageClass
                    .stream().map(DiscoverySelectors::selectClass).collect(Collectors.toList());

            LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                    .selectors(discoverySelectorList)
                    .filters(
                            // includeClassNamePatterns(".*Scenario[s]?Test[s]?")
                    ).build();

            log.warn("看看扫出来的大小{}", packageClass.size());
            log.warn("看看扫出来的大小{}", discoverySelectorList.size());


            SummaryGeneratingListener summaryGeneratingListener = testCaseRunnerLauncher.executeRequest(request);
            // 获取执行的测试用例数量
            long testsSkippedCount = summaryGeneratingListener.getSummary().getTestsSkippedCount();
            long testsAbortedCount = summaryGeneratingListener.getSummary().getTestsAbortedCount();
            long testsStartedCount = summaryGeneratingListener.getSummary().getTestsStartedCount();
            long testsSucceededCount = summaryGeneratingListener.getSummary().getTestsSucceededCount();
            long testsFoundCount = summaryGeneratingListener.getSummary().getTestsFoundCount();
            long testsFailedCount = summaryGeneratingListener.getSummary().getTestsFailedCount();
            long totalFailureCount = summaryGeneratingListener.getSummary().getTotalFailureCount();
            long timeStarted = summaryGeneratingListener.getSummary().getTimeStarted();
            long timeFinished = summaryGeneratingListener.getSummary().getTimeFinished();
            log.warn("测试用例执行结果,testsSkippedCount:{},testsAbortedCount:{}," +
                            "testsStartedCount:{},testsSucceededCount:{}," +
                            "testsFoundCount:{},testsFailedCount:{},totalFailureCount:{}",
                    testsSkippedCount, testsAbortedCount,
                    testsStartedCount, testsSucceededCount,
                    testsFoundCount, testsFailedCount, totalFailureCount);
            return summaryGeneratingListener;
        };

        log.info("提交 Callable 到线程池");
        System.out.println("##################################################");
        System.out.println(callable);

        Future<SummaryGeneratingListener> future = executorService.submit(callable);
        System.out.println("##################################################");
        System.out.println(future);
        SummaryGeneratingListener summaryGeneratingListener = null;
        try {
            summaryGeneratingListener = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        TestExecutionSummary summary = summaryGeneratingListener.getSummary();
        log.info("主线程继续执行");
        return summary;
//        log.info("主线程等待获取 Future 结果");
        // 主线程调用 future.get() 方法会阻塞自己，直到子任务完成。
        // SummaryGeneratingListener result = future.get();
        // 也可以使用 Future 方法提供的 isDone 方法，它可以用来检查子线程的 task 是否已经完成了
//        while(!future.isDone()) {
//            log.debug("子线程正在执行测试用例...");
//            Thread.sleep(1000);
//        }

        // log.info("主线程获取到 Future 结果: {}", result);

    }


    private String messageHandler() {
        LocalDate todayLocalDate = LocalDate.now();
        Date today = Date.from(todayLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<AutoCaseExecutionDailyDataDTO> autoCaseExecutionDailyDataDTOList = autoExecutionRecordService.getDailyCaseExecutionSummaryByPerson(ProjectTypeEnum.PROJECT_C.getProjectId(), today);


        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("## C组-自动化定时执行结果汇总").append(" \n ");
        // 计算汇总数据
        int sum = autoCaseExecutionDailyDataDTOList.stream()
                .mapToInt(AutoCaseExecutionDailyDataDTO::getCount)
                .sum();
        int successSum = autoCaseExecutionDailyDataDTOList.stream()
                .mapToInt(AutoCaseExecutionDailyDataDTO::getSuccessCount)
                .sum();

        // 添加小组汇总信息
        stringBuilder.append("- **").append("小组").append("**:").append(" \n ");
        stringBuilder.append("  - 共: ").append(sum).append("个").append(" \n ");
        stringBuilder.append("  - 成功: ").append(successSum).append("个").append(" \n ");
        stringBuilder.append("  - 失败: <font color=red>").append(sum - successSum).append("</font>个").append(" \n ");
        double groupPassRate = sum > 0 ? (double) successSum / sum * 100 : 0;
        stringBuilder.append("  - 通过率: <font color=green>").append(String.format("%.2f", groupPassRate)).append("%</font>").append(" \n ");


        for (AutoCaseExecutionDailyDataDTO data : autoCaseExecutionDailyDataDTOList) {
            stringBuilder.append("- **").append(data.getAuthor()).append("**:").append(" \n ");
            stringBuilder.append("  - 共: ").append(data.getCount()).append("个").append(" \n ");
            stringBuilder.append("  - 成功: ").append(data.getSuccessCount()).append("个").append(" \n ");
            stringBuilder.append("  - 失败: <font color=red>").append(data.getFailCount()).append("</font>个").append(" \n ");
            stringBuilder.append("  - 通过率: <font color=green>").append(String.format("%.2f", data.getPassRate() * 100)).append("%</font>").append(" \n ");
        }


        stringBuilder.append("[查看报告详情](https://automation.hungrypanda.it:2096/#/auto-case/daily-case-summary)").append(" \n ");
        stringBuilder.append(" \n ");

        return stringBuilder.toString();
    }

}














