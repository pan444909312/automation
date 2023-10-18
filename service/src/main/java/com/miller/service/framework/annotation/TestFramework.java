package com.miller.service.framework.annotation;

import com.miller.service.framework.depend.DependsOnClassAnnotation;
import com.miller.service.framework.depend.DependsOnMethodAnnotation;
import com.miller.service.framework.lifecycle.LifecycleCallback;
import com.miller.service.framework.listenner.TestResultWatcher;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

/**
 * 启用测试框架的入口注解。测试框架提供对测试用例的增强. 比如: 类、方法执行顺序; 监听测试执行过程; 监听测试执行结果; 生命周期方法回调等功能。
 *
 * <p>
 * - 测试方法执行依赖顺序{@link TestMethodOrder}。 <br>
 * - 测试类执行依赖顺序 {@link TestClassOrder}。 <br>
 * - 测试结果监听器 {@link TestResultWatcher}。 <br>
 * - JUnit5 生命周期回调 {@link LifecycleCallback}。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/16 20:33:21
 */
@ExtendWith({TestResultWatcher.class, LifecycleCallback.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestClassOrder(DependsOnClassAnnotation.class)
@TestMethodOrder(DependsOnMethodAnnotation.class)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestFramework {
}
