package com.miller.userapp.module.data.order.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.shop.FullSubEntity;
import com.miller.userapp.mapper.shop.FullSubMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class FullSubSql {
    private SqlSession sqlSession;

    public FullSubSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;

    }
    public FullSubMapper getFullSubMapper(){
        return sqlSession.getMapper(FullSubMapper.class);
    }
    public List<FullSubEntity> getFullSubEntity(Long shopId, String languageCode){
        QueryWrapper<FullSubEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<FullSubEntity> lambda = queryWrapper.lambda();
        lambda.eq(FullSubEntity::getShopId,shopId);
        lambda.eq(FullSubEntity::getLanguageCode,languageCode);
        return  getFullSubMapper().selectList(queryWrapper);

    }
}
