package com.miller.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.common.util.ULIDUtils;
import com.miller.entity.TestCaseEntity;
import com.miller.mapper.TestCaseMapper;
import com.miller.service.TestCaseService;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.clz.ClassFindService;
import com.miller.service.framework.launcher.TestCaseRunnerLauncher;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;

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
     * @param packageName 包名
     * @return 测试执行计划ID，通过ID查询测试结果
     */
    @Override
    public String runTestCase(String packageName) {
        syncRunTestCase(packageName);
        // TODO 通过ID查询测试结果需要落库
        return ULIDUtils.generateULID();
    }

    /**
     * 异步执行测试用例
     *
     * @param packageName 包名
     */
    private synchronized void syncRunTestCase(String packageName) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<SummaryGeneratingListener> callable = () -> {
            log.info("进入 Callable 的 call 方法");
            // 获取包下所有类, 通过注解过滤，需要执行的测试用例
            List<Class<?>> packageClass = classFindService.getPackageClass(packageName)
                    .stream().filter(clz -> clz.isAnnotationPresent(Scenario.class)).toList();

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
        Future<SummaryGeneratingListener> future = executorService.submit(callable);
        log.info("主线程继续执行");
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














