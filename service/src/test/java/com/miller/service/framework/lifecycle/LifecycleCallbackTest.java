package com.miller.service.framework.lifecycle;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Miller Shan
 * @version 1.0.0
 * @see LifecycleCallback
 * @since 2023/10/23 21:30:33
 */
@ExtendWith(LifecycleCallback.class)
class LifecycleCallbackTest {

    @DisplayName("Lifecycle callback testing.")
    @Test
    void test() {
        System.out.println("test ============================>>>>");
    }

    @Disabled
    @DisplayName("When this method cause Exception, handleTestExecutionException() will be invoked.")
    @Test
    void testExecuteCauseException() {
        System.out.println("handleTestExecutionException ============================>>>>");
        throw new UnsupportedOperationException("handleTestExecutionException ============================>>>>");
    }
}