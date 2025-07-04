package com.miller.userapp.module.data.basic.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.config.SysAppConfigEntity;
import com.hungrypanda.app.server.entity.shop.FullSubEntity;
import com.miller.userapp.mapper.shop.SysAppConfigMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class SysAppConfigSql {
    private SqlSession sqlSession;

    public SysAppConfigSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;

    }
    public SysAppConfigMapper getSysAppConfigMapper(){
        return sqlSession.getMapper(SysAppConfigMapper.class);
    }
    public SysAppConfigEntity getSysAppConfigByKey(String configKey){
        QueryWrapper<SysAppConfigEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<SysAppConfigEntity> lambda = queryWrapper.lambda();
        lambda.eq(SysAppConfigEntity::getConfigKey,configKey);
        lambda.last("limit 1");
        return  getSysAppConfigMapper().selectOne(queryWrapper);
    }
    public List<SysAppConfigEntity> getSysAppConfigByKeys(List<String> configKeys){
        QueryWrapper<SysAppConfigEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<SysAppConfigEntity> lambda = queryWrapper.lambda();
        lambda.in(SysAppConfigEntity::getConfigKey,configKeys);
        return  getSysAppConfigMapper().selectList(queryWrapper);
    }
}
