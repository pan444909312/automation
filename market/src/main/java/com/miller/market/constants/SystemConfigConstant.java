package com.miller.market.constants;

import com.miller.service.framework.util.ApplicationPropertiesUtils;

/**
 * 系统配置常量
 *
 */
public class SystemConfigConstant {
    /**
     * 接口请求的域名
     */
     public static final String DOMAIN = ApplicationPropertiesUtils.loadProperties().getProperty("fresh.env.domain");
    // public static final String DOMAIN = "https://test.pandafreshmart.com";
}
