package com.miller.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.TestCaseEntity;
import com.miller.mapper.TestCaseMapper;
import com.miller.service.TestCaseService;
import com.miller.service.framework.clz.ClassFindService;
import com.miller.service.framework.launcher.TestCaseRunnerLauncher;
import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;

/**
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/3 11:23:37
 */
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
     * @param packageName 包名
     * @return 测试用例数量
     */
    public Long runTestCase(String packageName) {
        List<DiscoverySelector> discoverySelectorList = classFindService.getPackageClass(packageName)
                .stream().map(DiscoverySelectors::selectClass).collect(Collectors.toList());

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(discoverySelectorList)
                .filters(
                        includeClassNamePatterns(".*Scenario[s]?Test[s]?")
                ).build();
        SummaryGeneratingListener summaryGeneratingListener = testCaseRunnerLauncher.executeRequest(request);
        long testsFoundCount = summaryGeneratingListener.getSummary().getTestsFoundCount();
        return testsFoundCount;

    }

}
