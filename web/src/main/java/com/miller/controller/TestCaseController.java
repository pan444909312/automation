package com.miller.controller;

import com.miller.service.TestCaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String runScenario(@RequestParam(value = "packageName") String packageName) {
        if (null == packageName || StringUtils.isBlank(packageName)) {
            return "packageName is empty.";
        }
        Long testsFoundCount = testCaseService.runTestCase(packageName);

        return String.valueOf(testsFoundCount);
    }
}
