package com.miller.pos.constants;

import com.panda.common.enums.CountryEnum;
import com.github.javafaker.Faker;

import static java.util.Locale.CHINESE;

/**
 * 业务配置常量
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/14 20:27:34
 */
public class BusinessConstant {
    Faker faker= new Faker(CHINESE);

    /**
     * 接口请求的域名
     */
    public static final String DOMAIN = "https://test-gateway.hungrypanda.cn";

    /**
     * 请求头公共参数.
     */
    public static String localshopid = "651600159";


    public static int limit = 100;

    public static String app_key = "6000001298850019";
    public static String app_secret = "fb907620b07f4cb29656c44dd0401c12";
    public static String pos_no="18768196431" ;
    public  static String shop_id="651600159";

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
    public static String countryCode = CountryEnum.CHINA.getCode();
    public static String appTypeId = "1";
    public static String uniqueToken = "381bb9f0ec5f3988";
    public static String devicemodel = "TAS-AN00";
    public static String devicebrand = "HUAWEI";
    // 默认值为请求头需要的名称，方便以后修改，不要修改默认值。
    public static String authorization = "lPWgzWw5aXp3iX0RCSN+vsUV/Li2K7vzxTAvEutqw+c=";
}
