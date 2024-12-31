package com.miller.service.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 测试场景注解,用于测试框架识别测试用例为场景。并且自动统计分析每个场景的开发、维护、人工成本，计算最终ROI。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/27 21:30:41
 */
@TestFramework
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Scenario {
    /**
     * 场景ID，使用 {@link com.miller.common.util.ULIDUtils} 工具生成唯一ID,参考{@code ULIDUtilsTests.java}
     */
    String scenarioID();

    /**
     * 场景名称
     */
    String scenarioName();

    /**
     * 自动化开发成本: 单位分钟
     * <ul>最终的值单位是分钟，写法可<b>参考</b>如下格式：
     *     <li>10 分钟: developmentTime = 10</li>
     *     <li>1小时: developmentTime = 60</li>
     *     <li>2小时: developmentTime = 2 * 60</li>
     *     <li>2小时: developmentTime = 120</li>
     *     <li>8小时: developmentTime = 8 * 60</li>
     *     <li>8小时: developmentTime = 480</li>
     * </ul>
     */
    int developmentTime();

    /**
     * 自动化维护成本: 单位分钟
     * <ul>最终的值单位是分钟，写法可<b>参考</b>如下格式：
     *     <li>10 分钟: maintenanceTime = 10</li>
     *     <li>1小时: maintenanceTime = 60</li>
     *     <li>2小时: maintenanceTime = 2 * 60</li>
     *     <li>2小时: maintenanceTime = 120</li>
     *     <li>8小时: maintenanceTime = 8 * 60</li>
     *     <li>8小时: maintenanceTime = 480</li>
     *
     * </ul>
     */
    int maintenanceTime();

    /**
     * 手工测试成本: 单位分钟
     * <ul>最终的值单位是分钟，写法可<b>参考</b>如下格式：
     *     <li>10 分钟: manualTestTime = 10</li>
     *     <li>1小时: manualTestTime = 60</li>
     *     <li>2小时: manualTestTime = 2 * 60</li>
     *     <li>2小时: manualTestTime = 120</li>
     *     <li>8小时: manualTestTime = 8 * 60</li>
     *     <li>8小时: maintenanceTime = 480</li>
     * </ul>
     */
    int manualTestTime();

    /**
     * 作者: 格式为公司邮箱
     */
    String author();
}
