package com.miller.service.framework.listenner;

import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.*;

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

    @Test
    void testFailed() {
        Assertions.fail();
    }

    @Test
    void testFailed2() {
        Assertions.fail();
    }

    @Disabled
    @Test
    void testDisabled() {
        Assertions.assertTrue(true);
    }

    @Disabled
    @Test
    void testDisabled2() {
        Assertions.assertTrue(true);
    }

    @Test
    void testAborted() {
        // 测试中止通常是由于使用了如 assumeTrue(), assumeFalse() 等方法来设定执行的前提条件未满足，或者直接抛出了中断测试执行的异常。
        Assumptions.assumeTrue(false, () -> "testAborted2 is Aborted");
    }

    @Test
    void testAborted2() {
        Assumptions.assumeTrue(false, () -> "testAborted2 is Aborted");
    }


    @AfterAll
    static void afterAll() {
        System.out.println("执行成功的方法总数:" + TestResultWatcher.testCaseCountOfSuccessful);
        System.out.println("执行失败的方法总数:" + TestResultWatcher.testCaseCountOfFailed);
        System.out.println("执行禁用的方法总数:" + TestResultWatcher.testCaseCountOfDisabled);
        System.out.println("执行中止的方法总数:" + TestResultWatcher.testCaseCountOfAborted);
        System.out.println("执行方法总数:" + (
                TestResultWatcher.testCaseCountOfSuccessful +
                        TestResultWatcher.testCaseCountOfFailed +
                        TestResultWatcher.testCaseCountOfDisabled +
                        TestResultWatcher.testCaseCountOfAborted));
    }
}