package com.miller.service.framework.annotation;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Tag用于区分环境
 *
 * <p>
 * 通过Tag标签区分测试用例所属的执行环境
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/9 19:13:07
 */
public interface EnvTag {
    /**
     * 自定义注解 Dev
     */
    @Tag("dev")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Dev {
    }

    @Tag("test")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Test {
    }

    @Tag("prod")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Prod {
    }

    //    @Tag("all")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Dev
    @Test
    @Prod
    @interface ALL {
    }
}
