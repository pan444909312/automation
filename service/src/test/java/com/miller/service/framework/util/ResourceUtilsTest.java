package com.miller.service.framework.util;

import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@TestFramework
class ResourceUtilsTest {
    @Test
    @DisplayName("Read file content from resource file path")
    void testReadFileFromResourcesPath() {
        String content = new ResourceUtils().readFileFromResourcesPath(this.getClass(), "application.properties");
        assertNotNull(content);
        content = new ResourceUtils().readFileFromResourcesPath(this.getClass(), "test.json");
        assertTrue(JSONUtils.isJSONFormat(content));
    }


    @Test
    @DisplayName("Read test case data from resource file path")
    void testReadTestCaseDataFromResourcesPath() {
        // load default properties
        Properties defalutProperties = new PropertiesUtils().loadConfig(this.getClass(), "application.properties");
        // load test case data
        String content = new ResourceUtils().readTestCaseDataFromResourcesPath(this.getClass(), "Person.json");
        assertTrue(content.contains(defalutProperties.getProperty("spring.profiles.active")));
    }

}