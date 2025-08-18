package com.miller.userapp.module.data.user.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.user.UserEntity;
import com.miller.userapp.mapper.user.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

@Slf4j
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
        log.info("查询sqlSession信息：{}",sqlSession);
        UserMapper userMapper = getUserMapper();
        return userMapper.selectOne(queryWrapper);
    }
    public UserEntity getUser(Long userId){
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UserEntity> lambda = queryWrapper.lambda();
        lambda.eq(UserEntity::getIsDel,0);
        lambda.eq(UserEntity::getUserId,userId);
        return getUserMapper().selectOne(queryWrapper);
    }

    public UserEntity getUserRold0(Long userId){
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UserEntity> lambda = queryWrapper.lambda();
        lambda.eq(UserEntity::getIsDel,0);
        lambda.eq(UserEntity::getUserId,userId);
        lambda.eq(UserEntity::getRoleId,0);
        return getUserMapper().selectOne(queryWrapper);
    }

    public int updatePassword(Long userId,String salt,String pwd){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setUserSalt(salt);
        userEntity.setUserPassword(pwd);
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<UserEntity> lambda = updateWrapper.lambda();
        lambda.eq(UserEntity::getUserId,userId);
        lambda.set(UserEntity::getUserSalt,salt);
        lambda.set(UserEntity::getUserPassword,pwd);
        return getUserMapper().update(userEntity, updateWrapper);
    }
}
