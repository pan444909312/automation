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
     * 自定义注解 DEV, 必须大写，在 Launcher 里做了强制转换为大写
     */
    @Tag("DEV")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Dev {
    }

    @Tag("TEST")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Test {
    }

    @Tag("PROD")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Prod {
    }

    //    @Tag("ALL")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Dev
    @Test
    @Prod
    @interface ALL {
    }
}
