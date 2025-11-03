package com.miller.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.miller.service.dto.*;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * XXL Config Utils
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/8/13 10:30:20
 */
public class XXLConfUtils {


    // 内网地址
    private static final String XXL_CONFIG_URL;

    private static HashMap<String, Object> headers = new HashMap<>();
    private static HashMap<String, Object> responseCookies;

    static {
        Properties properties = PropertiesUtils.loadConfig(XXLJobUtils.class, "application.properties");
        String profilesActive = properties.getProperty("spring.profiles.active");
        if (Objects.equals(profilesActive, "prod")) {
            // 使用内网地址
            XXL_CONFIG_URL = "http://172.31.236.24:18800";

        } else {
            // 使用外网地址
            XXL_CONFIG_URL = "http://47.110.44.153:18800";

        }

        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        login();
    }

    private static void login() {
        var params = new HashMap<String, Object>();
        params.put("userName", "autotest");
        params.put("password", "auto_test2025");
        params.put("ifRemember", "on");

        Map<String, Object> stringObjectMap = HttpUtils.sendPostRequest(XXL_CONFIG_URL + "/login", null, headers, params, null);
        responseCookies = (HashMap<String, Object>) stringObjectMap.get("cookies");
    }

    /**
     * 更新 XXL Config  配置
     *
     * @param env   {@link XXLConfigEnvEnum}
     * @param key   配置 key
     * @param title 配置名称
     * @param value 配置值，一般是 true 或 false
     * @return 成功返回true， 失败返回false
     */
    public static boolean updateConfig(String env, String key, String title, Boolean value) {
        return updateConfig(env, key, title, value.toString());
    }

    public static boolean updateConfig(String env, String key, String title, String value) {
        XXLConfigEnvEnum xxlConfigEnvEnum = XXLConfigEnvEnum.valueOf(env.toUpperCase());
        XXLConfigRequestDTO xxlConfigRequestDTO = new XXLConfigRequestDTO();
        xxlConfigRequestDTO.setEnv(xxlConfigEnvEnum.getEnv());
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

    public static String getConfigValue(String env, String key) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("appname", key.substring(0, key.indexOf('.')));
        map.put("start", 0);
        map.put("length", 1);
        map.put("searchAllEnv", 0);
        responseCookies.put("XXL_CONF_CURRENT_ENV", env);
        String result = HttpUtils.sendPostRequestReturnBody(XXL_CONFIG_URL + "/conf/pageList", map, headers, null, responseCookies);

        return JSONPath.eval(result, "$.data[0].value").toString();
    }

    public static void main(String[] args) {
        updateConfig(XXLConfigEnvEnum.TEST.getEnv(), "search-server.category.prediction.algorithm.switch", "类目分数最低阈值", "false");
//        System.out.println("获取到的结果是：" + getConfigValue("test", "search-server.category.prediction.algorithm.switch"));
//        System.out.println("获取到的结果是：" + getConfigValue("test", "hp-pos-server.aipos.callbackurl"));

    }

}
