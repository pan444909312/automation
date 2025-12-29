package com.miller.service.apifox;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.apifox.ApiFoxRunErrorSceneEntity;

public interface ApiFoxRunErrorSceneService extends IService<ApiFoxRunErrorSceneEntity> {

    ApiFoxRunErrorSceneEntity findByCaseId(Long apiFoxCaseId);

}
