package com.miller.userapp.module.data.basic.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.config.SysAppConfigEntity;
import com.hungrypanda.app.server.entity.shop.ShopExtraInfoEntity;
import com.miller.userapp.mapper.shop.ShopExtraInfoMapper;
import org.apache.ibatis.session.SqlSession;

public class ShopExtraInfoSql {
    private SqlSession sqlSession;

    public ShopExtraInfoSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;

    }
    public ShopExtraInfoMapper getShopExtraInfoMapper(){
        return sqlSession.getMapper(ShopExtraInfoMapper.class);
    }
    public ShopExtraInfoEntity getShopExtraInfo(Long shopId){
        QueryWrapper<ShopExtraInfoEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<ShopExtraInfoEntity> lambda = queryWrapper.lambda();
        lambda.eq(ShopExtraInfoEntity::getShopId,shopId);
        return  getShopExtraInfoMapper().selectOne(queryWrapper);
    }
}
