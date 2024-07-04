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
        String content = ResourceUtils.readFileFromResourcesPath("application.properties");
        assertNotNull(content);
        content = ResourceUtils.readFileFromResourcesPath("test.json");
        assertTrue(JSONUtils.isJSONFormat(content));
    }


    @Test
    @DisplayName("Read test case data from resource file path")
    void testReadTestCaseDataFromResourcesPath() {
        // load default properties
        Properties defalutProperties = PropertiesUtils.loadConfig("application.properties");
        // load test case data
        String content = ResourceUtils.readTestCaseDataFromResourcesPath("Person.json");
        assertTrue(content.contains(defalutProperties.getProperty("spring.profiles.active")));
    }

}