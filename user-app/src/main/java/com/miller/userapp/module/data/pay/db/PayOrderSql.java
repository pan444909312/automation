package com.miller.userapp.module.data.pay.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.payserver.entity.PayOrder;
import com.miller.userapp.mapper.pay.PayOrderMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.Objects;

public class PayOrderSql {
    private SqlSession sqlSession;
    public  PayOrderSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    public PayOrderMapper getPayOrderMapper(){
        return sqlSession.getMapper(PayOrderMapper.class);
    }

    /**
     * 只根据订单号查找成功的
     * @param orderSn
     * @return
     */
    public PayOrder getPayOrder(String orderSn){
        QueryWrapper<PayOrder> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PayOrder> lambda = queryWrapper.lambda();
        lambda.eq(PayOrder::getOrderId,orderSn);
        lambda.eq(PayOrder::getTradeStatus,1);
        lambda.orderByDesc(PayOrder::getCreateTime);
        lambda.last("limit 1");
        return  getPayOrderMapper().selectOne(queryWrapper);
    }

    /**
     * 按照订单号查找，根据isSuccess查找成功或者其他
     * @param orderSn
     * @param isSuccess
     * @return
     */
    public PayOrder getPayOrder(String orderSn,boolean isSuccess){
        QueryWrapper<PayOrder> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PayOrder> lambda = queryWrapper.lambda();
        lambda.eq(PayOrder::getOrderId,orderSn);
        if(isSuccess){
            lambda.eq(PayOrder::getTradeStatus,1);
        }
        lambda.orderByDesc(PayOrder::getCreateTime);
        lambda.last("limit 1");
        return  getPayOrderMapper().selectOne(queryWrapper);
    }

    /**
     * 按照交易号查找
     * @param tradeNo
     * @param isTradeNo 如果是交易号，则无需关注isSuccess
     * @param isSuccess
     * @return
     */
    public PayOrder getPayOrder(String tradeNo,boolean isTradeNo,boolean isSuccess){
        QueryWrapper<PayOrder> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PayOrder> lambda = queryWrapper.lambda();
        if(isTradeNo || Objects.isNull(isTradeNo)){
            lambda.eq(PayOrder::getTradeNo,tradeNo);
        }else {
            return  getPayOrder(tradeNo,isSuccess);
        }
        return  getPayOrderMapper().selectOne(queryWrapper);
    }
}
