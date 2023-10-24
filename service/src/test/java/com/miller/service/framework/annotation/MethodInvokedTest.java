package com.miller.service.framework.annotation;

import com.miller.service.framework.dto.IssuesDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link MethodInvoked}
 *
 * @author Miller Shan
 * @see MethodInvoked
 */
@TestFramework
@DisplayName("TestCase for @MethodInvoked Annotation.")
public class MethodInvokedTest {

    @MethodInvoked(clazz = MethodInvokedTest.class, methodName = "test")
    @DisplayName("Test method of no parameter and void return value were invoked by Class Object.")
    @Test
    void testMethodInvoked() {
        // ReflectionUtils.invokeMethod(MethodInvokedTest.class, "test", new Object[]{}, new Class[]{});
    }

    @MethodInvoked(clazz = MethodInvokedTest.class, methodName = "methodOfReturnString", parameterTypes = {String.class})
    @DisplayName("Test method of String parameter.")
    @Test
    void testMethodParameterTypeIsString() {
        // 方法参数为 String 类型
    }

    @MethodInvoked(clazz = MethodInvokedTest.class, methodName = "methodOfReturnString", parameterTypes = {Integer.class})
    @DisplayName("Test Overloading method.")
    @Test
    void testMethodParameterTypeIsInteger() {
        // 方法参数为 Integer 类型
    }

    @MethodInvoked(clazz = MethodInvokedTest.class, methodName = "testMethodOfStatic", parameterTypes = {String.class})
    @DisplayName("Test static method.")
    @Test
    void testMethodOfStatic() {
    }

    @MethodInvoked(clazz = MethodInvokedTest.class, methodName = "testMethodOfMultipleParameters",
            parameterTypes = {String.class, Integer.class})
    @DisplayName("Test multiple parameters method.")
    @Test
    void testMethodOfMultipleParameters() {
    }

    @MethodInvoked(clazz = MethodInvokedTest.class, methodName = "testMethodOfParameterIsObject",
            parameterTypes = {String.class, IssuesDTO.class})
    @DisplayName("Test method parameters is Java Bean.")
    @Test
    void testMethodOfParameterIsObject() {
    }

    // JUnit 测试方法通常形式
    void test() {
        System.out.println("The method of void return value be invoked by reflection.");
    }

    // 带返回值的方法
    String methodOfReturnString(String name) {
        System.out.println("-------------- String type " + name);
        return "Hello:" + name;
    }

    // 方法重载
    String methodOfReturnString(Integer name) {
        System.out.println("-------------- Integer type " + name);
        return "Hello:" + name;
    }

    // 静态方法
    static String testMethodOfStatic(String name) {
        System.out.println("-------------- static method " + name);
        return "Hello:" + name;
    }

    // 私有方法
    private String testMethodOfPrivate(String name) {
        System.out.println("-------------- private method " + name);
        return "Hello:" + name;
    }

    // 多参数方法
    String testMethodOfMultipleParameters(String name, Integer age) {
        System.out.println("-------------- Multiple Parameters " + name);
        return String.format("Hello: %s, I am: %d year old.", name, age);
    }

    // 多参数方法
    String testMethodOfParameterIsObject(String name, IssuesDTO issuesDTO) {
        issuesDTO = new IssuesDTO();
        System.out.println("-------------- Parameters is Java Object " + issuesDTO);
        return String.format("Hello: %s, I am: %s year old.", name, issuesDTO);
    }
}
