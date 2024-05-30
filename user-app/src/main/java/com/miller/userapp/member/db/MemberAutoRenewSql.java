package com.miller.userapp.member.db;

import com.miller.service.framework.db.DBUtils;
import com.miller.userapp.member.renew.MemberAutoRenewDTO;
import com.miller.userapp.member.renew.MemberEntityDTO;

public class MemberAutoRenewSql {
    DBUtils dbUtils;
    public MemberAutoRenewSql(){
        dbUtils = PandaDB.getDBInstance();
    }

    public  MemberAutoRenewDTO getMemberAutoRenew(String userId){
        String sql = "select * from hp_member_auto_renew where user_id = ?";
        return dbUtils.queryOneObjectReturnObject(sql,MemberAutoRenewDTO.class,userId);

    }
    public   Integer updateMemberAutoRenew(Long nextAutoTime){
        String sql = "update hp_member_auto_renew set next_auto_time = ?";
        return dbUtils.executeInsertOrUpdateOrDelete(sql,nextAutoTime);
    }
}
