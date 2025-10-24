package com.miller.service.apifox.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.apifox.ApiFoxConfigEntity;
import com.miller.mapper.apifox.ApiFoxConfigMapper;
import com.miller.service.apifox.ApiFoxConfigService;
import com.miller.service.apifox.enums.AttributionGroupEnum;
import org.springframework.stereotype.Service;

@Service
public class ApiFoxConfigServiceImpl extends ServiceImpl<ApiFoxConfigMapper, ApiFoxConfigEntity> implements ApiFoxConfigService {

    @Override
    public String getGroupConfig(AttributionGroupEnum groupEnum) {
        QueryWrapper<ApiFoxConfigEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_code",groupEnum.name()).last("limit 1");
        String targetSql = queryWrapper.getTargetSql();

        ApiFoxConfigEntity entity = this.baseMapper.selectOne(queryWrapper,false);
        return entity.getConfigValue();
    }

}
