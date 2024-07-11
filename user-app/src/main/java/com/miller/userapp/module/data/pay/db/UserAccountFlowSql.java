package com.miller.userapp.module.data.pay.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.user.UserAccountFlowEntity;
import com.miller.userapp.mapper.user.UserAccountFlowMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UserAccountFlowSql {
    private SqlSession sqlSession;
    public  UserAccountFlowSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
//    public  UserAccountFlowSql(){
//        this.sqlSession = PandaDB.getSqlSession(this.getClass());
//    }
    public UserAccountFlowMapper getUserAccountFlowMapper(){
        return sqlSession.getMapper(UserAccountFlowMapper.class);
    }



    public List<UserAccountFlowEntity> getUserAccountFlowEntity(String orderSn){
        QueryWrapper<UserAccountFlowEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UserAccountFlowEntity> lambda = queryWrapper.lambda();
        lambda.eq(UserAccountFlowEntity::getOrderSn,orderSn);
        return  getUserAccountFlowMapper().selectList(queryWrapper);
    }

    /**
     * 只能用在普通外卖补充支付
     * @param userId
     * @return
     */
    public UserAccountFlowEntity getUserAccountFlowEntity(Long userId){
        QueryWrapper<UserAccountFlowEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UserAccountFlowEntity> lambda = queryWrapper.lambda();
        lambda.eq(UserAccountFlowEntity::getUserId,userId);
        lambda.eq(UserAccountFlowEntity::getTempAccountBalance,0);
        lambda.eq(UserAccountFlowEntity::getType,1);
        lambda.orderByDesc(UserAccountFlowEntity::getCreateTime);
        queryWrapper.last("limit 1");
        return  getUserAccountFlowMapper().selectOne(queryWrapper);
    }
    public UserAccountFlowEntity getUserAccountFlowEntity(Long userId,String orderSn){
        QueryWrapper<UserAccountFlowEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UserAccountFlowEntity> lambda = queryWrapper.lambda();
        lambda.eq(UserAccountFlowEntity::getUserId,userId);
        if(StringUtils.isEmpty(orderSn)){
            lambda.isNull(UserAccountFlowEntity::getOrderSn);
        }else {
            lambda.eq(UserAccountFlowEntity::getOrderSn,orderSn);
        }
        lambda.eq(UserAccountFlowEntity::getType,1);
        lambda.orderByDesc(UserAccountFlowEntity::getCreateTime);
        queryWrapper.last("limit 1");
        return  getUserAccountFlowMapper().selectOne(queryWrapper);
    }
}
