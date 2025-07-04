package com.miller.service.report.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.report.ConfigEntity;
import com.miller.mapper.report.ConfigMapper;
import com.miller.service.report.ConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统配置表 服务实现类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-12-31
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, ConfigEntity> implements ConfigService {

    @Autowired
    ConfigMapper configMapper;

    @Override
    public String getConfigByKey(String key) {

        ConfigEntity configEntity = configMapper.selectOne(new LambdaQueryWrapper<ConfigEntity>().eq(ConfigEntity::getConfigKey, key));

        if (configEntity != null)
            return configEntity.getConfigValue();
        else
            return null;
    }
}
