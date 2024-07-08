package com.miller.controller;

import com.miller.service.TestCaseService;
import com.miller.service.framework.launcher.TestCaseRunnerLauncher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

/**
 * 测试用例管理接口
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/3 11:08:44
 */
@RestController
@RequestMapping("/testCase")
public class TestCaseController {
    @Autowired
    private TestCaseService testCaseService;

    @GetMapping("/debugScenarios")
    public void debugScenarios() {
        testCaseService.debugScenarios();

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        selectPackage("com.miller.takeaway.order.branch.settlement")
                ).build();
        TestCaseRunnerLauncher.executeRequest(request);
    }

    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        selectPackage("com.miller.takeaway.order.branch.settlement")
                ).build();
        TestCaseRunnerLauncher.executeRequest(request);
    }
}
