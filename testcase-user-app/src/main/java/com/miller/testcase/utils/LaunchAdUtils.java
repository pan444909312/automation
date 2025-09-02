package com.miller.testcase.utils;

/**
 * @Author: panjuxiang
 * @Since: 2025/9/2
 */
public class LaunchAdUtils {


    /**
     * 将传入城市的启动页广告状态都改为未发布
     *
     * @param city
     */
    public static void initCityLaunchAd(String city) {
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(" update hp_launch_ad set state = 0 where city = \"" + city + "\"");

    }
}
