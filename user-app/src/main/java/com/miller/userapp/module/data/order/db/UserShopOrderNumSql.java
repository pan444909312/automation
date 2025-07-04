package com.miller.userapp.module.data.order.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.user.UserShopOrderNumEntity;
import com.miller.userapp.mapper.user.UserShopOrderNumMapper;
import org.apache.ibatis.session.SqlSession;

public class UserShopOrderNumSql {
    private SqlSession sqlSession;

    public UserShopOrderNumSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;

    }
    public UserShopOrderNumMapper getSUserShopOrderNumMapper(){
        return sqlSession.getMapper(UserShopOrderNumMapper.class);
    }
    public UserShopOrderNumEntity getUserShopOrderNumEntity(String userId,Long shopId){
        QueryWrapper<UserShopOrderNumEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UserShopOrderNumEntity> lambda = queryWrapper.lambda();
        lambda.eq(UserShopOrderNumEntity::getUserId,userId);
        lambda.eq(UserShopOrderNumEntity::getShopId,shopId);
        lambda.in(UserShopOrderNumEntity::getAddSource,0,1);
        lambda.eq(UserShopOrderNumEntity::getIsDel,0);
        return  getSUserShopOrderNumMapper().selectOne(queryWrapper);
    }
}
