package com.miller.service.job;

import com.miller.common.util.StringToListUtils;
import com.miller.entity.constant.RunTeatCaseTypeEnum;
import com.miller.service.report.ConfigService;
import com.miller.service.testcase.TestCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: panjuxiang
 * @Since: 2025/8/4
 */
@Component
@Slf4j
public class AutoCaseExecutionTask {
    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private ConfigService configService;

    /**
     * 每日0:30分执行一次
     */
    @Scheduled(cron = "0 30 0 * * ?")
    //每5分钟执行一次
//    @Scheduled(cron = "0 0/5 * * * ?")
    public void executeCaseC() {
        String executionCaseUrl = configService.getConfigByKey("EXECUTION_CASE_TASK");
        List<String> strings = StringToListUtils.stringToList(executionCaseUrl);

//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("com.miller.testcase.module.account.promote_confirm");
//        strings.add("com.miller.testcase.module.account.redpacket");
//        strings.add("com.miller.testcase.module.account.member");
        testCaseService.runTestCaseC(strings, RunTeatCaseTypeEnum.TASK);
    }

    /**
     * 每日4:30分执行一次
     */
//    @Scheduled(cron = "0 30 4 * * ?")
    public void executeCaseD() {
        String executionCaseUrl = configService.getConfigByKey("EXECUTION_CASE_TASK_D");
        List<String> strings = StringToListUtils.stringToList(executionCaseUrl);

        testCaseService.runTestCaseD(strings, RunTeatCaseTypeEnum.TASK);
    }
}
