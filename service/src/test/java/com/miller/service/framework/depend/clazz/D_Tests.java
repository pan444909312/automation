package com.miller.service.framework.depend.clazz;

import com.miller.service.framework.BasicTestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试类执行顺序，按照依赖顺序执行
 *
 */
public class D_Tests extends BasicTestCase {
    @Test
    void test() {
        System.out.println(this.getClass().getName());
        assertEquals(4, 4);
    }
}
