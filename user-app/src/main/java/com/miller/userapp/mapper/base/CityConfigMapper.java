package com.miller.userapp.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.entity.address.CityConfigEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CityConfigMapper extends BaseMapper<CityConfigEntity> {
}
