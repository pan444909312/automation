package com.miller.service.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Miller Shan
 * @version 1.0
 * @see ApiDoc
 * @since 2023/10/9 18:13:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ApiDocs {
    ApiDoc[] value();
}