package com.miller.service.framework.depend;

import com.miller.service.framework.listenner.TestResultWatcher;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 处理{@link DependsOnMethod @DependsOnMethod} 和 {@link DependsOnClass @DependsOnClass} 在依赖的链中，中间有一个失败则后面都标识为禁用。
 * 此监听器处理逻辑已经整合到了{@link TestResultWatcher}
 *
 * @author Miller Shan
 * @version 1.0
 * @see DependsOnMethod
 * @see DependsOnClass
 * @since 2023/10/17 17:24:58
 */
@Deprecated
public class DependsOnMethodOrClassTestWatcher implements ExecutionCondition, TestWatcher {
    /**
     * 存储成功的测试方法
     */
    private Set<String> successfulTestMethods = new HashSet<>();
    /**
     * 存储失败的类
     */
    private static Set<String> failedTestClasses = new HashSet<>();

    @Override
    public void testSuccessful(ExtensionContext context) {
        context.getTestMethod().ifPresent(method -> successfulTestMethods.add(method.getName()));
    }

    // 处理失败的方法，将失败的方法对应的类找出来。被禁用和忽略的暂时不处理，如果要处理重写testDisabled()和testAborted()即可，逻辑相同
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        // 如果类中的某一个方法失败了，那么认为这个类也执行失败了
        String failedClassName = context.getTestClass().orElse(null).getName();
        failedTestClasses.add(failedClassName);
    }

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        // 处理测试方法依赖
        Method method = context.getTestMethod().orElse(null);
        if (method != null) {
            DependsOnMethod annotation = method.getAnnotation(DependsOnMethod.class);
            if (annotation != null) {
                // 执行失败的方法
                Optional<String> unsuccessfulMethod = Arrays.stream(annotation.value())
                        .filter(name -> !successfulTestMethods.contains(name)).findAny();
                if (unsuccessfulMethod.isPresent()) {
                    // 标识方法为 disabled
                    return ConditionEvaluationResult.disabled(
                            String.format("'%s()' 不能执行，因为它依赖的测试方法 '%s()' 执行失败或者未执行!",
                                    method.getName(), unsuccessfulMethod.get()));
                }
            }
        }

        // 处理测试类依赖
        Class<?> currentExecuteClass = context.getTestClass().orElse(null);
        if (currentExecuteClass != null) {
            // 获取当前执行类上的 DependsOnClass 注解
            DependsOnClass annotation = currentExecuteClass.getAnnotation(DependsOnClass.class);
            if (annotation != null) {
                // 获取注解上的所有类
                Class[] classes = annotation.value();
                // 遍历注解里面的类
                for (Class classInAnnotation : classes) {
                    // 如果失败类集合中包含了当前依赖的类，则将此类设置为 disabled
                    if (failedTestClasses.contains(classInAnnotation.getName())) {
                        // 将不能执行的类添加到失败类的集合当中
                        failedTestClasses.add(currentExecuteClass.getName());
                        return ConditionEvaluationResult.disabled(
                                String.format("'%s()' 不能执行，因为它依赖的测试类 '%s()' 执行失败或者未执行!",
                                        currentExecuteClass.getName(), classInAnnotation.getName()));
                    }
                }
            }
        }
        // 继续执行
        return ConditionEvaluationResult.enabled("Enable by Default");
    }
}

