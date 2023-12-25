package com.miller.userapp.util;

import com.alibaba.fastjson2.JSON;
import com.miller.service.framework.util.MapUtils;
import com.miller.userapp.constants.BusinessConstant;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 对请求工具的参数进行封装，统一处理参数中的副作用，比如：加密、解密等。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/7 16:08:37
 */
public class RequestUtils {
    /**
     * 请求体为json格式对应的请求头
     */
    private static Map<String, Object> headers = null;

    /**
     * 公共请求头
     */
    private static Map<String, Object> commonHeaders() {
        // 公共请求头。每次获取都用新的对象，避免多用户冲突。
        var headers = new HashMap<String, Object>();
        headers.put("latitude", BusinessConstant.latitude);
        headers.put("longitude", BusinessConstant.longitude);
        headers.put("realLatitude", BusinessConstant.realLatitude);
        headers.put("zipCode", BusinessConstant.zipCode);
        headers.put("version", BusinessConstant.version);
        headers.put("pageNo", BusinessConstant.pageNo);
        headers.put("pageSize", BusinessConstant.pageSize);
        headers.put("countryCode", BusinessConstant.countryCode);
        headers.put("language", BusinessConstant.language);
        headers.put("platform", BusinessConstant.platform);
        headers.put("cityName", BusinessConstant.cityName);
        headers.put("country", BusinessConstant.country);
        headers.put("appTypeId", BusinessConstant.appTypeId);
        headers.put("authCode", BusinessConstant.authCode);
        headers.put("androidSafeToken", BusinessConstant.androidSafeToken);
        headers.put("uniqueToken", BusinessConstant.uniqueToken);
        headers.put("unionId", BusinessConstant.unionId);
        headers.put("pandaAppId", BusinessConstant.pandaAppId);
        headers.put("authorization", BusinessConstant.authorization);
        headers.put("testGroup", BusinessConstant.testGroup);
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
        return JSON.toJSONString(t);
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
        Map<String, Object> objectMap = MapUtils.beanToMap(formBody);
        // 对参数的额外操作
        return objectMap;
    }

    /**
     * 对请求url上的 Params 参数的额外处理
     *
     * @param params 请求参数
     * @return 请求参数
     */
    public static Map<String, Object> putParams(Map<String, Object> params) {
        // 求参数的额外操作
        return params;
    }
}
