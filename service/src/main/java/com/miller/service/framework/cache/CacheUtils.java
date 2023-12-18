package com.miller.service.framework.cache;

import com.miller.service.framework.cache.remote.redis.RedisService;

/**
 * 统一的缓存工具类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/18 11:01:20
 */
public class CacheUtils {

    private static AbstractCacheService cacheService;

    static {
        RedisService redisService = RedisService.getRedisServiceInstance();
        redisService.connectionSlave("r-1udjtdncdilouvmf23pd.redis.rds.aliyuncs.com", 6379, "xVGrf4upEgRXFmUO");
        CacheUtils.cacheService = redisService;
    }

    public synchronized static void set(String key, Object value) {
        cacheService.set(key, value);
    }

    public static Object get(String key) {
        return cacheService.get(key);
    }

    /**
     * @see com.miller.service.framework.cache.remote.redis.RedisService#get(String, Class)
     */
    public static <T> T get(String key, Class<T> classType) {
        return cacheService.get(key, classType);
    }
}
