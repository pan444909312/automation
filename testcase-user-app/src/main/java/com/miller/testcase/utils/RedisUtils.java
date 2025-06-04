package com.miller.testcase.utils;

import com.miller.service.framework.cache.remote.redis.RedisService;
import com.miller.testcase.config.TestcaseConfig;

/**
 * Redis 工具类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/8/29 18:16:09
 */
public class RedisUtils extends RedisService {
    static RedisService getRedisInstance() {
        RedisService redisService = RedisService.getRedisServiceInstance();
        redisService.connectionSlave(TestcaseConfig.redisHost, TestcaseConfig.redisPort, TestcaseConfig.redisPassword);
        return redisService;
    }
}
