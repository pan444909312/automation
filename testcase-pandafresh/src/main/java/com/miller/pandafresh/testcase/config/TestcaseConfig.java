package com.miller.pandafresh.testcase.config;

/**
 * 配置相关
 *
 */
public class TestcaseConfig {

    /**
     * 接口请求的域名
     */
    public static final String HOST = "https://test.pandafreshmart.com";

    /**
     * H5接口请求的域名
     */
    public static final String H5HOST = "https://api-cn-f2e-test.hungrypanda.cn";
    public static final String B2BH5HOST = "https://mobile-test.pandasupply.co/";

    public static final String HpHOST = "https://app-test.hungrypanda.cn";

    /**
     * 接口请求的端口
     */
    public static final int PORT = 80;
    public static final String HpfHost = "https://fresh-api-test.hungrypanda.cn";

    /**
     * Redis 配置
     */
    public static String redisHost = "r-3nscqny4art27v9hrzpd.redis.rds.aliyuncs.com";
    public static Integer redisPort = 6379;
    public static String redisPassword = "YNKAthEbNF3XoK8E";

    public  static final String HP_AUTH_KEY="hP*L8pp65_#1flvjk342589fdgjl34m";

    public static String shopCartId = "0";
    public static String goodsId = "0";
    public static String goodsSkuId = "0";
    public static String goodsCount = "1";
    public static String addressId = "0";
    public static String deliveryTime = "0";
    public static String deliveryDate = "0";
    public static String deliveryTimeId = "0";
    public static String takeTimeId = "0";
    public static String takeTime = "0";
    public static String takeDate = "0";

    public static String b2bCodeKey = "0";
    public static String b2bCode = "0";

    public static String b2bMessageCode = "0";

    public static String b2bAddressId = "0";
    public static String b2bDeliveryTime = "0";
    public static String b2bDeliveryDate = "0";

    public static String b2bShopCartId = "0";

    /**
     * b2b立即支付订单号
     */
    public static String b2bpayNowOrderSn = "PFB2BB2508181039368848";


    /**
     * 支付渠道浮动金额百分比
     */
    public static double floatingRate = 1.01;
    /**
     * 固定浮动金额 单位：分
     */
    public static double floatingAmount = 0.0;
    /**
     * 金额浮动类型 0按百分比浮动 1按固定金额浮动
     */
    public static  int floatingType = 0;

    /**
     * 支付渠道
     */
    public static String payChannel = "stripePay";

    /**
     * 支付渠道记录ID
     */
    public static String channelRecordId = "694362694935535616";

    /**
     * paymentMethodId
     */
    public static String paymentMethodId = "pm_1SOBcdB2BLLEGTseiyIECo6F";
    public static String publishableApiKey = "pk_test_TmuvqLAXY1drvf9AC3i2JgTE";
    public static String paymentIntentId = "pi_3SbzLNB2BLLEGTse102OsPsp";
    public static String clientSecret = "pi_3SbzLNB2BLLEGTse102OsPsp_secret_nXck8vf1HTetQjYIhhlPMUk7u";

}
