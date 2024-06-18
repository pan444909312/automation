package com.miller.userapp.member.renew;

import com.miller.userapp.member.db.MemberAutoRenewSql;
import com.miller.userapp.member.db.MemberEntitySql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class MemberRenewUpdateData {

    //主要用来测试站更新自动续费的数据，可以不用手动更新
    //详细见文档：https://alidocs.dingtalk.com/i/nodes/AR4GpnMqJzMKgaXKTAZgP4q1VKe0xjE3
    public static void main(String[] args) {
        MemberAutoRenewSql  memberAutoRenewSql = new MemberAutoRenewSql();
        MemberEntitySql memberEntitySql = new MemberEntitySql();
        String userId = "249296";
        MemberAutoRenewDTO memberAutoRenewDTO = memberAutoRenewSql.getMemberAutoRenew(userId);
        if (Objects.isNull(memberAutoRenewDTO) || memberAutoRenewDTO.getAutoRenew() != 1){
            throw new RuntimeException("非自动续费用户");
        }
        if (memberAutoRenewDTO.getAuthType() != 1){
            throw new RuntimeException("非授权用户，是订阅用户");
        }
        MemberEntityDTO memberEntityDTO = memberEntitySql.getMemberEntity(userId);
        if (Objects.isNull(memberEntityDTO)){
            throw new RuntimeException("会员不存在");
        }
        Long currentTime = System.currentTimeMillis();
//        memberEntityDTO.setMemberEndTime(currentTime + 2*24*60*60*1000 - 60*1000); //当前时间+48小时-1分钟
//        memberAutoRenewDTO.setNextAutoTime(LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
//                .toInstant(ZoneOffset.of("+8")).toEpochMilli());
        Long memberEndTime = currentTime + 2*24*60*60*1000 - 60*1000;
        Long nextAutoTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
                .toInstant(ZoneOffset.of("+8")).toEpochMilli();
        memberAutoRenewSql.updateMemberAutoRenew(nextAutoTime);
        memberEntitySql.updateMemberEntity(memberEndTime);
    }

}
