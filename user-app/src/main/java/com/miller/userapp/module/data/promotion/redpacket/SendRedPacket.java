package com.miller.userapp.module.data.promotion.redpacket;

import com.miller.userapp.module.data.promotion.redpacket.flow.AddUserCdkeyFlow;
import com.miller.userapp.module.data.promotion.redpacket.request.AddUserCdkeyRequestDTO;
import com.miller.userapp.util.DBUtils;

public class SendRedPacket {
    public static void main(String[] args){
        AddUserCdkeyRequestDTO addUserCdkeyRequestDTO=new AddUserCdkeyRequestDTO();
        addUserCdkeyRequestDTO.setRedPacketId(10096518L);
        addUserCdkeyRequestDTO.setUserId(1398661268L);
//        addUserCdkeyRequestDTO.setDeviceId("c5b9471ecc93a603");
        addUserCdkeyRequestDTO.setLockStatus(0);
        addUserCdkeyRequestDTO.setCdKey("");

        AddUserCdkeyFlow addUserCdkeyFlow =new AddUserCdkeyFlow(DBUtils.getDBOfPandaTest());
        addUserCdkeyFlow.initSql();
        addUserCdkeyFlow.addCdKey(addUserCdkeyRequestDTO);
    }
}
