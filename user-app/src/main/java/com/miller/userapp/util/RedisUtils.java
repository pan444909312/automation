package com.miller.userapp.util;

import com.miller.service.framework.cache.remote.redis.RedisService;
import com.miller.service.framework.util.PropertiesUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

/**
 * Redis 工具类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/8/29 18:16:09
 */
public class RedisUtils extends RedisService {

    public static RedisService getRedisInstance() {
        var redisHost = new PropertiesUtils().getProperty(RedisUtils.class, "spring.data.redis.host");
        var redisPort = new PropertiesUtils().getProperty(RedisUtils.class, "spring.data.redis.port");
        var redisPassword = new PropertiesUtils().getProperty(RedisUtils.class, "spring.data.redis.password");
        RedisService redisService = RedisService.getRedisServiceInstance();
        redisService.connectionSlave(redisHost, Integer.valueOf(redisPort), redisPassword);
        return redisService;
    }


    public static String getSysAppConfigValue(String key) {
        Object valueObj = RedisUtils.getRedisInstance().get(key);
        if (valueObj != null) {
            return valueObj.toString();
        } else {
            Map<String, Object> configMap = PandaTestDBHelpful.executeSelectOneSql("SELECT `config_value` FROM hp_sys_app_config WHERE config_key = '" + key + "' limit 1");
            return configMap.get("config_value").toString();
        }
    }

}
