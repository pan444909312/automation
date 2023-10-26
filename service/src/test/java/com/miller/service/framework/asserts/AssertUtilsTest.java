package com.miller.service.framework.asserts;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * @author Miller Shan
 */
class AssertUtilsTest {
    @Test
    void testMyAssertThat() {
        Boolean result = AssertUtils.assertThat(1, 1);
        MatcherAssert.assertThat(result, Matchers.is(true));

        result = AssertUtils.assertThat(1, 2);
        MatcherAssert.assertThat(result, Matchers.is(false));
    }
}