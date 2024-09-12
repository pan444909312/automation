package com.miller.userapp.module.data.promotion.redpacket.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.address.CityConfigEntity;
import com.miller.userapp.mapper.base.CityConfigMapper;
import org.apache.ibatis.session.SqlSession;

public class CityConfigSql {
    private SqlSession sqlSession;
    public CityConfigSql (SqlSession sqlSession){
        this.sqlSession=sqlSession;
    }

    public CityConfigMapper getCityConfigMapper(){
        return sqlSession.getMapper(CityConfigMapper.class);
    }

    public CityConfigEntity getCityConfigByCityName(String cityName){
        QueryWrapper<CityConfigEntity> queryWrapper=new QueryWrapper<>();
        LambdaQueryWrapper<CityConfigEntity> lambda=queryWrapper.lambda();
        lambda.eq(CityConfigEntity::getCity,cityName);
        lambda.eq(CityConfigEntity::getIsDel,0);
        return getCityConfigMapper().selectOne(lambda);
    }
}
