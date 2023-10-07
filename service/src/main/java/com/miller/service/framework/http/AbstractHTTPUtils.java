package com.miller.service.framework.http;

import java.util.Map;

/**
 * HTTP 请求的抽象类
 *
 * @author Miller Shan
 * @since 2023-10-2 16:39:13
 * @version 1.0
 */
public abstract class AbstractHTTPUtils {

    public abstract Map<String, Object> sendGetRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Map<String, Object> cookies);

    public abstract Map<String, Object> sendPostRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies);

    public abstract Map<String, Object> sendPutRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies);

    public abstract Map<String, Object> sendDeleteRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies);

    public abstract Map<String, Object> sendOtherMethodRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies,String method);


    public String sendGetRequestReturnBody(String uri, Map<String, Object> params, Map<String, Object> headers, Map<String, Object> cookies) {
        Map<String, Object> stringObjectMap = sendGetRequest(uri, params, headers, cookies);
        String responseBody = String.valueOf(stringObjectMap.get("body"));
        return responseBody;
    }

    public String sendPostRequestReturnBody(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies) {
        Map<String, Object> stringObjectMap = sendPostRequest(uri, params, headers, body, cookies);
        String responseBody = String.valueOf(stringObjectMap.get("body"));
        return responseBody;
    }
}
