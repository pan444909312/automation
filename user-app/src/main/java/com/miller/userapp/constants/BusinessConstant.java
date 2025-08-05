package com.miller.userapp.constants;

import com.panda.common.enums.CountryEnum;
import com.panda.common.enums.LanguageEnum;

/**
 * 业务配置常量
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/06 20:27:34
 */
public class BusinessConstant {
    /**
     * 接口请求的域名
     */
    public static final String DOMAIN = "https://app-test.hungrypanda.cn";

    /**
     * 请求头公共参数.
     * 纬度
     */
    public static String latitude = "29.6605799";
    /**
     * 经度
     */
    public static String longitude = "115.9541";
    /**
     * 真实的纬度
     */
    public static String realLatitude = "30.20123";
    /**
     * 真实的经度
     */
    public static String realLongitude = "120.22141";
    /**
     * 邮编
     */
    public static String zipCode = "332000";
    /**
     * 版本号
     */
//    public static String version = "8.49.0";
    public static String version = "8.59.5";
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
    public static String platform = "ANDROID_USER";
    public static String cityName;
    public static String country;
    public static String appTypeId = "1";
    public static String authCode;
    public static String androidSafeToken;
    public static String uniqueToken = "auto-test-device";
    public static String unionId;
    public static String pandaAppId = "com.hungrypanda.waimai";
    // 默认给个空字符串
    public static String authorization = "";
    //    public static String testGroup = "I_R_TEST_GROUP,I_R_TEST_GROUP,SUPERMARKET_SCENES_TEST_GROUP," +
//            "S_H_R_L_TEST_GROUP_7,22,23,31,32,NUMBER_MASKING_00,33,34,35,40,39,45,52,54," +
//            "HPF,TESTE02,FASTD01,YSDCS02,YYYZM01,IST01,HYBQ01,SKEQ01,XRJ01,TJBQ01,JSYZQ02,HYXBQ01," +
//            "TJTCX01,YBXS02,CCPRO01,ZDFQ01,SKXRB01,ABT02,XRTC01,QYTCD01,SMSS02,XMLM01,RRREC02," +
//            "CCT01,ZFBMM01,SSJLY01,DFF01,SPSS01,MRBX01,ZNYX01";
    public static String testGroup = "I_R_TEST_GROUP,I_R_TEST_GROUP,SUPERMARKET_SCENES_TEST_GROUP," +
            "S_H_R_L_TEST_GROUP_3,22,23,29,30,31,32,NUMBER_MASKING_00,33,34,36,35,40,39,45,49,52,53,55,56," +
            "HPF,SKEQ02,PLCC02,SKXRB02,XRJ02,SKYS02,MGDD02,SKYH02,XDRS02,FASTD01,YSDCS02,IST01,HYBQ01," +
            "TJBQ01,HYXBQ01,TJTCX01,YBXS02,CCPRO01,ABT02,QYTCD01,SMSS01,XMLM01,RRREC01,ZFBMM01,SSJLY01," +
            "SPSS01,MRBX01,SXAU01,PAYTO01,LXTZ01,JQSJ01,SYGB01,JSYXR01,GDJ02,ZTKP01,ZKTS02,RTR01,SYUI01," +
            "SWS01,DWC01,HHAB01,YHTX01,TCZT01,XTZA01,QDJS01,XGBSS01,SYSKA02,WLTC01,SPM01,XGBFU01,SDDAB01," +
            "TCSHW01,SSXS01,JSYHA01,DPCDA01,DPHD01,YRSZT01,TSRW02,LLQX01,RDMU01,YHMGD01,NTCZT01,DPCDB01," +
            "HHSQ03,CZHG01,WLTCN01,ESFI02,ABCS01,DPYGB01,HBCY01,GWCYC01,HYUI01,SKBD02,GGCLA01,YFYHA01," +
            "XRSY01,HDMR01,SYMK01,CMRT01,CPYHA01,SKYX02,VOOPT01,YHLL01,YJSDA01,XGSPA01,LXCYH01,TCZKB01,LYYL02";
}
