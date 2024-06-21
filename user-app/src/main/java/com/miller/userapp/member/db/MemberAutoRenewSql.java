package com.miller.userapp.member.db;

import com.miller.service.framework.db.DBUtils;
import com.miller.userapp.member.renew.MemberAutoRenewDTO;
import com.miller.userapp.member.renew.MemberEntityDTO;
import com.miller.userapp.member.PandaDB;

import java.util.Arrays;

public class MemberAutoRenewSql {
    DBUtils dbUtils;
    public MemberAutoRenewSql(){
        dbUtils = PandaDB.getDBInstance();
    }

    public  MemberAutoRenewDTO getMemberAutoRenew(Long userId){
        String sql = "select * from hp_member_auto_renew where user_id = ?";
        return dbUtils.queryOneObjectReturnObject(sql,MemberAutoRenewDTO.class,userId);

    }
    public   Integer updateMemberAutoRenew(Long nextAutoTime,Long userId){
        String sql = "update hp_member_auto_renew set next_auto_time = ? and user_id = ";
        return dbUtils.executeInsertOrUpdateOrDelete(sql, Arrays.asList(nextAutoTime,userId));
    }
}
