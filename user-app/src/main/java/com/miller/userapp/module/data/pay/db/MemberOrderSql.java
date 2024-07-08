package com.miller.userapp.module.data.pay.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.member.MemberOrderEntity;
import com.hungrypanda.app.server.entity.order.OrderEntity;
import com.miller.userapp.mapper.order.MemberOrderMapper;
import com.miller.userapp.mapper.order.OrderMapper;
import com.miller.userapp.module.data.member.PandaDB;
import org.apache.ibatis.session.SqlSession;

public class MemberOrderSql {
    private SqlSession sqlSession;
    public  MemberOrderSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
//    public  MemberOrderSql(){
//        this.sqlSession = PandaDB.getSqlSession(this.getClass());
//    }
    public MemberOrderMapper getMemberOrderMapper(){
        return sqlSession.getMapper(MemberOrderMapper.class);
    }
    public MemberOrderEntity getMemberOrderEntity(String orderSn){
        QueryWrapper<MemberOrderEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<MemberOrderEntity> lambda = queryWrapper.lambda();
        lambda.eq(MemberOrderEntity::getMemberOrderSn,orderSn);
        return  getMemberOrderMapper().selectOne(queryWrapper);
    }
}
