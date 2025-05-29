package com.miller.service.framework.util;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import net.javacrumbs.jsonunit.assertj.JsonAssert;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.jetbrains.annotations.NotNull;

/**
 * JsonUnit Utils
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/5/27 17:19:26
 */
public class JsonUnitUtils {

    /**
     * JSON 断言
     * @param actual    实际值
     * @param callbacks 回调，可选
     * @return JsonAssert.ConfigurableJsonAssert
     */
    public static JsonAssert.ConfigurableJsonAssert assertThatJson(@NotNull Object actual, JsonAssertions.JsonAssertionCallback... callbacks) {
        return JsonAssertions.assertThatJson(actual, callbacks);
    }

    /**
     * 获取 JSON 内容，根据给定的 JsonPath
     * @param json      JSON 内容
     * @param jsonPath  JsonPath 路径
     * @param filters   过滤器,可选
     * @param <T>       泛型
     * @return jsonPath 提取的值
     *
     * @see JsonPath#read(Object, String, Predicate...) 由于JsonUnit 本身没有提供通过 JsonPath 提取值的方法，
     * 但是 JsonUnit 依赖 json-unit-json-path 而它依赖 om.jayway.jsonpath » json-path 所以可以直接使用它来做数据提取，
     * 这样就不用引入新的库，避免引入新的依赖。
     */
    public static <T> T extractValue(String json, String jsonPath, Predicate... filters) {
        return JsonPath.read(json, jsonPath, filters);
    }
}
