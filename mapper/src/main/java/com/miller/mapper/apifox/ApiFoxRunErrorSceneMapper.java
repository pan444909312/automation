package com.miller.mapper.apifox;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.apifox.ApiFoxRunErrorSceneEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiFoxRunErrorSceneMapper extends BaseMapper<ApiFoxRunErrorSceneEntity> {
    boolean updateToDel(Long id);
}
