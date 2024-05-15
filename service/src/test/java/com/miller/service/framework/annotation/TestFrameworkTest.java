package com.miller.service.framework.annotation;

import org.junit.jupiter.api.Test;

/**
 * 测试框架注解测试
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/5/15 14:01:46
 */
@TestFramework
public class TestFrameworkTest {
    @Test
    void testAnnotationOfTestFramework() {
//        throw new RuntimeException(";");
        System.out.println("Assert @TestFramework is running...");
    }
}
