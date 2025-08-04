package com.miller.controller.testcase;

import com.miller.entity.platform.req.TestCaseRunScenarioReq;
import com.miller.service.testcase.TestCaseService;
import com.miller.service.framework.launcher.TestCaseRunnerLauncher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/runScenario")
    public String runScenario(@RequestBody TestCaseRunScenarioReq req) {
        List<String> packageNameList = req.getPackageNameList();
        if (null == packageNameList || packageNameList.isEmpty()) {
            return "packageName is empty.";
        }
        String runTestCaseULID = testCaseService.runTestCase(packageNameList);

        return String.valueOf(runTestCaseULID);
    }

    /**
     * 测试非Spring环境下执行测试用例。
     */
    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        selectPackage("com.miller.userapp.module.pay.suite")
                ).filters(
//                        includeClassNamePatterns(".*Scenario[s]?Test[s]?")
                        includeClassNamePatterns(".*Test[s]?")
                ).build();
        new TestCaseRunnerLauncher().executeRequest(request);

    }
}
