package com.miller.userapp.module.data.promotion.redpacket;

import com.miller.common.util.DateUtils;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.launcher.TestCaseRunnerLauncher;
import com.miller.userapp.module.data.promotion.redpacket.flow.AddUserCdkeyFlow;
import com.miller.userapp.module.data.promotion.redpacket.request.AddUserCdkeyRequestDTO;
import com.miller.userapp.util.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

@Slf4j
@Scenario(scenarioID = "01K23RNF4T0TGF37NF0V5PDBHZ", scenarioName = "红包工厂_一键给指定用户发放红包",
        author = "zhangcheng@hungrypandagroup.com",
        developmentTime = 8 * 60, maintenanceTime = 0, manualTestTime = 10)
public class SendRedPacket {

    private void tearDown() {
        new TestCaseRunnerLauncher().runTestMethod(CouponGenerate.class, "reportedData");
        // 搜索索引更新
    }

    @Test
    @DisplayName("一键给指定用户发放红包")
    void reportedData() {
        // 什么都不需要做，仅仅是作为数据上报，复用现在测试框架功能
    }

    public static void main(String[] args) {
        String startTime = "2024-10-01 12:30:45";
        String endTime = "2024-11-01 12:30:45";
        AddUserCdkeyRequestDTO addUserCdkeyRequestDTO = new AddUserCdkeyRequestDTO();
        addUserCdkeyRequestDTO.setRedPacketId(10096518L);
        addUserCdkeyRequestDTO.setUserId(1398661268L);
//        addUserCdkeyRequestDTO.setDeviceId("c5b9471ecc93a603");
        addUserCdkeyRequestDTO.setLockStatus(0);
        addUserCdkeyRequestDTO.setCdKey("");
        addUserCdkeyRequestDTO.setStartTime(DateUtils.getTimestamp(startTime, "Asia/Shanghai"));
        addUserCdkeyRequestDTO.setStartTime(DateUtils.getTimestamp(endTime, "Asia/Shanghai"));

        AddUserCdkeyFlow addUserCdkeyFlow = new AddUserCdkeyFlow(DBUtils.getDBOfPandaTest());
        addUserCdkeyFlow.initSql();
        addUserCdkeyFlow.addCdKey(addUserCdkeyRequestDTO);
    }

    public void sendRedPacketToUserByUserId(Long userId,Long redPacketId) {

        String startTime = new DateTime(new Date()).toString("yyyy-MM-dd");
        String endTime = new DateTime(new Date()).plusDays(30).toString("yyyy-MM-dd");
        AddUserCdkeyRequestDTO addUserCdkeyRequestDTO = new AddUserCdkeyRequestDTO();
        addUserCdkeyRequestDTO.setRedPacketId(redPacketId);
        addUserCdkeyRequestDTO.setUserId(userId);
        addUserCdkeyRequestDTO.setLockStatus(0);
        addUserCdkeyRequestDTO.setCdKey("");
        addUserCdkeyRequestDTO.setStartTime(DateUtils.getTimestamp(startTime, "Asia/Shanghai"));
        addUserCdkeyRequestDTO.setStartTime(DateUtils.getTimestamp(endTime, "Asia/Shanghai"));

        AddUserCdkeyFlow addUserCdkeyFlow = new AddUserCdkeyFlow(DBUtils.getDBOfPandaTest());
        addUserCdkeyFlow.initSql();
        addUserCdkeyFlow.addCdKey(addUserCdkeyRequestDTO);

        tearDown();
    }
}
