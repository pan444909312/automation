package com.miller.userapp.module.data.user.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.user.UserLogEntity;
import com.miller.userapp.mapper.user.UserLogMapper;
import org.apache.ibatis.session.SqlSession;

import javax.management.Query;

public class UserLogSql {
    private SqlSession sqlSession;
    public  UserLogSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    public UserLogMapper getUserLogMapper(){
        return sqlSession.getMapper(UserLogMapper.class);
    }
    public void insert(UserLogEntity userLog){
        getUserLogMapper().insert(userLog);
    }
    public Integer getCaptcha(String tel){
        QueryWrapper<UserLogEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UserLogEntity> lambda = queryWrapper.lambda();
        lambda.eq(UserLogEntity::getTelephone,tel);
        lambda.orderByDesc(UserLogEntity::getUserLogId);
        queryWrapper.last("limit 1");
        return  getUserLogMapper().selectOne(queryWrapper).getVerifycode();
    }
}
