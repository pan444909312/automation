package com.miller.userapp.module.data.pay.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.account.UserAccountEntity;
import com.miller.userapp.mapper.user.UserAccountMapper;
import org.apache.ibatis.session.SqlSession;

public class UserAccountSql {
    private SqlSession sqlSession;
    public  UserAccountSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
//    public  UserAccountSql(){
//        this.sqlSession = PandaDB.getSqlSession(this.getClass());
//    }
    public UserAccountMapper getUserAccountMapper(){
        return sqlSession.getMapper(UserAccountMapper.class);
    }
    public UserAccountEntity getUserAccountEntity(Long userId){
        QueryWrapper<UserAccountEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UserAccountEntity> lambda = queryWrapper.lambda();
        lambda.eq(UserAccountEntity::getUserId,userId);
        return  getUserAccountMapper().selectOne(queryWrapper);
    }
}
