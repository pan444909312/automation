package com.miller.userapp.module.data.member.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.member.MemberAutoRenewEntity;
import com.miller.userapp.mapper.member.MemberAutoRenewMapper;
import org.apache.ibatis.session.SqlSession;


public class MemberAutoRenewSql {
    private SqlSession sqlSession;

    public MemberAutoRenewSql(SqlSession sqlSession) {
        this.sqlSession = sqlSession;

    }

    public MemberAutoRenewMapper getMemberAutoRenewMapper() {
        return sqlSession.getMapper(MemberAutoRenewMapper.class);
    }

    public MemberAutoRenewEntity getMemberAutoRenew(Long userId) {
//        String sql = "select * from hp_member_auto_renew where user_id = ?";
        QueryWrapper<MemberAutoRenewEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<MemberAutoRenewEntity> lambda = queryWrapper.lambda();
        lambda.eq(MemberAutoRenewEntity::getUserId, userId);
        return getMemberAutoRenewMapper().selectOne(queryWrapper);

    }

    public Integer updateMemberAutoRenew(Long nextAutoTime, Long userId) {
//        String sql = "update hp_member_auto_renew set next_auto_time = ? where user_id = ? ";
        UpdateWrapper<MemberAutoRenewEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<MemberAutoRenewEntity> lambda = updateWrapper.lambda();
        lambda.eq(MemberAutoRenewEntity::getUserId, userId);
        lambda.set(MemberAutoRenewEntity::getNextAutoTime, nextAutoTime);

        return getMemberAutoRenewMapper().update(updateWrapper);
    }
}
