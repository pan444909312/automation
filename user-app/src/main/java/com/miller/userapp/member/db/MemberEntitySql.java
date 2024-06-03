package com.miller.userapp.member.db;

import com.miller.service.framework.db.DBUtils;
import com.miller.userapp.member.renew.MemberEntityDTO;

public class MemberEntitySql {
    DBUtils dbUtils;
    public MemberEntitySql(){
        dbUtils = PandaDB.getDBInstance();
    }
    public MemberEntityDTO getMemberEntity(String userId){
        Long currentTime = System.currentTimeMillis();

        String sql = "select * from member_entity where user_id = ? and is_del = 0 and status = 1 and member_start_time <= "
                + currentTime + " and member_end_time > "+currentTime + " limit 1";
        return dbUtils.queryOneObjectReturnObject(sql,MemberEntityDTO.class,userId);

    }
    public Integer updateMemberEntity(Long memberEndTime){
        String sql = "update member_entity set member_end_time = ?";
        return dbUtils.executeInsertOrUpdateOrDelete(sql,memberEndTime);
    }
}
