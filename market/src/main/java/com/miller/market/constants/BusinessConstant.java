package com.miller.market.constants;

import com.panda.common.enums.CountryEnum;
import com.panda.common.enums.LanguageEnum;

/**
 * 业务配置常量
 *
 */
public class BusinessConstant {

    /**
     * PF接口请求的域名
     */
    public static final String DOMAIN = "https://test.pandafreshmart.com";


    /**
     * 请求头公共参数.
     * 站点
     */
    public static String portalId = "3";

    /**
     * 请求头公共参数.
     * 配送区域
     */
    public static String regionId = "3";

    /**
     * 请求头公共参数.
     * 城市id
     */
    public static String cityId = "755";

    /**
     * 请求头公共参数.
     * 纬度
     */
    public static String latitude = "30.203476";
    /**
     * 经度
     */
    public static String longitude = "120.21713";
    /**
     * 设备号
     */
    public static String deviceNumber = "BE30531E-E2CB-4289-B3F1-4D7BF08570BA";

    public static String userAgent = "PandaFresh/4.25.1 (iPhone; iOS 15.2; Scale/3.00)";

    /**
     * 版本号
     */
    public static String version = "4.24.0";

    public static String system = "PandaFresh";
    /**
     * 页码
     */
    public static String pageNo;
    /**
     * 每页数量
     */
    public static String pageSize;
    public static String countryCode = CountryEnum.CHINA.getCode();
    public static String language = LanguageEnum.CN.getKey();
    public static String platform = "IOS";

    public static String _sign = "3f6d878d3bf68ac4f0425449e6c96d07";
    public static String _ts = "1715219615843";
    public static String cityName;
    public static String country;
    public static String appTypeId = "1";
    public static String authCode;
    public static String androidSafeToken;
    public static String uniqueToken;
    public static String unionId;
    public static String pandaAppId = "com.hungrypanda.waimai";
    // 默认给个空字符串
    public static String authorization = "";

    //pfabtest
    public static String abTestGroups = "101,102,103,104,105,106";


}
