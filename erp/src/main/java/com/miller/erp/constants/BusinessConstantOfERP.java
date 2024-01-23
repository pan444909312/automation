package com.miller.erp.constants;

/**
 * 业务配置常量
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 17:27:34
 */
public class BusinessConstantOfERP {
    /**
     * 接口请求的域名
     */
    public static final String DOMAIN_TEST_GATEWAY = "https://test-gateway.hungrypanda.cn";

    /**
     * ERP 账号
     */
    public static final String USERNAME = "dongdong_test";
    /**
     * ERP 账号密码，MD5 加密
     */
    public static final String PASSWORD = "9078a56f98d2ac683c8720570c829ac9";

    /**
     * token: ERP 认证令牌
     */
    public static String TOKEN = "";

    /**
     * ERP 后台老的项目接口请求域名。
     * 前后端不分离的项目，代码工程在 platform
     */
    public static final String DOMAIN_TEST_BACKUP ="https://platform-test-backup.hungrypanda.cn";
}
