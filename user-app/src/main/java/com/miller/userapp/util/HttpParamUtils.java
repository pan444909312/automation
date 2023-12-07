package com.miller.userapp.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
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
public class HttpParamUtils {
    private static Map<String, Object> headers = null;

    /**
     * 登录时默认请求头
     */
    public static Map<String, Object> getDefaultHeaders() {
        // 公共请求头
        headers = new HashMap<>();
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
     * 请求头参数，携带自定义请求头键值，一般用于区分用户
     *
     * @param myHeaders 自定义请求头键值对，一般在登录之后设置此参数
     */
    public static Map<String, Object> putHeaders(Map<String, Object> myHeaders) {
        headers = getDefaultHeaders();
        // 自定义请求头
        if (Objects.nonNull(myHeaders)) headers.putAll(myHeaders);
        return headers;
    }

    /**
     * 对请求体中的数据进行包装处理，考虑后续请求体参数可能会需要进行加密。
     *
     * @param t 请求体对象
     * @return 请求体字符串
     */
    public static <T> String putBody(T t) {
        String body2 = JSON.toJSONString(t);
        // 对请求参数的额外操作。一下代码是为代码因为现在还没有用到对请求体加密。
        /*
        验签规则：
        1. 获取所有请求体内容，放入到 JSONObject 对象中。
        2. 将 ：authorization， _ts 添加到 JSONObject。
        3. 调用 SignGenerateUtil.getSign（）方法获取 _sign。
        4. 将 _sign和_ts放到请求头发送给服务端。
         */
        String body = "{\"isOnline\":1}";
        JSONObject jsonObjectBody = new JSONObject();
        // 使用 fastjson 工具类，因为其他工具可能会出现转换之后类型变了的问题。比如：1 变成 1.0

        Map requestBody = JSON.parseObject(body, Map.class);
        jsonObjectBody.putAll(requestBody);
        var time = System.currentTimeMillis();
        jsonObjectBody.put("_ts", time);
        String token = HttpParamUtils.headers.get("authorization").toString();
        jsonObjectBody.put("authorization", token);
        var requestSignatureKey = "ldkai_1ldal#nvhsl*afl3g2akgbvsa";
        var signReal = SignGenerateUtil.getSign(jsonObjectBody, requestSignatureKey);
        headers.put("_sign", signReal);
        headers.put("_ts", time);

        return body2;
    }

    /**
     * 对请求参数的额外操作
     *
     * @param params 请求参数
     * @return 请求参数
     */
    public static Map<String, Object> putParams(Map<String, Object> params) {
        // 对请求参数的额外操作
        return params;
    }
}
