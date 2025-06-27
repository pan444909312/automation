package com.miller.testcase.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.Predicate;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.cache.remote.redis.RedisService;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;
import com.miller.service.framework.util.JsonUnitUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.module.erp.login.ERPLoginTests;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.jsonunit.assertj.JsonAssert;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import net.javacrumbs.jsonunit.core.Option;
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
//            throw new IllegalArgumentException("请求头中缺少 Content-Type 字段");
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
        if (null == filePath || filePath.isBlank())
            return null;
        String testCaseResource = JsonUtils.getFileContent(filePath);
        return testCaseResource;
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
        if (null == filePath || filePath.isBlank())
            return null;
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
     * @param key   唯一值，建议使用测试用例 ID 值（scenarioID），默认会拼上前缀 Automation_，如果直连 Redis
     *              查询，请自行拼接。
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
    public static String sendRequest(String method, String uri, Map<String, Object> params, Map<String, Object> headers,
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

                    uri = TestcaseConfig.HOST_APP + path;
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
                    String host = TestcaseConfig.HOST_APP;
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
        if (!Objects.isNull(body)) {
            if (headers.get("content-type").toString().contains("application/x-www-form-urlencoded")) {
                body = JSONUtils.parseObject(body.toString()).toJavaObject(Map.class);
            } else {
                body = JSONUtils.toJSONString(JSONUtils.parseObject(body.toString()).toJavaObject(Map.class));
            }
        }
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
        String uri = TestcaseConfig.HOST_APP + "/api/user/combine/login";
        // 请求方式
        String method = "POST";
        // 请求头
        String headers = "module/headers.json";
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
        String body = "module/home/login/request/should_success.json";

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

    /**
     * @param tel 输入手机号
     * @return 手机号验证码
     */
    public static Integer getVerificationCode(String tel) {
        RedisService redisService;
        redisService = RedisService.getRedisServiceInstance();
        redisService.connectionSlave("r-3nscqny4art27v9hrzpd.redis.rds.aliyuncs.com", 6379, "YNKAthEbNF3XoK8E");
        redisService.set("message-server:IMG_CAPTCHA:28d33b2425c344c581a4520f3c8c98f9", 32, 60L);
        String uri = TestcaseConfig.HOST_APP + "/api/app/user/sendVerificationCode";
        var headers = TestCaseHelpful.getHeaders("module/account/getVerificationCode/request/headers.json");
        var requestBody = TestCaseHelpful
                .getJsonRequestBody("module/account/getVerificationCode/request/should_success.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "captchaToken", "28d33b2425c344c581a4520f3c8c98f9");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "phoneNumber", tel);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.captchaCheckInfo.imageCheckInfo.checkCode", "32");
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        // 需要在redis存值，不然图形校验不通过
        // 获取验证码,需要查询加密后的手机号
        String telephone = getPhoneNumber(tel);
        return (Integer) PandaTestDBHelpful
                .executeSelectOneSql("select * from user_log where telephone = ? order by create_time desc limit 1",
                        telephone)
                .get("verifycode");
    }

    /**
     * 获取加密后的明文手机号
     *
     * @param encodePhone 需要加密的手机号
     * @return 加密后的手机号
     */
    public static String getPhoneNumber(String encodePhone) {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/encryption/crypto";
        var headers = TestCaseHelpful.getHeaders("module/erp/login/request/headers_crypto.json");
        String body = "{\"sceneType\":1,\"text\":\"" + encodePhone + "\",\"cryptoType\":1}";
        headers.put("token", TestCaseHelpful.erpLogin());
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        return TestCaseHelpful.extractValue(responseBody, "data.content");
    }


    /**
     * ERP 后台登录，返回token。 不支持账号密码登录，只能使用默认账号。
     *
     * @return token
     */
    public static String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        String headers = "module/erp/login/request/headers.json";
        String params = null;
        String body = "module/erp/login/request/should_success.json";
        String assertFullField = "module/erp/login/response/assert_full_field.json";

        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        return TestCaseHelpful.extractValue(responseBody, "$.data.token");
    }

}
