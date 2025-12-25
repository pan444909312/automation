package com.miller.service.enums;

import lombok.Getter;

/**
 * ApiFox 环境配置ID 枚举
 */
@Getter
public enum ApiFoxEnvEnum {

    D_ENV("345654"),
    TEST_ENV("345508")

    ;

    private final String envId;

    ApiFoxEnvEnum(String envId) {
        this.envId = envId;
    }
}
