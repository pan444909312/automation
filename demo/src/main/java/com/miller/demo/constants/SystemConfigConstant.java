package com.miller.demo.constants;

import com.miller.service.framework.util.PropertiesUtils;

/**
 * 系统配置常量
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/1 20:58:25
 */
public class SystemConfigConstant {
    /**
     * 接口请求的域名
     */
     public static final String DOMAIN = PropertiesUtils.loadProperties().getProperty("demo.env.domain");
    // public static final String DOMAIN = "http://47.242.73.37/api";
    //public static final String DOMAIN = "http://localhost:7080/api";
}
