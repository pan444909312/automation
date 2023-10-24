package com.miller.service.framework.util;

import io.restassured.path.json.JsonPath;

import java.util.List;

/**
 * Json数据解析器
 *
 * @author Miller Shan
 * @version 1.0.0
 */
public class JSONPathUtils {
    /**
     * 根据 JsonPathExpression 表达式从 Json 字符串中解析出内容
     *
     * @return 解析结果
     */
    public static String parseJsonStringToString(String json, String jsonPathExpression) {
        JsonPath jsonPath = new JsonPath(json);
        String string = jsonPath.getString(jsonPathExpression);
        return string;
    }

    public static Double parseJsonStringToDouble(String json, String jsonPathExpression) {
        JsonPath jsonPath = new JsonPath(json);
        Double aDouble = jsonPath.getDouble(jsonPathExpression);
        return aDouble;
    }

    public static Integer parseJsonStringToInteger(String json, String jsonPathExpression) {
        JsonPath jsonPath = new JsonPath(json);
        Integer integer = jsonPath.getInt(jsonPathExpression);
        return integer;
    }

    public static List<Object> parseJsonStringToList(String json, String jsonPathExpression) {
        JsonPath jsonPath = new JsonPath(json);
        List<Object> list = jsonPath.getList(jsonPathExpression);
        return list;
    }
}
