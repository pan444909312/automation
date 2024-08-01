package com.miller.userapp.module.data.user.db;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.account.AccountEntity;
import com.hungrypanda.app.server.entity.user.IntegralEntity;
import com.miller.userapp.mapper.user.AccountMapper;
import com.miller.userapp.mapper.user.UserAccountMapper;
import org.apache.ibatis.session.SqlSession;

public class AccountSql {
    private SqlSession sqlSession;
    public  AccountSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    public AccountMapper getAccountMapper(){
        return sqlSession.getMapper(AccountMapper.class);
    }
    public void insert(AccountEntity account){
        getAccountMapper().insert(account);
    }
    public int update(Long userId,Integer balance){
        UpdateWrapper<AccountEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<AccountEntity> lambda = updateWrapper.lambda();
        lambda.eq(AccountEntity::getUserId,userId);
        lambda.set(AccountEntity::getAccountBalance,balance);
        return getAccountMapper().update(updateWrapper);
    }
}
