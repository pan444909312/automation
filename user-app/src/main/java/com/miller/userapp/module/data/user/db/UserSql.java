package com.miller.userapp.module.data.user.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.account.AccountEntity;
import com.hungrypanda.app.server.entity.user.UserEntity;
import com.hungrypanda.app.server.entity.user.UserLogEntity;
import com.miller.userapp.mapper.user.UserLogMapper;
import com.miller.userapp.mapper.user.UserMapper;
import com.panda.market.dal.entity.User;
import org.apache.ibatis.session.SqlSession;

public class UserSql {
    private SqlSession sqlSession;
    public  UserSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    public UserMapper getUserMapper(){
        return sqlSession.getMapper(UserMapper.class);
    }

    public void insert(UserEntity user){
        getUserMapper().insert(user);
    }
    public UserEntity getUser(String tel){
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UserEntity> lambda = queryWrapper.lambda();
        lambda.eq(UserEntity::getUserName,tel);
        lambda.eq(UserEntity::getRoleId,0);
        lambda.eq(UserEntity::getIsDel,0);
        lambda.eq(UserEntity::getUserTelphone,tel);
        return getUserMapper().selectOne(queryWrapper);
    }
    public UserEntity getUser(Long userId){
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UserEntity> lambda = queryWrapper.lambda();
        lambda.eq(UserEntity::getIsDel,0);
        lambda.eq(UserEntity::getUserId,userId);
        return getUserMapper().selectOne(queryWrapper);
    }
    public int updatePassword(Long userId,String salt,String pwd){
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<UserEntity> lambda = updateWrapper.lambda();
        lambda.eq(UserEntity::getUserId,userId);
        lambda.set(UserEntity::getUserSalt,salt);
        lambda.set(UserEntity::getUserPassword,pwd);
        return getUserMapper().update(updateWrapper);
    }
}
