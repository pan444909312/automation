package com.miller.pos.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.enums.ContentTypeEnum;
import com.miller.service.framework.util.MapUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 对请求工具的参数进行封装，统一处理参数中的副作用，比如：加密、解密等。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/14 21:08:37
 */
public class RequestUtils extends com.miller.service.framework.http.HttpUtils {
    /**
     * 请求体为json格式对应的请求头
     */
    private static Map<String, Object> headers = null;

    private static String token = null;

    public static Map<String, Object> sendPostRequest(String uri, Object body) {
        return sendPostRequest(
                uri,
                null,
                ContentTypeEnum.JSON.toHeader(token),
                RequestUtils.putBodyOfJson(body),
                null
        );
    }

    public static <T> T sendPostRequest(String uri, Object body, Class<T> responseClass) {
        return sendPostRequestReturnJavaObject(
                uri,
                null,
                ContentTypeEnum.JSON.toHeader(token),
                RequestUtils.putBodyOfJson(body),
                null,
                responseClass
        );
    }


    public static void setToken(String token){
        RequestUtils.token = token;
    }


    /**
     * 公共请求头
     */
    private static Map<String, Object> commonHeaders() {
        // 公共请求头。每次获取都用新的对象，避免多用户冲突。
        var headers = new HashMap<String, Object>();
//        headers.put("localshopid", BusinessConstant.localshopid);
//        headers.put("latitude", BusinessConstant.latitude);
//        headers.put("longitude", BusinessConstant.longitude);
//        headers.put("user-agent", BusinessConstant.userAgent);
//        headers.put("locale", BusinessConstant.locale);
//        headers.put("version", BusinessConstant.version);
//        headers.put("systemversion", BusinessConstant.systemversion);
//        headers.put("devicesn", BusinessConstant.devicesn);
//        headers.put("platform", BusinessConstant.platform);
//        headers.put("countryCode", BusinessConstant.countryCode);
//        headers.put("appTypeId", BusinessConstant.appTypeId);
//        headers.put("uniqueToken", BusinessConstant.uniqueToken);
//        headers.put("devicemodel", BusinessConstant.devicemodel);
//        headers.put("devicebrand", BusinessConstant.devicebrand);
//        headers.put("Authorization", "lPWgzWw5aXp3iX0RCSN+vsUV/Li2K7vzxTAvEutqw+c=");
        headers.put("Authorization", BusinessConstant.authorization);

        return headers;
    }

    /**
     * 获取请求头参数
     */
    public static Map<String, Object> getHeaders() {
        checkHeaders(headers);
        return headers;
    }

    /**
     * 设置请求头参数
     *
     * @param myHeaders 自定义请求头键值对，一般在登录之后设置此参数
     */
    public static void setHeaders(Map<String, Object> myHeaders) {
        headers = commonHeaders();
        // 自定义请求头
        if (Objects.nonNull(myHeaders)) headers.putAll(myHeaders);
    }

    private static void checkHeaders(Map<String, Object> headers) {
        if (Objects.isNull(headers.get("Content-Type"))) {
            throw new IllegalArgumentException("请求头中缺少 Content-Type 字段");
        }
    }

    /**
     * 将对象转换为JSON字符串。
     * 对请求体中的数据进行包装处理，考虑后续请求体参数可能会需要进行加密。
     *
     * @param t 请求体对象
     * @return 请求体字符串
     */
    public static <T> String putBodyOfJson(T t) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//        return JSON.toJSONString(t);
        /*
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
    }

    /**
     * 对请求体中的数据进行包装处理，考虑后续请求体参数可能会需要进行加密。
     *
     * @param formBody 请求体中的 form 参数
     * @return 处理之后的请求体参数
     */
    public static <T> Map<String, Object> putBodyOfForm(T formBody) {
        // 将Java Bean 对象转换为Map
        Map<String, Object> stringObjectMap = MapUtils.beanToMap(formBody);
        // 请求参数的额外操作
        return stringObjectMap;
    }

    /**
     * 对请求 url 上的 Params 参数的额外处理
     *
     * @param params 请求参数 Java Bean
     * @return 请求参数的Map对象
     */
    public static <T> Map<String, Object> putParams(T params) {
        // 将Java Bean 对象转换为Map
        Map<String, Object> stringObjectMap = MapUtils.beanToMap(params);
        // 请求参数的额外操作
        return stringObjectMap;
    }
}
