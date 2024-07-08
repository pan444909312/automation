package com.miller.userapp.module.data.pay.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.userapp.mapper.pay.PayOrderMapper;
import com.miller.userapp.module.data.DataName;
import com.miller.userapp.module.data.member.PandaDB;
import com.miller.userapp.module.data.pay.PayOrder;
import org.apache.ibatis.session.SqlSession;
@DataName("pay")
public class PayOrderSql {
    private SqlSession sqlSession;
    public  PayOrderSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    public  PayOrderSql(){
        this.sqlSession = PandaDB.getSqlSession(this.getClass());
    }
    public PayOrderMapper getPayOrderMapper(){
        return sqlSession.getMapper(PayOrderMapper.class);
    }
    public PayOrder getPayOrder(String orderSn){
        QueryWrapper<PayOrder> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PayOrder> lambda = queryWrapper.lambda();
        lambda.eq(PayOrder::getOrderId,orderSn);
        lambda.eq(PayOrder::getTradeStatus,1);
        lambda.orderByDesc(PayOrder::getCreateTime);
        lambda.last("limit 1");
        return  getPayOrderMapper().selectOne(queryWrapper);
    }
}
