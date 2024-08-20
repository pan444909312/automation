package com.miller.merchant.admin.enums;

import lombok.Getter;

@Getter
public enum ContentTypeEnum {
    JSON("application/json"),
    TEXT_HTML("text/html")


 ;


    ContentTypeEnum(String value) {
        this.value = value;
    }

    private String value;
}
