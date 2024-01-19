package com.miller.service.framework.asserts;

import com.miller.service.framework.action.Actions;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;

/**
 * 封装断言
 *
 * @author Miller Shan
 * @version 1.0.0
 * @see Actions
 */
@Slf4j
public class AssertUtils {
    /**
     * 已废弃，没必要自己实现一套断言
     * @param actual 实际结果
     * @param expected 期望结果
     * @return 断言结果
     */
    @Deprecated
    public static Boolean assertThat(Object actual, Object expected) {
        log.debug("开始断言: {} 与 {} 是否相等", actual, expected);
        boolean result = false;
        try {
            // 底层断言使用 junit5 断言
            org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
            // 底层断言使用 assertj 流式断言
            org.assertj.core.api.Assertions.assertThat(actual).isEqualTo(expected);
            // 底层断言使用 hamcrest
            org.hamcrest.MatcherAssert.assertThat(actual, Matchers.is(expected));
            result = true;
        } catch (AssertionError e) {
            log.error("期望与实际不符，期望的是 [" + expected + "] 但是找到了 [" + actual + "]");
            result = false;
        } finally {
            log.debug("结束断言:{} 与 {} 是否相等, 断言结果为:{}", actual, expected, result);
            return result;
        }
    }
}
