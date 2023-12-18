package com.miller.service.framework.cache;

import com.miller.service.framework.dto.IssuesDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("缓存测试")
class CacheUtilsTest {

    @Test
    void should_get_value_from_cache() {
        CacheUtils.set("key", "value Miller");
        assertEquals("value Miller", CacheUtils.get("key"));
    }

    @DisplayName("Serializer in Redis")
    @Test
    void redisSerializer() {
        IssuesDTO issuesDTO = new IssuesDTO();
        issuesDTO.setIssueId("Test for redis");
        issuesDTO.setCreateTime(System.currentTimeMillis());
        // 将对象类型数据序列化存放
        CacheUtils.set("testSerializer:testStringRedisSerializer", issuesDTO);
        IssuesDTO issuesDTO1 = CacheUtils.get("testSerializer:testStringRedisSerializer", IssuesDTO.class);
        assertEquals(issuesDTO1.getIssueId(), "Test for redis");
        // List 类型序列化
        CacheUtils.set("testSerializer:list:testStringRedisSerializer", Arrays.asList(issuesDTO));
        List list = CacheUtils.get("testSerializer:list:testStringRedisSerializer", List.class);
        assertTrue(list.size() > 0);
    }
}