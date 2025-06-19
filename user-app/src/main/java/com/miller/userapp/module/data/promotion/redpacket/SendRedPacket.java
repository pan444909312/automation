package com.miller.userapp.module.data.promotion.redpacket;

import com.miller.common.util.DateUtils;
import com.miller.userapp.module.data.promotion.redpacket.flow.AddUserCdkeyFlow;
import com.miller.userapp.module.data.promotion.redpacket.request.AddUserCdkeyRequestDTO;
import com.miller.userapp.util.DBUtils;
import org.joda.time.DateTime;

import java.util.Date;

public class SendRedPacket {
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

    }
}
