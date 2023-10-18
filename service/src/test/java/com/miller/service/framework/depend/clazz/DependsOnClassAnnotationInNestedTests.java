package com.miller.service.framework.depend.clazz;

/**
 * 测试多个类之前互相依赖，按照依赖顺序执行.
 */

import com.miller.service.framework.BasicTestCase;
import com.miller.service.framework.depend.DependsOnClass;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DependsOnClassAnnotationInNestedTests {
    @Nested
    class Division extends BasicTestCase {
        @Test
        void division() {
            System.out.println("division");
            assertEquals(4, 4);
        }
    }

    @DependsOnClass(Subtraction.class)
    @Nested
    class Addition extends BasicTestCase {
        @Test
        void addition() {
            System.out.println("addition");
            assertEquals(1, 1);
        }
    }

    @DependsOnClass(Division.class)
    @Nested
    class Multiply extends BasicTestCase {
        @Test
        void multiply() {
            System.out.println("multiply");
            assertEquals(2, 2);
            // 执行失败后续依赖的所有类都应该禁用
            // assertEquals(12, 2);
        }
    }

    @DependsOnClass(Multiply.class)
    @Nested
    class Subtraction extends BasicTestCase {
        @Test
        void subtract() {
            System.out.println("subtract");
            assertEquals(3, 3);
        }
    }
}