package com.miller.service.apifox;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.apifox.ApiFoxConfigEntity;
import com.miller.service.apifox.enums.AttributionGroupEnum;

public interface ApiFoxConfigService  extends IService<ApiFoxConfigEntity> {

    String getGroupConfig(AttributionGroupEnum groupEnum);

}
