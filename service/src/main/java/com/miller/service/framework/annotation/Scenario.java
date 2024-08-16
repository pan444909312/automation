package com.miller.service.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 测试场景注解
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
     * 开发成本: 单位分钟
     * 例如: 10 分钟: developmentTime = 1 * 10; 1小时: developmentTime = 1 * 60;
     */
    int developmentTime();

    /**
     * 维护成本: 单位分钟
     * 例如: 10 分钟: maintenanceTime = 1 * 10; 1小时: maintenanceTime = 1 * 60;
     */
    int maintenanceTime();

    /**
     * 人工测试成本: 单位分钟
     * 例如: 10 分钟: manualTestTime = 1 * 10; 1小时: manualTestTime = 1 * 60;
     */
    int manualTestTime();
}
