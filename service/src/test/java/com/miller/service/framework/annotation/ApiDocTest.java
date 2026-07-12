package com.miller.service.framework.annotation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Miller Shan
 */
@ApiDoc("/project/60/interface/api/3040")
@TestFramework
@DisplayName("ApiDoc Testcase")
public class ApiDocTest {
    @Test
    @DisplayName("Update YApi website status.")
    void testApiDocAnnotation() {
    }
}
