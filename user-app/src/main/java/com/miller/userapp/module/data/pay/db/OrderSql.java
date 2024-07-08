package com.miller.userapp.module.data.pay.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.order.OrderEntity;
import com.miller.userapp.mapper.order.OrderMapper;
import com.miller.userapp.module.data.member.PandaDB;
import org.apache.ibatis.session.SqlSession;

public class OrderSql {
    private SqlSession sqlSession;
    public  OrderSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
//    public  OrderSql(){
//        this.sqlSession = PandaDB.getSqlSession(this.getClass());
//    }
    public OrderMapper getOrderMapper(){
        return sqlSession.getMapper(OrderMapper.class);
    }
    public OrderEntity getOrderEntity(String orderSn){
        QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OrderEntity> lambda = queryWrapper.lambda();
        lambda.eq(OrderEntity::getOrderSn,orderSn);
        return  getOrderMapper().selectOne(queryWrapper);
    }
}
