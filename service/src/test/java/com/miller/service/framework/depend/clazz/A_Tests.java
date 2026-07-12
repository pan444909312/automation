package com.miller.service.framework.depend.clazz;

import com.miller.service.framework.BasicTestCase;
import com.miller.service.framework.depend.DependsOnClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试类执行顺序，按照依赖顺序执行
 */
@DependsOnClass(B_Tests.class)
public class A_Tests extends BasicTestCase {
    @Test
    void test() {
        System.out.println(this.getClass().getName());
        assertEquals(1, 1);
    }
}
