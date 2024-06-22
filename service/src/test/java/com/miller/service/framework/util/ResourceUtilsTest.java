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
    void testReadFileFromResources() {
        String content = ResourceUtils.readFileFromResources("/application.properties");
        assertNotNull(content);
        content = ResourceUtils.readFileFromResources("/test.json");
        assertTrue(JSONUtils.isJSONFormat(content));
    }


    @Test
    @DisplayName("Read test case data from resource file path")
    void testReadTestCaseDataFromResourcesFile() {
        // load default properties
        Properties defalutProperties = ApplicationPropertiesUtils.loadConfig("application.properties");
        // load test case data
        String content = ResourceUtils.readTestCaseDataFromResourcesFile("Person.json");
        assertTrue(content.contains(defalutProperties.getProperty("spring.profiles.active")));
    }

}