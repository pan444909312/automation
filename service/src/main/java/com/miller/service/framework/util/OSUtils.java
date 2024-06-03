package com.miller.service.framework.util;

/**
 * 操作系统相关工具类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/5/15 17:33:14
 */
public class OSUtils {
    /**
     * 获取当前操作系统用户名
     *
     * @return 当前操作系统用户名
     */
    public static String getUserNameOfOS() {
        return System.getProperty("user.name");
    }
}
