package com.miller.userapp.module.data.pay.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.voucher.VoucherOrderInfoEntity;
import com.miller.userapp.mapper.order.VoucherOrderInfoMapper;
import org.apache.ibatis.session.SqlSession;

public class VoucherOrderInfoSql {
    private SqlSession sqlSession;
    public  VoucherOrderInfoSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
//    public  VoucherOrderInfoSql(){
//        this.sqlSession = PandaDB.getSqlSession(this.getClass());
//    }
    public VoucherOrderInfoMapper getVoucherOrderInfoMapper(){
        return sqlSession.getMapper(VoucherOrderInfoMapper.class);
    }
    public VoucherOrderInfoEntity getVoucherOrderInfoEntity(String orderSn){
        QueryWrapper<VoucherOrderInfoEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<VoucherOrderInfoEntity> lambda = queryWrapper.lambda();
        lambda.eq(VoucherOrderInfoEntity::getOrderSn,orderSn);
        return  getVoucherOrderInfoMapper().selectOne(queryWrapper);
    }
}
