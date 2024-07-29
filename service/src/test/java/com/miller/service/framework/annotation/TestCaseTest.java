package com.miller.service.framework.annotation;

import com.miller.common.util.ULIDUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;

/**
 * 测试如何确定唯一的测试用例ID
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/24 18:13:49
 */
public class TestCaseTest {

    /**
     * ID 生成规则：
     * 1. 先用 ULIDUtils.generateULID() 生成N个待用ID.
     * 2. 测试框架将测试代码中的ID作为唯一ID标识写入到TAPD自定义字段中。
     * 3. 测试框架通过代码中的唯一ID标识在TAPD中查询是否有此ID，如果有则更新测试用例，如果没有则创建测试用例
     * 4. TAPD 中设置一个隐藏字段专门给测试框架使用。
     *
     * @param testInfo
     */
    @RepeatedTest(value = 3)
    void test(TestInfo testInfo) {
        System.out.println(testInfo.getTestMethod().hashCode());    // -261110400 但是也会存在包名/类名/方法名/称变了 hashcode值就变了
        System.out.println(testInfo.getTestClass().get().getName() + "#" + testInfo.getTestMethod().get().getName());
    }

    @RepeatedTest(3)
    void testGeneratorID() {
        System.out.println(ULIDUtils.generateULID());
    }
}
