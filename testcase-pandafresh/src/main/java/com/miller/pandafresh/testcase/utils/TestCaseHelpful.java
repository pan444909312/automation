package com.miller.pandafresh.testcase.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.Predicate;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;
import com.miller.service.framework.util.JsonUnitUtils;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.service.util.AutoSignUtils;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.jsonunit.assertj.JsonAssert;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.apache.commons.lang3.math.NumberUtils;
import org.assertj.core.api.ObjectAssert;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
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
@Slf4j
public class TestCaseHelpful {
    private static String signAuthKey = "hP*L8pp65_#1flvjk342589fdgjl34m";
    private static Long timeStamp = System.currentTimeMillis();
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
     * 获取测试用例资源文件内容作为请求Params参数，一般是 GET 请求，参数在url上，以 ?key=value&key2=value2的形式
     *
     * @param filePath 文件路径,文件内容为 json
     * @return 请求的 Params 参数，也叫 QueryParams
     */
    public static Map<String, Object> getJsonRequestParams(String filePath) {
        if (null == filePath || filePath.isBlank()) return null;
        Map<String, Object> params = JsonUtils.readJsonFileToMap(filePath);
        // 对请求参数的二次处理，为后续验签准备
        return params;
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
     * 添加 AssertJ 断言
     *
     * @param actual 实际值
     * @param <T>    泛型
     * @return ObjectAssert
     */
    public static <T> ObjectAssert<T> assertThat(T actual) {
        return JsonUnitUtils.assertThat(actual);
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
     * 将一个值放入到缓存中，默认8小时有效期
     *
     * @param key   唯一值，建议使用测试用例 ID 值（scenarioID），默认会拼上前缀 Automation_，如果直连 Redis 查询，请自行拼接。
     * @param value 值
     */
    public static void set(String key, Object value) {
        RedisUtils.getRedisInstance().set("Automation_" + key, value, 60 * 60 * 8L);
    }

    /**
     * 获取缓存中的值
     *
     * @param key 唯一值，建议使用测试用例 ID 值（scenarioID）
     * @return 缓存中的值
     */
    public static Object get(String key) {
        return RedisUtils.getRedisInstance().get("Automation_" + key);
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
     * 使用 JSONPath 更新 JSON 字符串中指定 key 的值为新的值
     *
     * @param jsonStr  原始JSON字符串
     * @param jsonPath JSONPath表达式，例如 "$.store.book[0].title"
     * @param newValue 需要更新的新的值
     * @return 更新后的JSON字符串
     * @throws JSONException         当输入的字符串不是有效的JSON格式时抛出
     * @throws PathNotFoundException 当指定的JSONPath不存在时抛出
     */
    public static String updateJsonValue(String jsonStr, String jsonPath, Object newValue) {
        return JSONUtils.updateJsonValueByPath(jsonStr, jsonPath, newValue);
    }

    // --------------  以下为非通用方法，各业务特有 --------------

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
        if (!Objects.isNull(body)) {
            // 统一处理请求头中的content-type为小写
            if (headers.containsKey("Content-Type")) {
                Object contentTypeValue = headers.get("Content-Type");
                headers.remove("Content-Type");
                headers.put("content-type", contentTypeValue);
            }
            if (headers.get("content-type").toString().contains("application/x-www-form-urlencoded")) {
                body = JSONUtils.parseObject(body.toString()).toJavaObject(Map.class);
            } else {
                body = JSONUtils.toJSONString(JSONUtils.parseObject(body.toString()).toJavaObject(Map.class));
            }
        }
        // 处理 Web 站 请求验签。为了后续兼容服务端处理签名逻辑，这里使用方案一
        if (body instanceof String) {
            try {
                JSONObject jsonBody = JSONUtils.parseObject(body.toString());
                // 判断是否是 Web 站请求体，如果是，则转为 app 请求体
                if (jsonBody.containsKey("pm") &&
                        jsonBody.containsKey("ph") &&
                        jsonBody.containsKey("pd") &&
                        jsonBody.containsKey("nv") &&
                        jsonBody.containsKey("nt") &&
                        jsonBody.containsKey("nn") &&
                        jsonBody.containsKey("nd")) {
                    // 转为 app 请求体
                    // 通过域名判断，获取域名和路径
                    String domain = "";
                    String path = "";

                    if (uri.contains("://")) {
                        // 包含协议的情况
                        int protocolEnd = uri.indexOf("://") + 3;
                        int domainEnd = uri.indexOf("/", protocolEnd);
                        if (domainEnd != -1) {
                            domain = uri.substring(0, domainEnd);
                            path = uri.substring(domainEnd);
                        } else {
                            domain = uri;
                            path = "/";
                        }
                    } else {
                        // 不包含协议的情况，假设是相对路径
                        path = uri;
                    }

                    uri = TestcaseConfig.HOST + path;
                    method = JSONUtils.parseObject(body.toString()).getString("pm");
                    Map webBodyHeaders = JSONUtils.parseObject(body.toString()).getJSONObject("ph").toJavaObject(Map.class);
                    // 避免 Authorization 被 Web 请求体的 ph 覆盖
                    webBodyHeaders.remove("authorization");
                    // 如果H5里面携带了用H5的
                    Object h5ContentType;
                    if (webBodyHeaders.containsKey("content-type")) {
                        h5ContentType = webBodyHeaders.get("content-type");
                    } else {
                        h5ContentType = headers.get("content-type");
                    }
                    webBodyHeaders.putAll(headers);
                    headers.putAll(webBodyHeaders);
                    headers.put("content-type", h5ContentType);
                    String host = TestcaseConfig.HOST;
                    if (host.startsWith("https://")) {
                        host = host.substring(8);
                    }
                    headers.put("Host", host);
                    headers.put("content-type", h5ContentType);
                    if (h5ContentType.toString().contains("application/x-www-form-urlencoded")) {
                        body = JSONUtils.parseObject(body.toString()).getJSONObject("pd")
                                .toJavaObject(Map.class);
                    } else {
                        body = JSONUtils.toJSONString(JSONUtils.parseObject(body.toString()).getJSONObject("pd"));
                    }
                    // 方案二：后续处理
                    WebSignUtils.encode(JSONUtils.parseObject(body.toString()).getString("nt"),
                            JSONUtils.parseObject(body.toString()).getString("nu"),
                            JSONUtils.parseObject(body.toString()).getString("nm"),
                            JSONUtils.parseObject(body.toString()).getString("nh"),
                            JSONUtils.parseObject(body.toString()).getString("nb"));
                }
            } catch (Exception e) {
                // 解析失败说明不是JSON格式,忽略异常
            }
        }
        // 处理 https://fresh-api-test.hungrypanda.cn  签名
        if (uri.contains("fresh-api-test.hungrypanda.cn")) {
            log.info("检测到 fresh-api-test.hungrypanda.cn 域名，开始处理签名逻辑");
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
            //ab测包含autotest可绕过验签
            headers.replace("testgroup", headers.get("testgroup") + ",autotest");

        }
        if ("POST".equals(method)) {
            return HttpUtils.sendPostRequestReturnBody(uri, params, headers, body, null);
        } else if ("GET".equals(method)) {
            return HttpUtils.sendGetRequestReturnBody(uri, params, headers, null);
        } else if ("PUT".equals(method)) {
            return HttpUtils.sendPutRequestReturnBody(uri, params, headers, body, null);
        } else if ("DELETE".equals(method)) {
            return HttpUtils.sendDeleteRequestReturnBody(uri, params, headers, body, null);
        } else if ("PATCH".equals(method)) {
            return HttpUtils.sendPatchRequestReturnBody(uri, params, headers, body, null);
        } else {
            log.error("请求方式错误(405)异常 HttpRequestMethodNotSupportedException, method = {}, path = {}", method, uri);
            throw new RuntimeException("不支持的请求方法" + method);
        }
    }


    // -------------- 以下为非通用方法，各业务特有 --------------

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
    public static String sendHpRequest(String method, String uri, Map<String, Object> params, Map<String, Object> headers,
                                       Object body) {
        // 将headers中的key全部转成小写
        Map<String, Object> lowerCaseHeaders = new HashMap<>();
        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                lowerCaseHeaders.put(entry.getKey().toLowerCase(), entry.getValue());
            }
        }
        headers = lowerCaseHeaders;
        // 处理 Web 站 请求验签。为了后续兼容服务端处理签名逻辑，这里使用方案一
        if (body instanceof String) {
            try {
                JSONObject jsonBody = JSONUtils.parseObject(body.toString());
                // 判断是否是 Web 站请求体，如果是，则转为 app 请求体
                if (jsonBody.containsKey("pm") &&
                        jsonBody.containsKey("ph") &&
                        jsonBody.containsKey("pd") &&
                        jsonBody.containsKey("nv") &&
                        jsonBody.containsKey("nt") &&
                        jsonBody.containsKey("nn") &&
                        jsonBody.containsKey("nd")) {
                    // 转为 app 请求体
                    String domain = "";
                    String path = "";

                    if (uri.contains("://")) {
                        // 包含协议的情况
                        int protocolEnd = uri.indexOf("://") + 3;
                        int domainEnd = uri.indexOf("/", protocolEnd);
                        if (domainEnd != -1) {
                            domain = uri.substring(0, domainEnd);
                            path = uri.substring(domainEnd);
                        } else {
                            domain = uri;
                            path = "/";
                        }
                    } else {
                        // 不包含协议的情况，假设是相对路径
                        path = uri;
                    }

                    uri = TestcaseConfig.HpHOST + path;
                    method = JSONUtils.parseObject(body.toString()).getString("pm");
                    Map webBodyHeaders = JSONUtils.parseObject(body.toString()).getJSONObject("ph")
                            .toJavaObject(Map.class);
                    // 避免 Authorization 被 Web 请求体的 ph 覆盖
                    webBodyHeaders.remove("authorization");
                    // 如果H5里面携带了用H5的
                    Object h5ContentType;
                    if (webBodyHeaders.containsKey("content-type")) {
                        h5ContentType = webBodyHeaders.get("content-type");
                    } else {
                        h5ContentType = headers.get("content-type");
                    }
                    webBodyHeaders.putAll(headers);
                    headers.putAll(webBodyHeaders);
                    headers.put("content-type", h5ContentType);
                    String host = TestcaseConfig.HpHOST;
                    if (host.startsWith("https://")) {
                        host = host.substring(8);
                    }
                    headers.put("host", host);
                    headers.put("content-type", h5ContentType);
                    if (h5ContentType.toString().contains("application/x-www-form-urlencoded")) {
                        body = JSONUtils.parseObject(body.toString()).getJSONObject("pd")
                                .toJavaObject(Map.class);
                    } else {
                        body = JSONUtils.toJSONString(JSONUtils.parseObject(body.toString()).getJSONObject("pd"));
                    }
                    // 方案二：后续处理
                    WebSignUtils.encode(JSONUtils.parseObject(body.toString()).getString("nt"),
                            JSONUtils.parseObject(body.toString()).getString("nu"),
                            JSONUtils.parseObject(body.toString()).getString("nm"),
                            JSONUtils.parseObject(body.toString()).getString("nh"),
                            JSONUtils.parseObject(body.toString()).getString("nb"));
                }
            } catch (Exception e) {
                // 解析失败说明不是JSON格式,忽略异常
            }
        }
        // 处理content-type 为 application/x-www-form-urlencoded 类型
        if (!Objects.isNull(body)) {
            if (headers.get("content-type").toString().contains("application/x-www-form-urlencoded")) {
//                body = JSONUtils.parseObject(body.toString()).toJavaObject(Map.class);
                // 将 JSON 转为 Map 并确保所有值为简单类型（String/Number）
                Map<String, Object> rawMap = JSONUtils.parseObject(body.toString()).toJavaObject(Map.class);
                Map<String, String> formData = new HashMap<>();
                rawMap.forEach((key, value) -> formData.put(key, value != null ? value.toString() : ""));
                body = formData; // 传递简单键值对
            } else {
                body = JSONUtils.toJSONString(JSONUtils.parseObject(body.toString()).toJavaObject(Map.class));
            }
        }
        // 处理验签
        headers.put("_ts", timeStamp);
        JSONObject requestJsonObject = new JSONObject();
        if (!Objects.isNull(body) && body instanceof String) {
            requestJsonObject = JSONObject.parseObject((String) body);
        }
        requestJsonObject.put("_ts", timeStamp);

        requestJsonObject.put("authorization", headers.get("authorization") == null ? "" : headers.get("authorization"));
        // 老签名处理
        String sign = SignGenerateUtil.getSign(requestJsonObject, signAuthKey);
        headers.put("_sign", sign);

        // 新签名处理（老签名使用新的加密方法，且需要比老前面多传一个platform进行加密）
        requestJsonObject.put("platform", headers.get("platform"));
        String sig = SignUtil.getSign(signAuthKey, requestJsonObject);
        headers.put("_sig", sig);


        method = method.toUpperCase();
        if ("POST".equals(method)) {
            return HttpUtils.sendPostRequestReturnBody(uri, params, headers, body, null);
        } else if ("GET".equals(method)) {
            return HttpUtils.sendGetRequestReturnBody(uri, params, headers, null);
        } else if ("PUT".equals(method)) {
            return HttpUtils.sendPutRequestReturnBody(uri, params, headers, body, null);
        } else if ("DELETE".equals(method)) {
            return HttpUtils.sendDeleteRequestReturnBody(uri, params, headers, body, null);
        } else {
            log.error("请求方式错误(405)异常 HttpRequestMethodNotSupportedException, method = {}, path = {}", method, uri);
            throw new RuntimeException("不支持的请求方法" + method);
        }
    }


    /**
     * 登录并返回token
     *
     * @param mobilePhone 手机号 areaCode 默认 86
     * @param password    登录密码
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
        var responseBody = TestCaseHelpful.sendHpRequest(method, uri, null, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        TestCaseHelpful.assertThatJson(responseBody).node("result.accessToken").isNotNull();
        return TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
    }


    /**
     * PF登录获取token
     **/
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


    /**
     * b2b登录获取token
     **/
    public static String loginB2B(String mobilePhone, String code) {

        // 接口请求的 path
        String uri = TestcaseConfig.H5HOST + "/api/b2b/user/login";
        // 请求方式
        String method = "POST";
        // 请求头
        String headers = "module/b2b/loginB2B/request/headers.json";
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
        String body = "module/b2b/loginB2B/request/body.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 修改手机号和密码为动态传递过来的值
        requestBody = JSONUtils.updateJsonValueByPath(requestBody, "$.pd.phone", mobilePhone);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody, "$.pd.code", code);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        TestCaseHelpful.assertThatJson(responseBody).node("data.token").isNotNull();
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
    }
}
