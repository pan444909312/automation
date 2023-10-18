package com.miller.service.framework.depend;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 处理测试类之间的依赖的注解
 *
 * @author Miller Shan
 * @version 1.0
 * @see DependsOnClassAnnotation
 * @since 2023/10/17 14:22:59
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DependsOnClass {
    /**
     * 依赖的类列表，被依赖的类会优先执行.限制类必须继承自BasicTestCase
     */
    //Class<? extends BasicTestCase>[] value();
    Class[] value();
}
