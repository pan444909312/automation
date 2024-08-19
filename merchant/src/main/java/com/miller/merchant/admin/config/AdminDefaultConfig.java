package com.miller.merchant.admin.config;


import com.miller.merchant.util.PropertiesUtils;
import lombok.Data;

public  class AdminDefaultConfig {

    private static PropertiesUtils properties;

    static {
         properties = new PropertiesUtils();
    }



    /**
     * 接口请求的域名
     */
    public static final String DOMAIN = properties.getProperty("merchant.platform.domain") ;


    /**
     * 店铺ID
     */
    public static final String shopId = properties.getProperty("merchant.platform.shopid") ;


}
