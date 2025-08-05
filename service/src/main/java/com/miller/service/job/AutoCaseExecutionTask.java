package com.miller.service.job;

import com.miller.service.testcase.TestCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author: panjuxiang
 * @Since: 2025/8/4
 */
@Component
@Slf4j
public class AutoCaseExecutionTask {
    @Autowired
    private TestCaseService testCaseService;

    /**
     * 每日0:30分执行一次
     */
    @Scheduled(cron = "0 30 0 * * ?")
    //每5分钟执行一次
//    @Scheduled(cron = "0 0/5 * * * ?")
    public void execute() {
        ArrayList<String> packageNameList = new ArrayList<>();
        packageNameList.add("com.miller.userapp.module.shop.card.version3");
//        packageNameList.add("com.miller.testcase.module.account.promote_confirm");
//        packageNameList.add("com.miller.testcase.module.account.redpacket");
//        packageNameList.add("com.miller.testcase.module.account.member.Buymemberdetail_Tests");
        testCaseService.runTestCase(packageNameList);
    }
}
