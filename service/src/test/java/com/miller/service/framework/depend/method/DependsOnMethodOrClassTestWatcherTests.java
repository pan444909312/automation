package com.miller.service.framework.depend.method;

import com.miller.service.framework.BasicTestCase;
import com.miller.service.framework.depend.DependsOnClass;
import com.miller.service.framework.depend.DependsOnMethod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// @DependsOnClass(DependsOnMethodAnnotationTests.class)
public class DependsOnMethodOrClassTestWatcherTests extends BasicTestCase {
    static int counter = 0;

    @AfterEach
    void increaseCounter() {
        counter += 1;
    }

    @Test
    void independentTest() {
        assertEquals(0, counter, "Independent tests should run first");
    }

    @Test
    void alpha() {
        assertEquals(1, counter);
    }

    @Test
    @DependsOnMethod("alpha")
    void beta() {
        assertEquals(2, counter);
    }


    @Test
    @DependsOnMethod("beta")
    void delta() {
        assertEquals(3, counter);
    }

    @Test
    @DependsOnMethod("delta")
    void gamma() {
        assertEquals(4, counter);
    }
}
