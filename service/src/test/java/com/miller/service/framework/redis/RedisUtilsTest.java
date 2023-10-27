package com.miller.service.framework.redis;

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
@DisplayName("RedisUtils TestCase")
class RedisUtilsTest {
    static RedisUtils redisUtils;

    @BeforeAll
    static void beforeAll() {
        redisUtils = new RedisUtils();
        redisUtils.connectionSlave("47.242.73.37", 6379, "Autotest#1024");
    }

    @Test
    @DisplayName("Java operator String")
    void operateString() {
        redisUtils.set("name", "Miller");
        String key = String.valueOf(redisUtils.get("name"));
        assertThat(key, equalToIgnoringCase("Miller"));
    }

    @DisplayName("Serializer in Redis")
    @Test
    public void redisSerializer() {
        IssuesDTO issuesDTO = new IssuesDTO();
        issuesDTO.setIssueId("Test for redis");
        issuesDTO.setCreateTime(System.currentTimeMillis());
        // 将对象类型数据序列化存放
        redisUtils.set("testSerializer:testStringRedisSerializer", issuesDTO);
        // List 类型序列化
        redisUtils.set("testSerializer:list:testStringRedisSerializer", Arrays.asList(issuesDTO));
    }

    @Test
    @DisplayName("Java operator String by Spring RedisTemplate")
    void operateStringBySpringRedisTemplate() {
        // 获取 Spring redisTemplate
        redisUtils.getRedisTemplate().opsForValue().set("name", "Miller");
        String key = String.valueOf(redisUtils.getRedisTemplate().opsForValue().get("name"));
        assertThat(key, equalToIgnoringCase("Miller"));
    }

    @DependsOnMethod("operateString")
    @Test
    @DisplayName("Query Keys from Redis")
    void queryKey() {
        // 查询所有key
        Set keys = redisUtils.getKey("*");
        keys.forEach(System.out::println);
    }
}
