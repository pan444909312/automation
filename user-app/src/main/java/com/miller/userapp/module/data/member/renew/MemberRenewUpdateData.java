package com.miller.userapp.module.data.member.renew;

import com.hungrypanda.app.server.entity.member.MemberAutoRenewEntity;
import com.hungrypanda.app.server.entity.member.MemberEntityEntity;
import com.miller.userapp.module.data.member.db.MemberAutoRenewSql;
import com.miller.userapp.module.data.member.db.MemberEntitySql;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class MemberRenewUpdateData {

    //主要用来测试站更新自动续费的数据，可以不用手动更新
    //详细见文档：https://alidocs.dingtalk.com/i/nodes/AR4GpnMqJzMKgaXKTAZgP4q1VKe0xjE3
    public static void main(String[] args) {
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        MemberAutoRenewSql memberAutoRenewSql = new MemberAutoRenewSql(sqlSession);
        MemberEntitySql memberEntitySql = new MemberEntitySql(sqlSession);
        Long userId = 249296l;
        MemberAutoRenewEntity memberAutoRenewEntity = memberAutoRenewSql.getMemberAutoRenew(userId);
        if (Objects.isNull(memberAutoRenewEntity) || memberAutoRenewEntity.getAutoRenew() != 1){
            throw new RuntimeException("非自动续费用户");
        }
        if (memberAutoRenewEntity.getAuthType() != 1){
            throw new RuntimeException("非授权用户，是订阅用户");
        }
        MemberEntityEntity memberEntity = memberEntitySql.getMemberEntity(userId);
        if (Objects.isNull(memberEntity)){
            throw new RuntimeException("会员不存在");
        }
        Long currentTime = System.currentTimeMillis();
        Long memberEndTime = currentTime + 2*24*60*60*1000 - 60*1000;
        Long nextAutoTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
                .toInstant(ZoneOffset.of("+8")).toEpochMilli();
        memberAutoRenewSql.updateMemberAutoRenew(nextAutoTime,userId);
        memberEntitySql.updateMemberEntity(memberEndTime,userId);
    }

}
