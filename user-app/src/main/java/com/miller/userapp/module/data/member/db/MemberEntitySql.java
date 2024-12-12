package com.miller.userapp.module.data.member.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.member.MemberEntityEntity;
import com.miller.userapp.mapper.member.MemberEntityMapper;
import org.apache.ibatis.session.SqlSession;

public class MemberEntitySql {

    private SqlSession sqlSession;

    public MemberEntitySql(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public MemberEntityMapper getMemberEntityMapper() {
        return sqlSession.getMapper(MemberEntityMapper.class);
    }

    public MemberEntityEntity getMemberEntity(Long userId) {
        Long currentTime = System.currentTimeMillis();
//
//        String sql = "select * from member_entity where user_id = ? and is_del = 0 and status = 1 and member_start_time <= "
//                + currentTime + " and member_end_time > "+currentTime + " limit 1";
        QueryWrapper<MemberEntityEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<MemberEntityEntity> lambda = queryWrapper.lambda();
        lambda.eq(MemberEntityEntity::getUserId, userId);
        lambda.eq(MemberEntityEntity::getIsDel, 0);
        lambda.eq(MemberEntityEntity::getStatus, 1);
//        lambda.gt(MemberEntityEntity::getMemberStartTime,currentTime);
//        lambda.le(MemberEntityEntity::getMemberEndTime,currentTime);
        queryWrapper.last("limit 1");
        return getMemberEntityMapper().selectOne(queryWrapper);


    }

    public Integer updateMemberEntity(Long memberEndTime, Long userId) {
//        String sql = "update member_entity set member_end_time = ? where user_id = ?";
        UpdateWrapper<MemberEntityEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<MemberEntityEntity> lambda = updateWrapper.lambda();
        lambda.eq(MemberEntityEntity::getUserId, userId);
        lambda.set(MemberEntityEntity::getMemberEndTime, memberEndTime);
        return getMemberEntityMapper().update( updateWrapper);
    }
}
