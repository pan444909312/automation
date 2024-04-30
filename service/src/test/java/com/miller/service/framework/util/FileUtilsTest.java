package com.miller.service.framework.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {
    @Test
    @DisplayName("Read file content from resource file path")
    void testReadFileFromResources() {
        String content = FileUtils.readFileFromResources("/application.properties");
        assertNotNull(content);
        content = FileUtils.readFileFromResources("/test.json");
        assertTrue(JSONUtils.isJSONFormat(content));
    }

}