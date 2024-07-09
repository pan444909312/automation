package com.miller.controller;

import com.miller.service.TestCaseService;
import com.miller.service.framework.launcher.TestCaseRunnerLauncher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

/**
 * 测试用例管理接口
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/3 11:08:44
 */
@Slf4j
@RestController
@RequestMapping("/testCase")
public class TestCaseController {
    @Autowired
    private TestCaseService testCaseService;

    @GetMapping("/runScenario")
    public String debugScenarios(@RequestParam(value = "packageName", required = false) String packageName) {
        if (null == packageName || StringUtils.isBlank(packageName)) {
            return "packageName is empty.";
        }
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        selectPackage(packageName)
                ).filters(
                        includeClassNamePatterns(".*Scenario[s]?Test[s]?")
                ).build();
        TestCaseRunnerLauncher.executeRequest(request);
        return "success";
    }

    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        selectPackage("com.miller.takeaway.order.branch.settlement")
                ).filters(
                        includeClassNamePatterns(".*Scenario[s]?Test[s]?")
                ).build();
        TestCaseRunnerLauncher.executeRequest(request);
    }
}
