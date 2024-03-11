package com.miller.erp.util;

import com.alibaba.fastjson.JSON;
import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.service.framework.util.MapUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 对请求工具的参数进行封装，统一处理参数中的副作用，比如：加密、解密等。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 17:08:37
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
        headers.put("token", BusinessConstantOfERP.TOKEN);
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
        String body = JSON.toJSONString(t);
        // 对请求参数的额外操作,比如验签
        return body;
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
     * 对请求url上的 Params 参数的额外处理
     *
     * @param params 请求参数
     * @return 请求参数
     */
    public static <T> Map<String, Object> putParams(T params) {
        // 将Java Bean 对象转换为Map
        Map<String, Object> stringObjectMap = MapUtils.beanToMap(params);
        // 请求参数的额外操作
        return stringObjectMap;
    }
}
