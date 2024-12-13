package com.miller.userapp.module.data.user.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.user.IntegralEntity;
import com.hungrypanda.app.server.entity.user.UserLogEntity;
import com.miller.userapp.mapper.user.IntegralMapper;
import org.apache.ibatis.session.SqlSession;

public class IntegralSql {
    private SqlSession sqlSession;

    public IntegralSql(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public IntegralMapper getIntegralMapper() {
        return sqlSession.getMapper(IntegralMapper.class);
    }

    public void insert(IntegralEntity integral) {
        getIntegralMapper().insert(integral);
    }

    public int update(String userId, Long integral) {
        IntegralEntity integralEntity = new IntegralEntity();
        integralEntity.setUserId(Long.valueOf(userId));
        integralEntity.setIntergral(integral);
        UpdateWrapper<IntegralEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<IntegralEntity> lambda = updateWrapper.lambda();
        lambda.eq(IntegralEntity::getUserId, userId);
        lambda.set(IntegralEntity::getIntergral, integral);
        return getIntegralMapper().update(integralEntity, updateWrapper);
    }
}
