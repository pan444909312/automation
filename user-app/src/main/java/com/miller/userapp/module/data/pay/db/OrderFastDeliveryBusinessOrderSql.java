package com.miller.userapp.module.data.pay.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.order.OrderEntity;
import com.hungrypanda.app.server.entity.order.OrderFastDeliveryBusinessOrderEntity;
import com.miller.userapp.mapper.order.OrderFastDeliveryBusinessOrderMapper;
import com.miller.userapp.mapper.order.OrderMapper;
import com.miller.userapp.module.data.member.PandaDB;
import org.apache.ibatis.session.SqlSession;

public class OrderFastDeliveryBusinessOrderSql {
    private SqlSession sqlSession;
    public  OrderFastDeliveryBusinessOrderSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
//    public  OrderFastDeliveryBusinessOrderSql(){
//        this.sqlSession = PandaDB.getSqlSession(this.getClass());
//    }
    public OrderFastDeliveryBusinessOrderMapper getOrderFastDeliveryBusinessOrderMapper(){
        return sqlSession.getMapper(OrderFastDeliveryBusinessOrderMapper.class);
    }
    public OrderFastDeliveryBusinessOrderEntity getOrderFastDeliveryBusinessOrderEntity(String orderSn){
        QueryWrapper<OrderFastDeliveryBusinessOrderEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OrderFastDeliveryBusinessOrderEntity> lambda = queryWrapper.lambda();
        lambda.eq(OrderFastDeliveryBusinessOrderEntity::getAdditionalSn,orderSn);
        return  getOrderFastDeliveryBusinessOrderMapper().selectOne(queryWrapper);
    }
}
