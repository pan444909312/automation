package com.miller.service.framework.cache.remote.redis;

import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.depend.DependsOnMethod;
import com.miller.service.framework.dto.IssuesDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

/**
 * @author Miller Shan
 */
@Disabled
@TestFramework
@DisplayName("RedisService TestCase")
class RedisServiceTest {
    static RedisService redisService;

    @BeforeAll
    static void beforeAll() {
        redisService = RedisService.getRedisServiceInstance();
        redisService.connectionSlave("r-1udjtdncdilouvmf23pd.redis.rds.aliyuncs.com", 6379, "xVGrf4upEgRXFmUO");
    }

    @Test
    @DisplayName("Java operator String")
    void operateString() {
        redisService.set("name", "Miller");
        String key = String.valueOf(redisService.get("name"));
        assertThat(key, equalToIgnoringCase("Miller"));
    }

    @DisplayName("Serializer in Redis")
    @Test
    public void redisSerializer() {
        IssuesDTO issuesDTO = new IssuesDTO();
        issuesDTO.setIssueId("Test for redis");
        issuesDTO.setCreateTime(System.currentTimeMillis());
        // 将对象类型数据序列化存放
        redisService.set("testSerializer:testStringRedisSerializer", issuesDTO);
        // List 类型序列化
        redisService.set("testSerializer:list:testStringRedisSerializer", Arrays.asList(issuesDTO));
    }

    @Test
    @DisplayName("Java operator String by Spring RedisTemplate")
    void operateStringBySpringRedisTemplate() {
        // 获取 Spring redisTemplate
        redisService.getRedisTemplate().opsForValue().set("name", "Miller");
        String key = String.valueOf(redisService.getRedisTemplate().opsForValue().get("name"));
        assertThat(key, equalToIgnoringCase("Miller"));
    }

    @DependsOnMethod("operateString")
    @Test
    @DisplayName("Query Keys from Redis")
    void queryKey() {
        // 查询所有key
        Set keys = redisService.getKey("*");
        keys.forEach(System.out::println);
    }
    @Test
    @DisplayName("Delete key from Redis")
    void deleteKey() {
        redisService.getRedisTemplate().delete("name");
    }
}
