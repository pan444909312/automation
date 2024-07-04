package com.miller.service.framework.http;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static io.restassured.config.EncoderConfig.encoderConfig;

/**
 * 通过 Rest-Assured 实现 发送 HTTP 请求
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/2 20:24:55
 */
@Slf4j
public class HTTPUtilsByRestAssured extends AbstractHTTPUtils {
    /**
     * 发送 GET 请求
     *
     * @param uri     请求的 url, 例如: <a href="http://localhost:1024">http://localhost:1024</a>
     * @param params  请求的 Query Params，一般是 URI上的参数.例如: ?key1=value1&key2=value2
     * @param headers 请求头参数
     * @param cookies 请求携带的 Cookie
     * @return Map<String, Object>  key包含: request(Map), headers(Map), cookies(Map), status(Map), body(String), otherObject(Map)
     */
    @Override
    public Map<String, Object> sendGetRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Map<String, Object> cookies) {
        log.info("========================= 开始记录HTTP 日志 ========================");
        if (null == uri || uri.length() < 1) {
            log.error("uri cannot be null");
            throw new RuntimeException("uri cannot be null");
        }
        if (null == params) {
            params = new LinkedHashMap<>();
        }
        if (null == headers) {
            headers = new LinkedHashMap<>();
        }
        if (null == cookies) {
            cookies = new LinkedHashMap<>();
        }
        // Given,提供测试数据。
        RequestSpecification request = RestAssured.given();
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        // 关闭urlEncoding，但是可能会导致url上存在中文不支持
        request.urlEncodingEnabled(false);
        // 打印请求日志
        log.info("========================= 输出请求日志 =============================");

        request.log().all();

        request.params(params);
        request.headers(headers);
        request.cookies(cookies);

        // When,发送请求。
        log.info("========================= 开始发送请求 GET =========================");
        Response response = request.when().request(Method.GET, uri).then().extract().response();

        // response.then().statusCode(200);
        log.info("========================= 结束发送请求 GET =========================");
        log.info("========================= 输出响应日志 =============================");

        // Then,响应结果。
        response.then().log().all();
        Map<String, Object> stringObjectMap = processResponseResult(response, request);
        log.info("========================= 结束记录HTTP 日志 =========================");
        return stringObjectMap;
    }

    /**
     * 发送 POST 请求
     *
     * @param uri     请求的 url, 例如: <a href="http://localhost:1024">http://localhost:1024</a>
     * @param params  请求的 Query Params，LinkedHashMap<String, Object> URL上的参数有顺序要求，所以必须要求加入时的map是有序的。
     *                params一般是URI上的参数，比如: ?key=value&key1=value2     * @param headers 请求头
     * @param headers 请求头参数
     * @param body    请求体支持 application/json、application/x-www-form-urlencoded
     * @param cookies Cookie
     * @return Map<String, Object>  key包含: request(Map), headers(Map), cookies(Map), status(Map), body(String), otherObject(Map)
     */
    @Override
    public Map<String, Object> sendPostRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies) {
        return sendRequest(uri, params, headers, body, cookies, "POST");
    }

    /**
     * 发送 PUT 请求
     *
     * @see #sendPostRequest(String, Map, Map, Object, Map)
     */
    @Override
    public Map<String, Object> sendPutRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies) {
        return sendRequest(uri, params, headers, body, cookies, "PUT");
    }

    /**
     * 发送 DELETE 请求
     *
     * @see #sendPostRequest(String, Map, Map, Object, Map)
     */
    @Override
    public Map<String, Object> sendDeleteRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies) {
        return sendRequest(uri, params, headers, body, cookies, "DELETE");
    }

    /**
     * 发送其它请求，暂不做处理，交给框架自动处理
     *
     * @see #sendPostRequest(String, Map, Map, Object, Map)
     */
    @Override
    public Map<String, Object> sendOtherMethodRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies, String method) {
        return sendRequest(uri, params, headers, body, cookies, method);
    }

    private Map<String, Object> sendRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies, String method) {
        log.info("========================= 开始记录HTTP 日志 =========================");
        if (null == uri || uri.length() < 1) {
            log.error("uri cannot be null");
            throw new RuntimeException("uri cannot be null");
        }
        if (null == params) {
            params = new LinkedHashMap<>();
        }
        if (null == headers) {
            headers = new LinkedHashMap<>();
        }
        if (null == cookies) {
            cookies = new LinkedHashMap<>();
        }
        // 处理请求体为空的情况
        if (Objects.isNull(body)) {
            body = "";
        }
        // given, 提供测试数据。
        RequestSpecification request = RestAssured.given();

        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        // 关闭urlEncoding，但是可能会导致url上存在中文不支持
        request.urlEncodingEnabled(false);
        // params = Maps.newLinkedHashMap(params);
        log.info("========================= 输出请求日志 ==============================");
        // 打印请求日志
        request.log().all();

        // 根据请求头的Content-type来区分不同body
        String contentType = String.valueOf(headers.get("Content-Type"));
        // 处理 application/json 格式直接将body中的内容当做字符串形式发送即可
        if (contentType.toLowerCase(Locale.ROOT).contains("application/json")) {
            log.info("处理Content-Type为:{} 的请求body.", contentType);
            // 请求头
            request.headers(headers);
            //URL上的数据
            request.queryParams(params);
            //body中的数据
            request.body(body);
            request.cookies(cookies);
        }
        // 处理 application/x-www-form-urlencoded 格式
        else if (contentType.toLowerCase(Locale.ROOT).contains("x-www-form-urlencoded")) {
            log.info("处理Content-Type为:{} 的请求body.", contentType);
            // 参数包含中文需要添加 charset=UTF-8 ，不在框架层处理这个逻辑了，在业务层处理
            if (!contentType.toLowerCase(Locale.ROOT).contains("charset=UTF-8")) {
                // headers.put("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
                log.warn("If not support Chinese words, suggest add charset. For example:{} ", "application/x-www-form-urlencoded;charset=UTF-8");
            }
            request.headers(headers);
            request.queryParams(params);
            // body中的数据为键值对
            if (body instanceof Map) {
                try {
                    /*
                    接受键值对请求体时，如果将Java Bean 对象转换为 Map 可能会抛出异常，比如 Java Bean 对象中包含字段 String[] 数组时。
                    java.lang.IllegalArgumentException: Cannot serialize because
                    cannot determine how to serialize content-type application/x-www-form-urlencoded;charset=UTF-8
                    解决方案: 将对象序列化为 JSON 字符串，然后再转换为 Map 对象。
                    <pre>
                        String jsonString = JSON.toJSONString(bean);
                        Map map = JSON.parseObject(jsonString, Map.class);
                    </pre>
                     */
                    request.formParams((Map<String, ?>) body);
                } catch (IllegalArgumentException illegalArgumentException) {
                    log.error("Please serialize body {} to JSON then convert to Map", body);
                    throw illegalArgumentException;
                }
            }
            request.cookies(cookies);
        }
        // 请求头中没有写 Content-Type 则无法判断类型
        else {
            // 智能判断，如果请求体是 Map 则认为可能是 application/x-www-form-urlencoded
            if (body instanceof Map) {
                log.warn("系统预测请求体可能为 application/x-www-form-urlencoded 尝试处理");
                // body中的数据为键值对
                headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                request.headers(headers);
                request.queryParams(params);
                try {
                    request.formParams((Map<String, ?>) body);
                } catch (IllegalArgumentException illegalArgumentException) {
                    log.error("Please serialize body {} to JSON then convert to Map", body);
                    throw illegalArgumentException;
                }
                request.cookies(cookies);
            }
            // 兜底的处理逻辑
            else {
                log.warn("可能不支持的请求体, 因为Content-Type= {}", headers.get("Content-Type"));
                // headers.put("Content-Type", "application/json");
                request.headers(headers);
                request.queryParams(params);
                // 自动识别请求体类型，丢给 REST-Assured 框架自身去处理
                request.body(body);
                request.cookies(cookies);
                // 请求体不为空,并且长度大于0才处理
                // if (body instanceof String && ((String) body).length() > 0) {
                //     request.body(body);
                // }
            }
        }
        Response response = null;
        // System.out.println(Method.POST.name());
        if (method.equalsIgnoreCase("POST")) {
            log.info("========================= 开始发送请求 POST =========================");
            response = request.when().request(Method.POST, uri).then().extract().response();
            log.info("========================= 结束发送请求 POST =========================");
        } else if (method.equalsIgnoreCase("PUT")) {
            log.info("========================= 开始发送请求 PUT =========================");
            response = request.when().request(Method.PUT, uri).then().extract().response();
            log.info("========================= 结束发送请求 PUT =========================");
        } else if (method.equalsIgnoreCase("DELETE")) {
            log.info("========================= 开始发送请求 DELETE =========================");
            response = request.when().request(Method.DELETE, uri).then().extract().response();
            log.info("========================= 结束发送请求 DELETE =========================");
        } else {
            Method[] methodValues = Method.values();
            for (Method methodValue : methodValues) {
                String methodName = methodValue.name();
                if (method.equalsIgnoreCase(methodName)) {
                    log.info("========================= 开始发送请求 " + method.toUpperCase(Locale.ROOT) + " =========================");
                    response = request.when().request(method, uri).then().extract().response();
                    log.info("========================= 结束发送请求 " + method.toUpperCase(Locale.ROOT) + "=========================");
                } else {
                    log.error("========================= 发送请求失败 " + method.toUpperCase(Locale.ROOT) + "=========================");
                }
            }
        }
        // 记录响应日志中的所有内容
        log.info("========================= 输出响应日志 ==============================");
        assert response != null;
        response.then().log().all();
        log.info("========================= 结束记录HTTP 日志 =========================");
        return processResponseResult(response, request);
    }

    /**
     * 处理原生 REST-Assured 对象的返回结果，这里要区分来存储。响应头、响应体、响应状态码、其他
     *
     * @param response 响应对象
     * @return Map<String, Object>  key: request, headers, cookies, status, body, otherObject
     */
    private Map<String, Object> processResponseResult(Response response, RequestSpecification request) {
        HashMap<String, Object> result = new HashMap<>();
        // 请求数据
        HashMap<String, Object> requestLogMap = new HashMap<>();
        // 响应 Header
        HashMap<String, Object> responseHeaderMap = new HashMap<>();
        // 响应 Cookie
        HashMap<String, Object> responseCookieMap = new HashMap<>();
        // 响应 Body
        HashMap<String, Object> responseBodyMap = new HashMap<>();
        // 响应 Status
        HashMap<String, Object> responseStatusMap = new HashMap<>();
        // 响应中其他对象，主要包括原始响应对象，方便调用方调用原始对象的行为
        HashMap<String, Object> otherObjectMap = new HashMap<>();

        // 获取请求 日志
        // requestLogMap.put("params", request.log().params().toString());
        // requestLogMap.put("body", request.log().body().toString());
        // requestLogMap.put("headers", request.log().headers());
        // requestLogMap.put("cookies", request.log().cookies().toString());
        // requestLogMap.put("method", request.log().method().toString());
        // requestLogMap.put("uri", request.log().uri().toString());
        // 请求日志 所有
        // requestLogMap.put("all", request.log().all().toString());

        // 获取响应 headers
        Headers allHeaders = response.getHeaders();
        for (Header header : allHeaders) {
            responseHeaderMap.put(header.getName(), header.getValue());
        }
        // 获取响应 cookies
        Map<String, String> cookies = response.getCookies();
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            responseCookieMap.put(entry.getKey(), entry.getValue());
        }
        // 获取响应 status
        responseStatusMap.put("statusCode", response.statusCode());
        responseStatusMap.put("statusLine", response.statusLine());

        // 获取响 body
        // Bug:请勿修改响应体的内容，否则可能出现字符串反序列化错误，有些字符串中间的空格benign去掉
        // responseBodyMap.put("body", (response.getBody().asString()).replaceAll(" ", ""));
        responseBodyMap.put("body", response.getBody().asString());
        String body = response.getBody().asString();

        // 这里不应该把Response对象返回，应该解析出内容后返回，这样才能达到在HTTPClientUtils工具中不管是使用RestAssured还是HTTPClient都是相同效果。但是为了充分使用Rest-assured的功能，可以把此对象一并返回
        otherObjectMap.put("request", request);
        otherObjectMap.put("response", response);

        // 聚合在一起最后全部返回
        result.put("request", requestLogMap);
        result.put("headers", responseHeaderMap);
        result.put("cookies", responseCookieMap);
        result.put("status", responseStatusMap);
        result.put("body", responseBodyMap);
        // result.put("body", body);
        result.put("otherObject", otherObjectMap);
        // System.out.println("响应聚集结果" + result);
        return result;
    }
}
