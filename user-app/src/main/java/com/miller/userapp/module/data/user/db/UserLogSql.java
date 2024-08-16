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
//        lambda.eq(UserLogEntity::getTelephone,tel);
        lambda.likeRight(UserLogEntity::getTelephone,tel.substring(0,3));
        lambda.likeLeft(UserLogEntity::getTelephone,tel.substring(tel.length()-4,tel.length()));
        lambda.orderByDesc(UserLogEntity::getUserLogId);
        queryWrapper.last("limit 1");
        return  getUserLogMapper().selectOne(queryWrapper).getVerifycode();
    }
    public static void main(String[] args){
        System.out.println("16572882043".substring(0,3));
        System.out.println("16572882043".substring("16572882043".length()-4,"16572882043".length()));
    }
}
