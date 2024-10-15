package com.miller.market.util;

import com.miller.service.framework.cache.remote.redis.RedisService;
import com.miller.service.framework.util.PropertiesUtils;

/**
 * Redis 工具类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/8/29 18:16:09
 */
public class RedisUtils extends RedisService {

    //连接redis，指定db8（pfreids存储）
    public static RedisService getRedisInstance() {
        var redisHost = new PropertiesUtils().getProperty(RedisUtils.class, "spring.data.redis.host");
        var redisPort = new PropertiesUtils().getProperty(RedisUtils.class, "spring.data.redis.port");
        var redisPassword = new PropertiesUtils().getProperty(RedisUtils.class, "spring.data.redis.password");
        var database = new PropertiesUtils().getProperty(RedisUtils.class, "spring.data.redis.database");
        RedisService redisService = RedisService.getRedisServiceInstance();
        redisService.connectionBySingleDB(redisHost, Integer.valueOf(redisPort), redisPassword,Integer.valueOf(database));
        return redisService;
    }
}
