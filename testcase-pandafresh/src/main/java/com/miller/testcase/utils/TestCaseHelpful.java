package com.miller.testcase.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Predicate;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;
import com.miller.service.framework.util.JsonUnitUtils;
import com.miller.testcase.config.TestcaseConfig;
import net.javacrumbs.jsonunit.assertj.JsonAssert;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

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
     *
     * @param filePath 文件路径
     * @return 请求头
     */
    public static Map<String, Object> getHeaders(String filePath) {
        Map<String, Object> headers = JsonUtils.readJsonFileToMap(filePath);
        boolean hasContentType = headers.keySet().stream()
                .anyMatch(key -> key.equalsIgnoreCase("Content-Type"));
        if (!hasContentType) {
            throw new IllegalArgumentException("请求头中缺少 Content-Type 字段");
        }
        return headers;
    }

    /**
     *
     * 获取测试用例资源文件内容作为请求Params参数，一般是 GET 请求，参数在url上，以 ?key=value&key2=value2的形式
     *
     * @param filePath 文件路径,文件内容为 json
     *
     * @return 请求的 Params 参数，也叫 QueryParams
     */
    public static Map<String, Object> getJsonRequestParams(String filePath) {
        if (null == filePath || filePath.isBlank()) return null;
        Map<String, Object> params = JsonUtils.readJsonFileToMap(filePath);
        // 对请求参数的二次处理，为后续验签准备
        return params;
    }

    /**
     * 获取测试用例资源文件内容作为请求体，请求提为 application/json 格式
     *
     * @param filePath 文件路径
     * @return 请求体
     */
    public static String getJsonRequestBody(String filePath) {
        if (null == filePath || filePath.isBlank()) return null;
        String testCaseResource = JsonUtils.getFileContent(filePath);
        return testCaseResource;
    }

    /**
     *
     * 获取测试用例资源文件内容作为请求体，请求提为 application/x-www-form-urlencoded 格式
     *
     * @param filePath 文件路径
     *
     * @return 请求体
     */
    private static Map<String, Object> getFormDataRequestBody(String filePath) {
        if (null == filePath || filePath.isBlank()) return null;
        Map<String, Object> params = JsonUtils.readJsonFileToMap(filePath);
        // 对请求参数的二次处理，为后续验签准备
        return params;
    }

    /**
     * 发送 HTTP 请求
     *
     * @param method  请求方法, 支持 POST、GET、PUT、DELETE
     * @param uri     请求地址, 支持 http、https
     * @param params  请求参数
     * @param headers 请求头
     * @param body    请求体
     * @return 响应体字符串
     */
    public static String sendRequest(String method, String uri, Map<String, Object> params, Map<String, Object> headers,
                                     Object body) {
        JSONObject requestJsonObject = JSON.parseObject(body.toString());

        // 将true和false转换成0和1
        for (String key : requestJsonObject.keySet()) {
            String value = requestJsonObject.getString(key);
            if (Objects.equals(value, Boolean.TRUE.toString())) {
                requestJsonObject.put(key, NumberUtils.BYTE_ONE);
                continue;
            }
            if (Objects.equals(value, Boolean.FALSE.toString())) {
                requestJsonObject.put(key, NumberUtils.BYTE_ZERO);
            }
        }
        requestJsonObject.put("authorization", headers.get("authorization"));
        requestJsonObject.put("_ts", System.currentTimeMillis());


        String signReal = SignGenerate.getSignOfHPF(requestJsonObject, "hP*L8pp65_#1flvjk342589fdgjl34m");

        headers.put("_sign", signReal);
        headers.put("_ts", System.currentTimeMillis() + "");
        headers.put("authorization", headers.get("authorization"));


        if ("POST".equals(method)) {
            return HttpUtils.sendPostRequestReturnBody(uri, params, headers, body, null);
        } else if ("GET".equals(method)) {
            return HttpUtils.sendGetRequestReturnBody(uri, params, headers, null);
        } else if ("PUT".equals(method)) {
            return HttpUtils.sendPutRequestReturnBody(uri, params, headers, body, null);
        } else if ("DELETE".equals(method)) {
            return HttpUtils.sendDeleteRequestReturnBody(uri, params, headers, body, null);
        } else {
            new IllegalArgumentException("不支持的请求方法" + method);
            return "";
        }
    }

    /**
     * JSON 断言
     *
     * @param actual    实际值
     * @param callbacks 回调，可选
     * @return JsonAssert.ConfigurableJsonAssert
     */
    public static JsonAssert.ConfigurableJsonAssert assertThatJson(@NotNull Object actual,
                                                                   JsonAssertions.JsonAssertionCallback... callbacks) {
        return JsonUnitUtils.assertThatJson(actual, callbacks);
    }


    /**
     * 获取 JSON 文件内容
     *
     * @param json     JSON 文件内容
     * @param jsonPath JsonPath 路径
     * @param filters  过滤器,可选
     * @param <T>      泛型
     * @return jsonPath 提取的值
     */
    public static <T> T extractValue(String json, String jsonPath, Predicate... filters) {
        return JsonUnitUtils.extractValue(json, jsonPath, filters);
    }

    /**
     * 获取 resources 目录下指定文件内容
     *
     * @param filePath 文件路径
     * @return 文件内容
     */
    public static String getFileContent(String filePath) {
        return JsonUtils.getFileContent(filePath);
    }

    /**
     * 登录并返回token
     * @param mobilePhone 手机号 areaCode 默认 86
     * @param password  登录密码
     * @return token
     */
    public static String login(String mobilePhone, String password) {
        password = MD5Util.string2MD5(password);

        // 接口请求的 path
        String uri = TestcaseConfig.HpHOST + "/api/user/combine/login";
        // 请求方式
        String method = "POST";
        // 请求头
        String headers = "module/headersHP.json";
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
        String body = "module/login/request/should_success.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 修改手机号和密码为动态传递过来的值
        var updateAccountValue = JSONUtils.updateJsonValue(requestBody, "account", mobilePhone);
        requestBody = JSONUtils.updateJsonValue(updateAccountValue, "password", password);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        TestCaseHelpful.assertThatJson(responseBody).node("result.accessToken").isNotNull();
        return TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
    }


    /**PF登录获取token**/
    public static String loginPF(String mobilePhone, String code) {

        // 接口请求的 path
        String uri = TestcaseConfig.HOST + "/user/login";
        // 请求方式
        String method = "POST";
        // 请求头
        String headers = "module/headersPF.json";
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
        String body = "module/loginPF/request/should_success.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 修改手机号和密码为动态传递过来的值
        var updateAccountValue = JSONUtils.updateJsonValue(requestBody, "phone", mobilePhone);
        requestBody = JSONUtils.updateJsonValue(updateAccountValue, "code", code);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        TestCaseHelpful.assertThatJson(responseBody).node("data.token").isNotNull();
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
    }
}
