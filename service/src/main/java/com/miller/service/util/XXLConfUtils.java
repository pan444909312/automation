package com.miller.service.util;

import com.alibaba.fastjson.JSON;
import com.miller.service.dto.XXLConfigEnvEnum;
import com.miller.service.dto.XXLConfigRequestDTO;
import com.miller.service.dto.XXLResponseDTO;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;
import com.miller.service.framework.util.PropertiesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * XXL Config Utils
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/8/13 10:30:20
 */
public class XXLConfUtils {
    private static final String XXL_CONFIG_URL = "http://47.110.44.153:18800";
    private static HashMap<String, Object> headers = new HashMap<>();
    private static HashMap<String, Object> responseCookies;

    static {
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        login();
    }

    private static void login() {
        var params = new HashMap<String, Object>();
        params.put("userName", "shandongdong");
        params.put("password", "123456");
        params.put("ifRemember", "on");

        Map<String, Object> stringObjectMap = HttpUtils.sendPostRequest(XXL_CONFIG_URL + "/login", params, headers, null, null);
        responseCookies = (HashMap<String, Object>) stringObjectMap.get("cookies");
    }

    /**
     * 更新配置
     */
    public static boolean updateConfig(XXLConfigEnvEnum env, String key, String title, Boolean value) {
        XXLConfigRequestDTO xxlConfigRequestDTO = new XXLConfigRequestDTO();
        xxlConfigRequestDTO.setEnv(env.getEnv());
        xxlConfigRequestDTO.setKey(key);
        xxlConfigRequestDTO.setTitle(title);
        xxlConfigRequestDTO.setValue(value);
        return updateConfig(xxlConfigRequestDTO);
    }

    public static boolean updateConfig(XXLConfigRequestDTO xxlConfigRequestDTO) {
        Map<String, Object> body = JSON.parseObject(JSON.toJSONString(xxlConfigRequestDTO), Map.class);
        XXLResponseDTO XXLResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(XXL_CONFIG_URL + "/conf/update", null, headers, body, responseCookies, XXLResponseDTO.class);
        if (XXLResponseDTO.getCode().equals(200)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        updateConfig(XXLConfigEnvEnum.TEST, "user-app-server.shoplist.cache", "【首页店铺流】是否读redis缓存", false);
    }

}
