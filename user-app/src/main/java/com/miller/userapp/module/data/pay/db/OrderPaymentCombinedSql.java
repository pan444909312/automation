package com.miller.userapp.module.data.pay.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.order.OrderEntity;
import com.hungrypanda.app.server.entity.order.OrderPaymentCombinedEntity;
import com.hungrypanda.app.server.entity.order.OrderPaymentCombinedRelationEntity;
import com.miller.userapp.mapper.order.OrderMapper;
import com.miller.userapp.mapper.order.OrderPaymentCombinedMapper;
import com.miller.userapp.mapper.order.OrderPaymentCombinedRelationMapper;
import com.miller.userapp.module.data.member.PandaDB;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class OrderPaymentCombinedSql {
    private SqlSession sqlSession;
    public  OrderPaymentCombinedSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
//    public  OrderPaymentCombinedSql(){
//        this.sqlSession = PandaDB.getSqlSession(this.getClass());
//    }
    public OrderPaymentCombinedMapper getOrderPaymentCombinedMapper(){
        return sqlSession.getMapper(OrderPaymentCombinedMapper.class);
    }
    public OrderPaymentCombinedRelationMapper getOrderPaymentCombinedRelationMapper(){
        return sqlSession.getMapper(OrderPaymentCombinedRelationMapper.class);
    }
    public OrderPaymentCombinedEntity getOrderPaymentCombinedEntity(String orderCombinedSn){
        QueryWrapper<OrderPaymentCombinedEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OrderPaymentCombinedEntity> lambda = queryWrapper.lambda();
        lambda.eq(OrderPaymentCombinedEntity::getOrderCombinedSn,orderCombinedSn);
        return  getOrderPaymentCombinedMapper().selectOne(queryWrapper);
    }
    public List<OrderPaymentCombinedRelationEntity> getOrderPaymentCombinedRelationEntity(String orderCombinedSn){
        QueryWrapper<OrderPaymentCombinedRelationEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OrderPaymentCombinedRelationEntity> lambda = queryWrapper.lambda();
        lambda.eq(OrderPaymentCombinedRelationEntity::getOrderCombinedSn,orderCombinedSn);
        return  getOrderPaymentCombinedRelationMapper().selectList(queryWrapper);
    }
}
