package com.miller.controller.testcase;

import com.miller.entity.constant.RunTeatCaseTypeEnum;
import com.miller.entity.platform.req.TestCaseRunScenarioReq;
import com.miller.entity.report.ConfigEntity;
import com.miller.entity.report.resp.ConfigBasicRespDTO;
import com.miller.entity.util.Response;
import com.miller.service.report.ConfigService;
import com.miller.service.testcase.TestCaseService;
import com.miller.service.framework.launcher.TestCaseRunnerLauncher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
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

    @Autowired
    private ConfigService configService;

    @PostMapping("/runScenario")
    public Response<String> runCaseDebug(@RequestBody TestCaseRunScenarioReq req) {
        List<String> packageNameList = req.getPackageNameList();
        if (null == packageNameList || packageNameList.isEmpty()) {
            return Response.fail("packageName is empty");
        }
        String runTestCaseULID;
        try {

            runTestCaseULID = testCaseService.runTestCase(packageNameList, RunTeatCaseTypeEnum.DEBUG);
        } catch (Exception e) {
            return Response.fail("runTestCase error:" + e.getMessage());
        }

        return Response.success(String.valueOf(runTestCaseULID));
    }

    @PostMapping("/runCase")
    public Response<String> runCase(@RequestBody TestCaseRunScenarioReq req) {
        List<String> packageNameList = req.getPackageNameList();
        if (null == packageNameList || packageNameList.isEmpty()) {
            return Response.fail("packageName is empty");
        }
        String runTestCaseULID;
        try {

            runTestCaseULID = testCaseService.runTestCase(packageNameList, RunTeatCaseTypeEnum.PLATFORM);
        } catch (Exception e) {
            return Response.fail("runTestCase error:" + e.getMessage());
        }

        return Response.success(String.valueOf(runTestCaseULID));
    }

    @GetMapping("/getCasePackageList")
    public Response<List<?>> getCasePackageList() {

        List<ConfigBasicRespDTO> packageList = configService.getExecutionCasePackageList();


        return Response.success(packageList);
    }

}
