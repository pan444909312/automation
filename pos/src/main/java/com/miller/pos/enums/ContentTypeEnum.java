package com.miller.pos.enums;

import com.miller.common.util.StringUtils;
import com.miller.pos.constants.BusinessConstant;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

public enum ContentTypeEnum {

    JSON("application/json");

    private String contentType;


    ContentTypeEnum(String contentType) {
        this.contentType = contentType;
    }


    public Map<String, Object> toHeader(String token) {
        HashMap<String, Object> headerMap = new HashMap<>();

        if (!ObjectUtils.isEmpty(token)) {
            headerMap.put("Authorization", token);
        }
        headerMap.put("Content-Type", this.contentType);
        return headerMap;
    }

    public Map<String, Object> toHeader() {
        return this.toHeader(BusinessConstant.authorization);
    }
}