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
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Scenario {
    /**
     * 场景ID，使用 ULID 工具生成唯一ID
     */
    String scenarioID();

    /**
     * 场景名称
     */
    String name();
}
