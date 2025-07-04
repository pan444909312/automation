package com.miller.userapp.module.data.order.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.shop.ShopFirstDiscountConfigEntity;
import com.miller.userapp.mapper.shop.ShopFirstDiscountConfigMapper;
import org.apache.ibatis.session.SqlSession;

public class ShopFirstDiscountConfigSql {
    private SqlSession sqlSession;

    public ShopFirstDiscountConfigSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;

    }
    public ShopFirstDiscountConfigMapper getShopFirstDiscountConfigMapper(){
        return sqlSession.getMapper(ShopFirstDiscountConfigMapper.class);
    }
    public ShopFirstDiscountConfigEntity getShopFirstDiscountConfigEntity(Long shopId){
        Long now = System.currentTimeMillis();
        QueryWrapper<ShopFirstDiscountConfigEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<ShopFirstDiscountConfigEntity> lambda = queryWrapper.lambda();
        lambda.eq(ShopFirstDiscountConfigEntity::getShopId,shopId);
        lambda.eq(ShopFirstDiscountConfigEntity::getStatus,1);
        lambda.eq(ShopFirstDiscountConfigEntity::getSts,1);
        lambda.le(ShopFirstDiscountConfigEntity::getStartTime,now);
        lambda.ge(ShopFirstDiscountConfigEntity::getEndTime,now);
        lambda.last("limit 1");
        return getShopFirstDiscountConfigMapper().selectOne(queryWrapper);
    }
}
