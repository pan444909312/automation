package com.miller.service.framework.annotation;

import org.skyscreamer.jsonassert.JSONCompareMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 测试框架断言
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/10/12 11:29:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Asserts {
    String[] jsonPath();

    /**
     * 校验 jsonPath 中的key与接口返回的是否匹配
     *
     * @return 成功返回true，失败返回false
     */
    boolean assertKey() default true;

    /**
     * 校验 jsonPath 中的value与接口返回的是否匹配
     *
     * @return 成功返回true，失败返回false
     */
    boolean assertValue() default true;

    /**
     * 比较模式，默认为严格模式,参考 {@link org.skyscreamer.jsonassert.JSONCompareMode}
     *
     * @return 成功返回true，失败返回false
     */
    JSONCompareMode compareMode();

}
