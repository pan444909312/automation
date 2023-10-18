package com.miller.service.framework.depend.clazz;

import com.miller.service.framework.BasicTestCase;
import com.miller.service.framework.depend.DependsOnClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试类执行顺序，按照依赖顺序执行
 */
@DependsOnClass(D_Tests.class)
public class C_Tests extends BasicTestCase {
    @Test
    void test() {
        System.out.println(this.getClass().getName());
        // C_Tests执行失败，后面依赖的所有类都应该跳过执行
        // assertEquals(13, 3);
        assertEquals(3, 3);
    }
}
