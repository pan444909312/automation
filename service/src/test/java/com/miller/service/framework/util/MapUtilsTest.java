package com.miller.service.framework.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Miller Shan
 */
class MapUtilsTest {

    @Test
    void removeEmptyValue() {
        var map = new HashMap<String, Object>();
        map.put("One", 1);
        map.put("Null", null);
        map.put("Null2", null);
        map.put("two", "2");
        map.put("Empty", "");
        map.put("Empty2", "");
        map.put("three", 3);
        Map<String, Object> resultMap = MapUtils.removeKeyIfValueIsNullOrEmptyString(map);
        System.out.println(resultMap);
    }
}