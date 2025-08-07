package com.miller.service.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @Author: panjuxiang
 * @Since: 2025/7/15
 */
public class AutoSignUtils {
    private static final String signAuthKey = "hP*L8pp65_#1flvjk342589fdgjl34m";

    public static Map<String, Object> signHandler(Map<String, Object> headers, Object body){
        Long timeStamp = System.currentTimeMillis();
        headers.put("_ts", timeStamp);
        JSONObject requestJsonObject = new JSONObject();
        if (!Objects.isNull(body) && body instanceof String) {
            requestJsonObject = JSONObject.parseObject((String) body);
        }
        requestJsonObject.put("_ts", timeStamp);

        requestJsonObject.put("authorization", headers.get("authorization") == null ? "" : headers.get("authorization"));
        requestJsonObject = convertBooleanToNumber(requestJsonObject);
        // 老签名处理
        String sign = SignGenerateUtil.getSign(requestJsonObject, signAuthKey);
        headers.put("_sign", sign);

        // 新签名处理（老签名使用新的加密方法，且需要比老前面多传一个platform进行加密）
        requestJsonObject.put("platform", headers.get("platform"));
        String sig = SignUtil.getSign(signAuthKey, requestJsonObject);
        headers.put("_sig", sig);
        return headers;
    }

    protected static JSONObject convertBooleanToNumber(JSONObject jsonObject) {
        // 将true和false转换成0和1
        for (String key : jsonObject.keySet()) {
            String value = jsonObject.getString(key);
            if (Objects.equals(value, Boolean.TRUE.toString())) {
                jsonObject.put(key, NumberUtils.BYTE_ONE);
                continue;
            }
            if (Objects.equals(value, Boolean.FALSE.toString())) {
                jsonObject.put(key, NumberUtils.BYTE_ZERO);
            }
        }
        return jsonObject;
    }
}
