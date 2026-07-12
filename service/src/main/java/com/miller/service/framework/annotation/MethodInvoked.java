package com.miller.service.framework.annotation;

import com.miller.service.framework.depend.DependsOnMethod;
import com.miller.service.framework.lifecycle.LifecycleCallback;

import java.lang.annotation.*;

/**
 * 此注解用于修饰在被测方法之上，实现执行{@link org.junit.jupiter.api.Test @Test}方法之前先执行指定的方法。
 * 通过生命周期回调callback在测试方法执行之前进行回调处理。
 * 反射的方式虽然可以调用任意的方法，但是被调用的方法不属于JUnit管理的@Test方法了。建议使用{@link DependsOnMethod}替代，非侵入方式。
 *
 * @author Miller Shan
 * @version 1.0
 * @see LifecycleCallback
 * @since 2023/10/18 14:53:37
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodInvoked {
    /**
     * 类名
     */
    Class<?> clazz();

    /**
     * 方法名
     */
    String methodName();

    /**
     * 方法参数。方法实参应该在运行期间获得，而不是通过注解传递。
     * 而且通过注解传参有类型限制，类型只能为“常量”值。
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.16.1">Table 4.7.16.1-A. Interpretation of tag values as types</a>
     */
    @Deprecated String[] parameterArgs() default {};

    /**
     * 方法参数类型. 默认为空这样调用无参方法可以不用传。
     */
    Class<?>[] parameterTypes() default {};
}
