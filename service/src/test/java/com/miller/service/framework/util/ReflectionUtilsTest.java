package com.miller.service.framework.util;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.miller.service.framework.util.ReflectionUtils.invokeMethod;
import static com.miller.service.framework.util.ReflectionUtils.invokeMethodOfNoParameter;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Miller Shan
 */
class ReflectionUtilsTest {

    @DisplayName("Test method of no parameter and void return value were invoked by Class name.")
    @Test
    void testNoParameterByClassName() {
        // 调用无参、void方法
        Object testMethodOfReturnVoid = invokeMethodOfNoParameter(ReflectionUtilsTest.class.getName(), "test");
        // void 方法返回值为 null
        assertThat(testMethodOfReturnVoid, Matchers.nullValue());
    }

    @DisplayName("Test method of no parameter and void return value were invoked by Class Object.")
    @Test
    void testNoParameterByClass() {
        // 调用无参、void方法
        Object testMethodOfReturnVoid = invokeMethodOfNoParameter(ReflectionUtilsTest.class, "test");
        // void 方法返回值为 null
        assertThat(testMethodOfReturnVoid, Matchers.nullValue());
    }

    @DisplayName("Test method of return void should return null value.")
    @Test
    void testMethodOfReturnVoid() {
        // 调用无参、void方法。 方法参数为空可以传 null 或者 new Object[]{}
        Object testMethodOfReturnVoid = invokeMethod(ReflectionUtilsTest.class.getName(), "test",
                // 方法参数及参数类型必须一致
                new Object[]{}, new Class[]{});
        // void 方法返回值为 null
        assertThat(testMethodOfReturnVoid, Matchers.nullValue());
    }

    @DisplayName("Test method of return String should return String value.")
    @Test
    void testMethodOfReturnString() {
        // 方法返回值为 String
        Object testMethodOfReturnString = invokeMethod(ReflectionUtilsTest.class, "testMethodOfReturnString",
                // 方法参数及参数类型必须一致
                new Object[]{"Miller"}, new Class[]{String.class});
        assertThat(String.valueOf(testMethodOfReturnString), Matchers.containsStringIgnoringCase("Miller"));
    }

    @DisplayName("Test Overloading method.")
    @Test
    void testMethodOfReturnStringButBeOverloading() {
        // 方法被重载
        Object overloadingMethod = invokeMethod(ReflectionUtilsTest.class, "testMethodOfReturnString",
                // 方法参数及参数类型必须一致
                new Object[]{18}, new Class[]{Integer.class});
        assertThat(String.valueOf(overloadingMethod), Matchers.containsStringIgnoringCase("18"));
    }

    @DisplayName("Test static method.")
    @Test
    void testMethodOfStatic() {
        // 静态方法
        Object testMethodOfStatic = invokeMethod(ReflectionUtilsTest.class, "testMethodOfStatic",
                // 方法参数及参数类型必须一致
                new Object[]{"Mila"}, new Class[]{String.class});
        assertThat(String.valueOf(testMethodOfStatic), Matchers.containsStringIgnoringCase("Mila"));
    }

    @DisplayName("Test private method.")
    @Test
    void testMethodOfPrivate() {
        // 私有方法
        Object testMethodOfPrivate = invokeMethod(ReflectionUtilsTest.class, "testMethodOfPrivate",
                // 方法参数及参数类型必须一致
                new Object[]{"Vicky"}, new Class[]{String.class});
        assertThat(String.valueOf(testMethodOfPrivate), Matchers.containsStringIgnoringCase("Vicky"));
    }

    @DisplayName("Test multiple parameters method.")
    @Test
    void testMethodOfMultipleParameters() {
        // 多参数方法
        Object testMethodOfMultipleParameters = invokeMethod(ReflectionUtilsTest.class, "testMethodOfMultipleParameters",
                // 方法参数及参数类型必须一致
                new Object[]{"Miller", 30}, new Class[]{String.class, Integer.class});
        assertThat(String.valueOf(testMethodOfMultipleParameters), Matchers.containsStringIgnoringCase("Miller"));
    }

    // JUnit 测试方法通常形式
    void test() {
        System.out.println("The method of void return value be invoked by reflection.");
    }

    // 带返回值的方法
    String testMethodOfReturnString(String name) {
        return "Hello:" + name;
    }

    // 方法重载
    String testMethodOfReturnString(Integer name) {
        return "Hello:" + name;
    }

    // 静态方法
    static String testMethodOfStatic(String name) {
        return "Hello:" + name;
    }

    // 私有方法
    private String testMethodOfPrivate(String name) {
        return "Hello:" + name;
    }

    // 多参数方法
    String testMethodOfMultipleParameters(String name, Integer age) {
        return String.format("Hello: %s, I am: %d year old.", name, age);
    }
}