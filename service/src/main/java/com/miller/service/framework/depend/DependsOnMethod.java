package com.miller.service.framework.depend;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 处理测试方法之间的依赖的注解
 *
 * @author Miller Shan
 * @version 1.0
 * @see DependsOnMethodAnnotation
 * @since 2023/10/17 15:22:59
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DependsOnMethod {

    /**
     * 依赖的方法列表，被依赖的方法会优先执行
     */
    String[] value();
}
