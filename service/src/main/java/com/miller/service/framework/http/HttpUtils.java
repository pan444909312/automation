package com.miller.service.framework.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miller.service.framework.constants.JsonLibraryEnum;
import com.miller.service.framework.util.JSONUtils;
import com.miller.service.util.AutoSignUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送HTTP请求工具
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/2 15:24:55
 */
public class HttpUtils {
    /**
     * 通过传入不同的实现类来实现不同的协议调用，但是要求接口实现的功能必须相同，也就是返回的数据格式相同
     */
    private static AbstractHTTPUtils abstractProtocol = new HTTPUtilsByRestAssured();

    public static Map<String, Object> sendGetRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Map<String, Object> cookies) {
//        AutoSignUtils.signHandler(headers,null);
        if (!headers.containsKey("enableSign")){
            headers.put("enableSign", true);
        }
        return abstractProtocol.sendGetRequest(uri, params, headers, cookies);
    }

    public static Map<String, Object> sendPostRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies) {
//        AutoSignUtils.signHandler(headers,body);
        if (!headers.containsKey("enableSign")){
            headers.put("enableSign", true);
        }
        return abstractProtocol.sendPostRequest(uri, params, headers, body, cookies);
    }

    public static Map<String, Object> sendPutRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies) {
        return abstractProtocol.sendPutRequest(uri, params, headers, body, cookies);
    }

    public static Map<String, Object> sendDeleteRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies) {
        return abstractProtocol.sendDeleteRequest(uri, params, headers, body, cookies);
    }

    public static Map<String, Object> sendOtherMethodRequest(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies, String method) {
        return abstractProtocol.sendOtherMethodRequest(uri, params, headers, body, cookies, method);
    }

    /**
     * 发送 GET 请求，返回响应体。
     *
     * @param uri     请求的 url, 例如: <a href="http://localhost:1024">http://localhost:1024</a>
     * @param params  url上的参数，例如: ?key1=value1&key2=value2
     * @param headers 请求头
     * @param cookies 请求 Cookie
     * @return 响应体
     */
    public static String sendGetRequestReturnBody(String uri, Map<String, Object> params, Map<String, Object> headers, Map<String, Object> cookies) {
        Map<String, Object> stringObjectMap = sendGetRequest(uri, params, headers, cookies);
        HashMap<String, Object> responseBodyMap = (HashMap<String, Object>) stringObjectMap.get("body");
        return String.valueOf(responseBodyMap.get("body"));
    }

    /**
     * 发送 POST 请求，返回响应体。
     *
     * @param uri     请求的 url, 例如: <a href="http://localhost:1024">http://localhost:1024</a>
     * @param params  url上的参数，例如: ?key1=value1&key2=value2
     * @param headers 请求头
     * @param body    请求体，可能存在多种类型，比如: form-data, x-www-form-urlencoded, raw(JSON)
     * @param cookies Cookie
     * @return 响应体
     */
    public static String sendPostRequestReturnBody(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies) {
        Map<String, Object> stringObjectMap = sendPostRequest(uri, params, headers, body, cookies);
        HashMap<String, Object> responseBodyMap = (HashMap<String, Object>) stringObjectMap.get("body");
        return String.valueOf(responseBodyMap.get("body"));
    }

    /**
     * 发送 PUT 请求，返回响应体。
     *
     * @see #sendPostRequestReturnBody(String, Map, Map, Object, Map)
     */
    public static String sendPutRequestReturnBody(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies) {
        Map<String, Object> stringObjectMap = sendPutRequest(uri, params, headers, body, cookies);
        HashMap<String, Object> responseBodyMap = (HashMap<String, Object>) stringObjectMap.get("body");
        return String.valueOf(responseBodyMap.get("body"));
    }

    /**
     * 发送 DELETE 请求，返回响应体。
     *
     * @see #sendPostRequestReturnBody(String, Map, Map, Object, Map)
     */
    public static String sendDeleteRequestReturnBody(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies) {
        Map<String, Object> stringObjectMap = sendDeleteRequest(uri, params, headers, body, cookies);
        HashMap<String, Object> responseBodyMap = (HashMap<String, Object>) stringObjectMap.get("body");
        return String.valueOf(responseBodyMap.get("body"));
    }

    /**
     * 发送 GET 请求，将响应结果的 JSON 字符串映射成 Java 对象。使用 Fastjson 作为默认的 JSON 解析器。
     *
     * @param uri        请求的 url, 例如: 例如: <a href="http://localhost:1024">http://localhost:1024</a>
     * @param params     url上的参数，例如: ?key1=value1&key2=value2
     * @param headers    请求头
     * @param cookies    请求Cookie
     * @param returnType 自定义 Java 对象，需要与响应数据结构映射
     * @param <T>        返回泛型
     * @return 自定义类型
     * @see #sendGetRequestReturnBody(String, Map, Map, Map)
     */
    public static <T> T sendGetRequestReturnJavaObject(String uri, Map<String, Object> params, Map<String, Object> headers, Map<String, Object> cookies, Class<T> returnType) {
        return sendGetRequestReturnJavaObject(uri, params, headers, cookies, returnType, JsonLibraryEnum.FASTJSON);
    }

    /**
     * 发送 POST 请求，将响应结果的 JSON 字符串映射成 Java 对象。使用 Fastjson 作为默认的 JSON 解析器。
     *
     * @param uri        请求的 url, 例如: 例如: <a href="http://localhost:1024">http://localhost:1024</a>
     * @param params     url上的参数，例如: ?key1=value1&key2=value2
     * @param headers    请求头
     * @param body       请求体，可能存在多种类型，比如: form-data, x-www-form-urlencoded, raw(JSON)
     * @param cookies    Cookie
     * @param returnType 自定义 Java 对象，需要与响应数据结构映射
     * @param <T>        返回泛型
     * @return 自定义类型
     * @see #sendPostRequestReturnBody(String, Map, Map, Object, Map)
     */
    public static <T> T sendPostRequestReturnJavaObject(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies, Class<T> returnType) {
        return sendPostRequestReturnJavaObject(uri, params, headers, body, cookies, returnType, JsonLibraryEnum.FASTJSON);
    }

    /**
     * 发送 PUT 请求，将响应结果的 JSON 字符串映射成 Java 对象。使用 Fastjson 作为默认的 JSON 解析器。
     *
     * @see #sendPostRequestReturnJavaObject(String, Map, Map, Object, Map, Class)
     */
    public static <T> T sendPutRequestReturnJavaObject(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies, Class<T> returnType) {
        return sendPutRequestReturnJavaObject(uri, params, headers, body, cookies, returnType, JsonLibraryEnum.FASTJSON);
    }

    /**
     * 发送 DELETE 请求，将响应结果的 JSON 字符串映射成 Java 对象。使用 Fastjson 作为默认的 JSON 解析器。
     *
     * @see #sendPostRequestReturnJavaObject(String, Map, Map, Object, Map, Class)
     */
    public static <T> T sendDeleteRequestReturnJavaObject(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies, Class<T> returnType) {
       return sendDeleteRequestReturnJavaObject(uri, params, headers, body, cookies, returnType, JsonLibraryEnum.FASTJSON);
    }

    /**
     * @param jsonLibraryEnum JSON解析器,系统默认支持{@link JsonLibraryEnum}
     * @see #sendGetRequestReturnJavaObject(String, Map, Map, Map, Class)
     */
    public static <T> T sendGetRequestReturnJavaObject(String uri, Map<String, Object> params, Map<String, Object> headers, Map<String, Object> cookies, Class<T> returnType, JsonLibraryEnum jsonLibraryEnum) {
        switch (jsonLibraryEnum) {
            case FASTJSON:
                return JSONUtils.jsonToObject(sendGetRequestReturnBody(uri, params, headers, cookies), returnType);
            case JACKSON:
                return JSONUtils.jsonToObjectByJackson(sendGetRequestReturnBody(uri, params, headers, cookies), returnType);
            // 暂不支持 GSON
            case GSON:
                System.err.println("暂不支持 GSOn");
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * @param jsonLibraryEnum JSON解析器,系统默认支持{@link JsonLibraryEnum}
     * @see #sendPostRequestReturnJavaObject(String, Map, Map, Object, Map, Class)
     */
    public static <T> T sendPostRequestReturnJavaObject(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies, Class<T> returnType, JsonLibraryEnum jsonLibraryEnum) {
        switch (jsonLibraryEnum) {
            case FASTJSON:
                return JSONUtils.jsonToObject(sendPostRequestReturnBody(uri, params, headers, body, cookies), returnType);
            case JACKSON:
                return JSONUtils.jsonToObjectByJackson(sendPostRequestReturnBody(uri, params, headers, body, cookies), returnType);
            // 暂不支持 GSON
            case GSON:
                System.err.println("暂不支持 GSON");
                break;
            default:
                break;
        }
        return null;
    }


    /**
     * @param jsonLibraryEnum JSON解析器,系统默认支持{@link JsonLibraryEnum}
     * @see #sendPutRequestReturnJavaObject(String, Map, Map, Object, Map, Class)
     */
    public static <T> T sendPutRequestReturnJavaObject(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies, Class<T> returnType, JsonLibraryEnum jsonLibraryEnum) {
        switch (jsonLibraryEnum) {
            case FASTJSON:
                return JSONUtils.jsonToObject(sendPutRequestReturnBody(uri, params, headers, body, cookies), returnType);
            case JACKSON:
                return JSONUtils.jsonToObjectByJackson(sendPutRequestReturnBody(uri, params, headers, body, cookies), returnType);
            // 暂不支持 GSON
            case GSON:
                System.err.println("暂不支持 GSOn");
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * @param jsonLibraryEnum JSON解析器,系统默认支持{@link JsonLibraryEnum}
     * @see #sendDeleteRequestReturnJavaObject(String, Map, Map, Object, Map, Class)
     */
    public static <T> T sendDeleteRequestReturnJavaObject(String uri, Map<String, Object> params, Map<String, Object> headers, Object body, Map<String, Object> cookies, Class<T> returnType, JsonLibraryEnum jsonLibraryEnum) {
        switch (jsonLibraryEnum) {
            case FASTJSON:
                return JSONUtils.jsonToObject(sendDeleteRequestReturnBody(uri, params, headers, body, cookies), returnType);
            case JACKSON:
                return JSONUtils.jsonToObjectByJackson(sendDeleteRequestReturnBody(uri, params, headers, body, cookies), returnType);
            // 暂不支持 GSON
            case GSON:
                System.err.println("暂不支持 GSOn");
                break;
            default:
                break;
        }
        return null;
    }
}
