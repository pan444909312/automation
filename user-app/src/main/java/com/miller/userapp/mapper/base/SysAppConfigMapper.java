package com.miller.userapp.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.entity.config.SysAppConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author panjuxiang
 * @since 2024/8/28 18:18
 */
@Mapper
public interface SysAppConfigMapper extends BaseMapper<SysAppConfigEntity> {
}
