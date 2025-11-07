package com.miller.controller.testcase;

import com.miller.common.util.StringToListUtils;
import com.miller.entity.constant.RunTeatCaseTypeEnum;
import com.miller.entity.platform.req.RunJmeterCaseReq;
import com.miller.entity.platform.req.TestCaseRunScenarioReq;
import com.miller.entity.report.resp.ConfigBasicRespDTO;
import com.miller.entity.util.Response;
import com.miller.service.report.ConfigService;
import com.miller.service.testcase.TestCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
            List<String> newList = StringToListUtils.stringToList(req.getPackageNameList().get(0));


            runTestCaseULID = testCaseService.runTestCase(newList, RunTeatCaseTypeEnum.DEBUG);
        } catch (Exception e) {
            return Response.fail("runTestCase error:" + e.getMessage());
        }

        return Response.success(String.valueOf(runTestCaseULID));
    }

    /**
     * 执行java用例
     * @param req
     * @return
     */
    @PostMapping("/runJavaCase")
    public Response<String> runCase(@RequestBody TestCaseRunScenarioReq req) {
        List<String> packageNameList = req.getPackageNameList();
        if (null == packageNameList || packageNameList.isEmpty()) {
            return Response.fail("packageName is empty");
        }
        String runTestCaseULID;
        try {
            List<String> newList = StringToListUtils.stringToList(req.getPackageNameList().get(0));

            runTestCaseULID = testCaseService.runTestCase(newList, RunTeatCaseTypeEnum.PLATFORM);
        } catch (Exception e) {
            return Response.fail("runTestCase error:" + e.getMessage());
        }

        return Response.success(String.valueOf(runTestCaseULID));
    }

    /**
     * 获取java用例执行包列表
     * @return
     */
    @GetMapping("/getJavaCasePackageList")
    public Response<List<?>> getCasePackageList() {

        List<ConfigBasicRespDTO> packageList = configService.getExecutionCasePackageList();


        return Response.success(packageList);
    }


    /**
     * 调用jmeter执行用例
     * @param runJmeterCaseReq
     * @return
     */
    @PostMapping("/runJmeterCase")
    public Response<String> runJmeterCase(@RequestBody RunJmeterCaseReq runJmeterCaseReq) {

        return Response.success(runJmeterCaseReq.getCountry());
    }

}
