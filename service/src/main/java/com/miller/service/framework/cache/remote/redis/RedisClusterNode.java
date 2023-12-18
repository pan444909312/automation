package com.miller.service.framework.cache.remote.redis;

import lombok.Data;

/**
 * Redis 集群中的 Slave 节点信息
 *
 * @author Miller Shan
 * @version 1.0.0
 */
@Data
public class RedisClusterNode {
    String host;
    Integer port;
}
