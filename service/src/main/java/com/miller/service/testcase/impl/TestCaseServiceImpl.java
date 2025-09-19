package com.miller.service.testcase.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.testcase.TestCaseEntity;
import com.miller.mapper.tesetcase.TestCaseMapper;
import com.miller.service.framework.notification.dingtalk.DingTalkUtils;
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

import java.util.ArrayList;
import java.util.List;
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

    /**
     * 运行测试用例
     *
     * @param packageNameList 包名列表
     * @return 测试执行计划ID，通过ID查询测试结果
     */
    @Override
    public String runTestCase(List<String> packageNameList,boolean isTask) {
        TestExecutionSummary summary = syncRunTestCase(packageNameList);

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderSuccess = new StringBuilder();
        long costTime = (summary.getTimeFinished() - summary.getTimeStarted()) / 1000;
        long testsFoundCount = summary.getTestsFoundCount();
        long testsSucceededCount = summary.getTestsSucceededCount();
        long testsFailedCount = summary.getTestsFailedCount();
        long testsSkippedCount = summary.getTestsSkippedCount() + summary.getTestsAbortedCount();


        double passRate = Math.round(((double) testsSucceededCount / testsFoundCount) * 100 * 100) / 100.0;
        if (isTask){

            stringBuilder.append("#### 自动化定时执行结果汇总").append(" \n ");
            stringBuilder.append("- **共**: " + testsFoundCount + "个").append(" \n ");
            stringBuilder.append("- **成功**: " + testsSucceededCount + "个").append(" \n ");
            stringBuilder.append("- **失败**: " + testsFailedCount + "个 ");
            if (testsFailedCount > 0) {
                stringBuilder.append("[查看失败详情](https://automation.hungrypanda.it:2096/#/auto-case/daily-case-summary)").append(" \n ");
            }else{
                stringBuilder.append(" \n ");
            }
            stringBuilder.append("- **跳过**: " + testsSkippedCount + "个").append(" \n ");
            stringBuilder.append("- **通过率**: " + passRate + "%").append(" \n ");
            stringBuilder.append("- **花费时间**: " + costTime + "秒").append(" \n ");
            DingTalkUtils.sendMarkdownMessageTest("自动化执行通知", stringBuilder.toString());

            if (testsSucceededCount > 0) {
                stringBuilderSuccess.append("#### 自动化定时执行结果汇总").append(" \n ");
                stringBuilderSuccess.append("- **共**: " + testsSucceededCount + "个").append(" \n ");
                stringBuilderSuccess.append("- **成功**: " + testsSucceededCount + "个").append(" \n ");
                stringBuilderSuccess.append("- **失败**: " + 0 + "个").append(" \n ");
                stringBuilderSuccess.append("- **跳过**: " + 0 + "个").append(" \n ");
                stringBuilderSuccess.append("- **通过率**: " + "100%").append(" \n ");
                stringBuilderSuccess.append("- **花费时间**: " + costTime + "秒").append(" \n ");
                DingTalkUtils.sendMarkdownMessage("自动化执行通知", stringBuilderSuccess.toString());
            }
        }else{
            stringBuilder.append("#### 自动化手动执行结果汇总").append(" \n ");
            stringBuilder.append("- **共**: " + testsFoundCount + "个").append(" \n ");
            stringBuilder.append("- **成功**: " + testsSucceededCount + "个").append(" \n ");
            stringBuilder.append("- **失败**: " + testsFailedCount + "个 ");
            stringBuilder.append("- **跳过**: " + testsSkippedCount + "个").append(" \n ");
            stringBuilder.append("- **通过率**: " + passRate + "%").append(" \n ");
            stringBuilder.append("- **花费时间**: " + costTime + "秒").append(" \n ");
            DingTalkUtils.sendMarkdownMessageTest("自动化执行通知", stringBuilder.toString());
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


            List<DiscoverySelector> discoverySelectorList = packageClass
                    .stream().map(DiscoverySelectors::selectClass).collect(Collectors.toList());

            LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                    .selectors(discoverySelectorList)
                    .filters(
                            // includeClassNamePatterns(".*Scenario[s]?Test[s]?")
                    ).build();
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

}














