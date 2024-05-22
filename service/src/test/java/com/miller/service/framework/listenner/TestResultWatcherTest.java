package com.miller.service.framework.listenner;

import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@TestFramework
@DisplayName("TestResultWatcher Testsuite")
class TestResultWatcherTest {

    @Test
    void testSuccessful() {
        // do nothing...
    }
    @Test
    void testSuccessful2() {
        // do nothing...
    }

    @AfterAll
    static void afterAll() {
        System.out.println("执行成功的方法总数:");
    }
}