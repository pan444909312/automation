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

    /**
     * 获取当前操作系统主机名
     *
     * @return 当前操作系统主机名
     */
    public static String getHostNameOfOS() {
        return CommandUtils.executeCommand("hostname");
    }

    /**
     * 获取当前操作系统名称
     *
     * @return 操作系统的名称，例如: Windows xxx, Mac xxx ,  Linux xxx, Unix xxx, SunOS xxx等
     */
    public static String getOSName() {
        return System.getProperty("os.name");
    }

    /**
     * 判断当前操作系统是否是Windows
     *
     * @return true:是Windows操作系统; false:不是Windows操作系统
     */
    public static boolean isWindows() {
        return getOSName().toLowerCase().startsWith("windows");
    }

    /**
     * 判断当前操作系统是否是Mac
     *
     * @return true:是Mac操作系统; false:不是Mac操作系统
     */
    public static boolean isMac() {
        return getOSName().toLowerCase().startsWith("mac");
    }

    public static boolean isLinux() {
        return getOSName().toLowerCase().startsWith("linux");
    }
}
