package com.miller.market.constants;

import com.miller.service.framework.util.PropertiesUtils;

/**
 * 系统配置常量
 *
 */
public class SystemConfigConstant {
    /**
     * 接口请求的域名
     */
     public static final String DOMAIN = PropertiesUtils.getProperty("fresh.env.domain");
    // public static final String DOMAIN = "https://test.pandafreshmart.com";
}
