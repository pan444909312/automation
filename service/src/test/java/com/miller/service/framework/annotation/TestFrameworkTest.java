package com.miller.service.framework.annotation;

import com.miller.service.framework.listenner.TestResultWatcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 测试框架注解测试
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/5/15 14:01:46
 */
@TestFramework
@DisplayName("Annotation of @TestFramework Testsuite")
public class TestFrameworkTest {
    @Test
    void testAnnotationOfTestFramework() {
//        throw new RuntimeException(";");
        System.out.println("Assert @TestFramework is running...");
    }
}
