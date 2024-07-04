package com.miller.demo.constants;

import com.miller.service.framework.util.PropertiesUtils;

/**
 * 业务配置常量
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/1 20:27:34
 */
public class BusinessConstant {
    /**
     * admin 账号
     */
    public static final String USERNAME_ADMIN = PropertiesUtils.getProperty("demo.user.account.admin");
    /**
     * admin 密码
     */
    public static final String PASSWORD_ADMIN = PropertiesUtils.getProperty("demo.user.password.admin");

    /**
     * miller 账号
     */
    public static final String USERNAME_MILLER = "miller.shan@aliyun.com";
    /**
     * miller 密码
     */
    public static final String PASSWORD_MILLER = "123456";

}
