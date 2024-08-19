package com.miller.merchant.util;

public class PropertiesUtils extends com.miller.service.framework.util.PropertiesUtils {

    public String getProperty(String key) {
        return super.getProperty(PropertiesUtils.class, key);
    }
}
