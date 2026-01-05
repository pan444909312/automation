package com.miller.testcase.config;

/**
 * 配置相关
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/5/27 11:28:26
 */
public class TestcaseConfig {

    /**
     * 接口请求的域名
     */
    public static final String HOST_USER_APP = "https://app-test.hungrypanda.cn";
    public static final String HOST_DELIVERY_APP = "https://app-deliverytest.hungrypanda.cn";

    /**
     * 测试环境ERP接口请求的域名
     */
    public static final String HOST_ERP = "https://test-gateway.hungrypanda.cn";


    /**
     * Redis 配置
     */
    public static String redisHost = "r-3nscqny4art27v9hrzpd.redis.rds.aliyuncs.com";
    public static Integer redisPort = 6379;
    public static String redisPassword = "YNKAthEbNF3XoK8E";
}
