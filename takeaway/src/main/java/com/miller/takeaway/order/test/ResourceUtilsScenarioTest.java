package com.miller.takeaway.order.test;

import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.framework.util.ResourceUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * 测试在SpringBoot环境下查找测试类，以及测试类对应的资源文件
 */
@Disabled
@TestFramework
class ResourceUtilsScenarioTest {
    @Test
    @DisplayName("Read file content from resource file path")
    void testReadFileFromResourcesPath() {
        String content = new PropertiesUtils().getProperty(this.getClass(), "test");
        System.out.println("takeaway:" + content);
        String content1 = new PropertiesUtils().getProperty(this.getClass(), "test-test");
        System.out.println("content1:" + content1);

        // 从 resource 目录下的 测试目录 读取数据
        String content2 = new ResourceUtils().readTestCaseDataFromResourcesPath(this.getClass(), "LoginDataPjx.json");
        System.out.println("content2: " + content2);

        // 从 resource 目录读取数据
        System.out.println(new ResourceUtils().readFileFromResourcesPath(this.getClass(), "application.properties"));

    }

}