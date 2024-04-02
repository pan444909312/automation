package com.miller.deliveryapp.constants;

import com.panda.common.enums.CountryEnum;

/**
 * 业务配置常量
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 17:27:34
 */
public class BusinessConstant {
    /**
     * 接口请求的域名
     */
    public static final String DOMAIN = "https://app-deliverytest.hungrypanda.cn";

    /**
     * 纬度
     */
    public static String latitude = "30.20353";
    /**
     * 经度
     */
    public static String longitude = "120.216552";
    public static String version = "5.23.0";
    public static String platform = "ANDROID_DELIVERY";
    public static String type = "3";
    public static String userAgent = "5.19.0";
    public static String locale = "zh-CN";
    public static String operatingSystem = "1";
    public static String brand = "HUAWEI";
    public static String uniqueToken = "202c7d3739966521";
    public static String appTypeId = "2";
    public static String countryCode = CountryEnum.CHINA.getCode();
    public static String deviceSafeToken = "a0_b1_c0_h0_i0_j0_m0_n0_p0_s0";
    // 默认值为请求头需要的名称，方便以后修改，不要修改默认值。
    public static String authorization = "authorization";
}
