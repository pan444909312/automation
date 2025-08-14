package com.miller.userapp.mapper.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.entity.data.AdsSearchDistanceMatrixEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 *两坐标距离表
 * @author heyuan
 * @version 1.0
 * @since 2024/9/25 20:07
 */
@Mapper
public interface AdsHpSearchDistanceMatrix extends BaseMapper<AdsSearchDistanceMatrixEntity> {
}
