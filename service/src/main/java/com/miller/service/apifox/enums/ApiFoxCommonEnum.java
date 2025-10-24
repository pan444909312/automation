package com.miller.service.apifox.enums;

import lombok.Getter;

@Getter
public enum ApiFoxCommonEnum {
    APIFOX_PROJECT_ID("345145")
    ;

    private String value;

    private ApiFoxCommonEnum(String value){
        this.value = value;
    }
}
