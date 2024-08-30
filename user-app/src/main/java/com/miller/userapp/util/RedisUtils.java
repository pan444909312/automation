package com.miller.userapp.util;

import com.miller.service.framework.cache.remote.redis.RedisService;
import com.miller.service.framework.util.PropertiesUtils;
import org.springframework.data.redis.core.RedisTemplate;

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
}
