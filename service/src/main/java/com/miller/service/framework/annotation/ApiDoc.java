package com.miller.service.framework.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 框架与接口文档交互处理器
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/9 18:05:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ApiDoc {
    String value();
}
