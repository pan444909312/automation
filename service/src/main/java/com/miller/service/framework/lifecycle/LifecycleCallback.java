package com.miller.service.framework.lifecycle;

import com.miller.service.framework.annotation.MethodInvoked;
import com.miller.service.framework.apidoc.YApiUtils;
import com.miller.service.framework.listenner.TestResultWatcher;
import com.miller.service.framework.util.ReflectionUtils;
import org.junit.jupiter.api.extension.*;

import java.util.Objects;

/**
 * JUnit 生命周期回调。
 * <p>
 * <pre>
 *         BeforeAllCallback,
 *             BeforeEachCallback,
 *                 // @Test 方法执行之前回调
 *                 BeforeTestExecutionCallback,
 *                     // @Test 执行执行发生异常之后回调
 *                     TestExecutionExceptionHandler,
 *                 // @Test 方法执行之后回调
 *                 AfterTestExecutionCallback,
 *             AfterEachCallback,
 *         AfterAllCallback,
 *         // 下面这三个不太常用，用于测试类创建、处理、销毁时回调
 *         TestInstanceFactory,
 *         TestInstancePostProcessor,
 *         TestInstancePreDestroyCallback
 * </pre>
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/18 11:03:13
 */
public class LifecycleCallback implements
        BeforeAllCallback,
        BeforeEachCallback,
        BeforeTestExecutionCallback,
        TestExecutionExceptionHandler,
        AfterTestExecutionCallback,
        AfterEachCallback,
        AfterAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        System.out.println(this.getClass().getName() + " beforeAll callback invoked.");
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        System.out.println(this.getClass().getName() + " afterAll() callback invoked.");
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        System.out.println(this.getClass().getName() + " beforeEach() callback invoked.");
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        System.out.println(this.getClass().getName() + " afterEach() callback invoked.");
    }

    /**
     * 在 @Test 方法执行之前调用此方法
     */
    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        // @Test方法执行之前调用
        System.out.println(this.getClass().getName() + " beforeTestExecution() callback invoked.");
        MethodInvoked methodInvokedAnnotation = extensionContext.getTestMethod().get().getDeclaredAnnotation(MethodInvoked.class);
        if (Objects.nonNull(methodInvokedAnnotation)) {
            Class<?> clazz = methodInvokedAnnotation.clazz();
            String methodName = methodInvokedAnnotation.methodName();
            String[] args = methodInvokedAnnotation.parameterArgs();
            Class<?>[] parameterTypes = methodInvokedAnnotation.parameterTypes();

            ReflectionUtils.invokeMethod(clazz, methodName, args, parameterTypes);
        }
    }

    /**
     * 在 @Test 方法执行之后调用此方法
     */
    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        //@Test方法执行之后调用
        System.out.println(this.getClass().getName() + " afterTestExecution() callback invoked.");
    }

    /**
     * 当 @Test 方法执行时发生异常后会执行此方法.
     * 调用顺序为: @Test -> handleTestExecutionException()
     */
    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        System.out.println(this.getClass().getName() + " handleTestExecutionException() callback invoked.");
        // 执行发生异常时把异常抛出来
        throw throwable;
    }
}
