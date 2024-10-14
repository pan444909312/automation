package com.miller.service.framework.annotation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Miller Shan
 * @version 1.0
 * @since 2024/10/14 10:31:00
 */
@TestFramework
@DisplayName("断言JSON")
public class AssertsTest {

    @Asserts(jsonPath = "xxx.json", compareMode = JSONCompareMode.STRICT)
    @Test
    void test() {
        Assertions.assertAll(() -> {
        });
    }
}
