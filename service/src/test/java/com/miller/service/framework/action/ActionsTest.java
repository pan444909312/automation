package com.miller.service.framework.action;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Miller Shan
 * @see Actions
 */
class ActionsTest {
    @Test
    void testEqualsAction() {
        Boolean result = (Boolean) Actions.executeKeyword(ActionsEnum.equals, new Object[]{1, 2});
        assertThat(result, Matchers.is(false));

        result = (Boolean) Actions.executeKeyword(ActionsEnum.equals, new Object[]{2, 2});
        assertThat(result, Matchers.is(true));
    }
}