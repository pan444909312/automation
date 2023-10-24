package com.miller.service.framework.lifecycle;

import com.miller.service.framework.annotation.MethodInvoked;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.depend.DependsOnMethod;
import org.junit.jupiter.api.Test;

/**
 * 测试测试用例执行之前的回调
 * <p>
 * {@link MethodInvoked @MethodInvoked} 与 {@link  DependsOnMethod  @DependsOnMethod}区别: <br>
 * 1. {@link MethodInvoked @MethodInvoked} 是通过 Java 的反射调用方法，被执行的测试方法可以是任意 Java 中的方法，
 * 而 {@link  DependsOnMethod  @DependsOnMethod} 是对 JUnit 5 方法执行顺序的处理，被依赖的方法必须是当前类中的方法。 <br>
 * 2. {@link MethodInvoked @MethodInvoked} 执行方法时对于方法的签名有明确要求，因为Java中会存在方法重载，
 * 而 {@link  DependsOnMethod  @DependsOnMethod} 执行时只需要方法的名称即可。<br>
 * 3. {@link MethodInvoked @MethodInvoked} 执行方法会被当做普通方法执行，
 * 而{@link  DependsOnMethod  @DependsOnMethod} 执行时是通过 JUnit 5 Launcher 去发现执行用例的。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0.0
 * @see DependsOnMethod
 * @see MethodInvoked
 * @since 2023/10/23 21:20:33
 */
@TestFramework
public class BeforeTestExecutionTest {

    @MethodInvoked(clazz = BeforeTestExecutionTest.class, methodName = "test2")
    @DependsOnMethod("test2")
    @Test
    void test1() {
        System.out.println("test1()>>>>>>>>>>>");
    }

    @MethodInvoked(clazz = BeforeTestExecutionTest.class, methodName = "test3")
    @DependsOnMethod("test3")
    @Test
    void test2() {
        System.out.println("test2()>>>>>>>>>>>");
    }

    @Test
    void test3() {
        System.out.println("test3()>>>>>>>>>>>");
    }
}
