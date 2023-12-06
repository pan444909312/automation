package com.miller.service.framework.util;

import com.alibaba.fastjson2.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Miller Shan
 */
class JSONUtilsTest {

    @Test
    @DisplayName("Asset String is JSON format.")
    void isJSONFormat() {
        String errorJson = "{\"A\":\"B\"}x";
        Assertions.assertThrows(JSONException.class, () -> JSONUtils.isJSONFormat(errorJson));
        String json = "{\"A\":\"B\"}";
        Assertions.assertTrue(JSONUtils.isJSONFormat(json));
    }
}