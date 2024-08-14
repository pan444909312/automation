package com.miller.userapp.module.data.pay.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.payserver.entity.PayUserAccount;
import com.miller.userapp.mapper.pay.PayUserAccountMapper;
import org.apache.ibatis.session.SqlSession;

public class PayUserAccountSql {
    private SqlSession sqlSession;
    public PayUserAccountSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    private PayUserAccountMapper getPayUserAccountMapper(){
        return sqlSession.getMapper(PayUserAccountMapper.class);
    }
    public PayUserAccount getPayUserAccount(String userId,String countryCode,String payChannel){
        QueryWrapper<PayUserAccount> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PayUserAccount> lambda = queryWrapper.lambda();
        lambda.eq(PayUserAccount::getUserId,userId);
        lambda.eq(PayUserAccount::getCountryCode,countryCode);
        lambda.eq(PayUserAccount::getPayChannel,payChannel);
        lambda.eq(PayUserAccount::getAccountNo,"pandaAccount");
        return getPayUserAccountMapper().selectOne(queryWrapper);
    }
}
