package com.miller.merchant.constants;

import com.panda.iam.server.api.constant.CountryEnum;

import java.math.BigDecimal;

/**
 * 业务配置常量
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/14 20:27:34
 */
public class ZlBusinessConstant {
    /**
     * 接口请求的域名
     */
    public static final String DOMAIN = "https://app-merchantest.hungrypanda.cn";

    /**
     * 请求头公共参数.  店铺：川流不息
     */
    public static String localshopid = "664504868";
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
    public static String productname = "小猪乔治";
    public static long menuid= 4149472 ;
    public static int productprice=20;
    public static int limit= 1 ;
    public static int productlabel= 1 ;
    public static int taxRate= 1 ;
    public static int productBuyLimitMin= 1;
    public static int status= 0;



}
