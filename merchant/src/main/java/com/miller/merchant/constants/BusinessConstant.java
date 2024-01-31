package com.miller.merchant.constants;

import com.panda.iam.server.api.constant.CountryEnum;

/**
 * 业务配置常量
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/14 20:27:34
 */
public class BusinessConstant {
    /**
     * 接口请求的域名
     */
    public static final String DOMAIN = "https://app-merchantest.hungrypanda.cn";

    /**
     * 请求头公共参数.
     */
    public static String localshopid = "59750820";
    /**
     * 纬度
     */
    public static String latitude = "30.20353";
    /**
     * 经度
     */
    public static String longitude = "120.216552";
    public static String userAgent = "4.12.15";
    public static String locale = "zh-CN";
    public static String version = "4.12.15";
    public static String systemversion = "12";
    public static String devicesn = "unknown";
    public static String platform = "ANDROID";
    public static String countryCode = CountryEnum.CN.getCode();
    public static String appTypeId = "1";
    public static String uniqueToken = "381bb9f0ec5f3988";
    public static String devicemodel = "TAS-AN00";
    public static String devicebrand = "HUAWEI";
    // 默认值为请求头需要的名称，方便以后修改，不要修改默认值。
    public static String authorization = "authorization";
}
