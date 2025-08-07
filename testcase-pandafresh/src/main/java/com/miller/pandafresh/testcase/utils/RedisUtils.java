package com.miller.pandafresh.testcase.utils;

import com.miller.service.framework.cache.remote.redis.RedisService;
import com.miller.pandafresh.testcase.config.TestcaseConfig;

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

    //连接redis，指定db8（pfreids存储）
    public static RedisService getPFRedisInstance() {
        RedisService redisService = RedisService.getRedisServiceInstance();
        redisService.connectionBySingleDB(TestcaseConfig.redisHost, TestcaseConfig.redisPort, TestcaseConfig.redisPassword,Integer.valueOf(8));
        return redisService;
    }
}
