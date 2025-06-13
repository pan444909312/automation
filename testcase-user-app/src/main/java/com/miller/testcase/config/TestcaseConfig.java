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
    public static final String HOST_APP = "https://app-test.hungrypanda.cn";

    /**
     * 前端安全代理服务器。注意：前端实际请求是先发送到代理服务器 api-cn-f2e-test.hungrypanda.cn。例如： f2e-web-test.hungrypanda.cn
     */
    public static final String Host_Mobile = "https://api-cn-f2e-test.hungrypanda.cn";

    /**
     * Redis 配置
     */
    public static String redisHost = "r-3nscqny4art27v9hrzpd.redis.rds.aliyuncs.com";
    public static Integer redisPort = 6379;
    public static String redisPassword = "YNKAthEbNF3XoK8E";
}
