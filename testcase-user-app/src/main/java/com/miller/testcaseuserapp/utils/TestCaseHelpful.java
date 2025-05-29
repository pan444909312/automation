package com.miller.testcaseuserapp.utils;

import com.jayway.jsonpath.Predicate;
import com.miller.service.framework.util.JsonUnitUtils;
import net.javacrumbs.jsonunit.assertj.JsonAssert;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * 测试用例助手, 简化和提高用例开发效率，作用如下：
 * 1. 获取 JSON 文件内容作为请求头;
 * 2. 获取 JSON 文件内容作为请求体;
 * 3. 获取 JSON 文件内容作为断言（通常是复制响应结果直接作为断言;
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/5/27 14:47:39
 */
public class TestCaseHelpful {
    /**
     * 获取测试用例资源文件内容作为请求头
     * @param filePath 文件路径
     * @return 请求头
     */
    public static Map<String, Object> getHeaders(String filePath) {
        Map<String, Object> headers = JSONUtils.readJsonFileToMap(filePath);
        if (!headers.containsKey("Content-Type")) {
            throw new IllegalArgumentException("请求头中缺少 Content-Type 字段");
        }
        return headers;
    }

    /**
     * 获取测试用例资源文件内容作为请求体，请求提为 application/json 格式
     * @param filePath 文件路径
     * @return 请求体
     */
    public static String getJsonRequestBody(String filePath) {
        String testCaseResource = JSONUtils.getFileContent(filePath);
        /*
        对请求参数的二次处理，为后续验签准备
        // 对请求参数的额外操作。以下代码是为代码因为现在还没有用到对请求体加密。

        //        验签规则：
        //        1. 获取所有请求体内容，放入到 JSONObject 对象中。
        //        2. 将 ：authorization， _ts 添加到 JSONObject。
        //        3. 调用 SignGenerateUtil.getSign（）方法获取 _sign。
        //        4. 将 _sign和_ts放到请求头发送给服务端。
        String body = "{\"isOnline\":1}";
        JSONObject jsonObjectBody = new JSONObject();
        // 使用 fastjson 工具类，因为其他工具可能会出现转换之后类型变了的问题。比如：1 变成 1.0

        Map requestBody = JSON.parseObject(body, Map.class);
        jsonObjectBody.putAll(requestBody);
        var time = System.currentTimeMillis();
        jsonObjectBody.put("_ts", time);
        String token = RequestUtils.getHeaders().get("authorization").toString();
        jsonObjectBody.put("authorization", token);
        var requestSignatureKey = "ldkai_1ldal#nvhsl*afl3g2akgbvsa";
        var signReal = SignGenerateUtil.getSign(jsonObjectBody, requestSignatureKey);
        // 给请求头添加验签参数
        RequestUtils.getHeaders().put("_sign", signReal);
        RequestUtils.getHeaders().put("_ts", time);
        return body;
        */

        return testCaseResource;
    }

    /**
     *
     * 获取测试用例资源文件内容作为请求体，请求提为 application/x-www-form-urlencoded 格式
     * @param filePath 文件路径
     *
     * @return 请求体
     */
    private static Map<String, Object> getFormDataRequestBody(String filePath) {
        Map<String, Object> params = JSONUtils.readJsonFileToMap(filePath);
        // 对请求参数的二次处理，为后续验签准备
        return params;
    }

    /**
     * JSON 断言
     * @param actual    实际值
     * @param callbacks 回调，可选
     * @return JsonAssert.ConfigurableJsonAssert
     */
    public static JsonAssert.ConfigurableJsonAssert assertThatJson(@NotNull Object actual, JsonAssertions.JsonAssertionCallback... callbacks) {
        return JsonUnitUtils.assertThatJson(actual, callbacks);
    }

    /**
     * 获取 JSON 文件内容
     * @param json      JSON 文件内容
     * @param jsonPath  JsonPath 路径
     * @param filters   过滤器,可选
     * @param <T>       泛型
     * @return jsonPath 提取的值
     */
    public static <T> T extractValue(String json, String jsonPath, Predicate... filters) {
        return JsonUnitUtils.extractValue(json, jsonPath, filters);
    }

    /**
     * 获取 resources 目录下指定文件内容
     * @param filePath 文件路径
     * @return 文件内容
     */
    public static String getFileContent(String filePath) {
        return JSONUtils.getFileContent(filePath);
    }

}
