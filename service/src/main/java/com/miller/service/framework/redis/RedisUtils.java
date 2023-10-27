package com.miller.service.framework.redis;

import lombok.Data;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具，使用方法参考单元测试.
 *
 * @author Miller Shan
 * @version 1.0.0
 * @see <a href="https://spring.io/projects/spring-data-redis">spring-data-redis</a>
 */
@Data
public class RedisUtils {
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 连接集群中的单个 Redis slave
     *
     * @param host     IP地址
     * @param port     端口
     * @param password 密码
     * @return {@link RedisTemplate}
     */
    public RedisTemplate<String, Object> connectionSlave(String host, Integer port, String password) {
        RedisClusterNode redisClusterNode = new RedisClusterNode();
        redisClusterNode.setHost(host);
        redisClusterNode.setPort(port);
        return connectionSlave(Collections.singletonList(redisClusterNode), password);
    }

    /**
     * 链接集群中的多个 Redis slave
     *
     * @param nodes    slave 列表
     * @param password Redis服务密码
     * @return {@link RedisTemplate}
     */
    public RedisTemplate<String, Object> connectionSlave(List<RedisClusterNode> nodes, String password) {
        //集群模式
        //List<RedisNode> nodes = Collections.singletonList(new RedisNode(host, port));
        List<RedisNode> clusterNodes = new ArrayList<>();
        for (RedisClusterNode redisClusterNode : nodes) {
            clusterNodes.add(new RedisNode(redisClusterNode.getHost(), redisClusterNode.getPort()));
        }

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setPassword(password);
        redisClusterConfiguration.setClusterNodes(clusterNodes);

        // 使用 Lettuce 连接池
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisClusterConfiguration);
        lettuceConnectionFactory.afterPropertiesSet();

        this.redisTemplate = new RedisTemplate<>();
        redisSerializerConfig(redisTemplate, lettuceConnectionFactory);

        return redisTemplate;
    }

    /**
     * Redis 单机模式
     *
     * @param host     IP
     * @param port     端口
     * @param password 密码
     * @return {@link RedisTemplate}
     */
    public RedisTemplate<String, Object> connectionBySingle(String host, Integer port, String password) {
        //单机模式
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setHostName(host);

        // 使用 Lettuce 连接池
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        lettuceConnectionFactory.afterPropertiesSet();

        this.redisTemplate = new RedisTemplate<>();
        redisSerializerConfig(redisTemplate, lettuceConnectionFactory);

        return redisTemplate;
    }

    private void redisSerializerConfig(RedisTemplate<String, Object> redisTemplate, LettuceConnectionFactory lettuceConnectionFactory) {
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);

        // redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        // 默认的序列化器
        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        // redis key的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //  非hash结构的value序列化器
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        // hash结构的field序列化器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // hash结构的value序列化器
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        // 当序列化器为空的时候是否使用默认序列化器
        redisTemplate.setEnableDefaultSerializer(true);
        redisTemplate.afterPropertiesSet();
    }

    /**
     * 根据正则表达式获取指定的key，* 代表所有。
     */
    public Set<String> getKey(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 给一个指定的 key 值附加过期时间
     *
     * @param key  key
     * @param time 单位秒
     * @return true:成功; false 失败
     */
    public Boolean expire(String key, Long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 根据 key 获取过期时间
     *
     * @param key key
     * @return 过期时间
     */
    public Long getTime(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断指定 key 是否存在
     *
     * @param key Key
     * @return true:成功; false 失败
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 移除指定 key 的过期时间
     *
     * @param key Key
     * @return true:成功; false 失败
     */
    public Boolean persist(String key) {
        return redisTemplate.boundValueOps(key).persist();
    }

    /**
     * 根据key获取值
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 将值放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将值放入缓存并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) , 小于1为无期限
     */
    public void set(String key, String value, Long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 批量添加 key (重复的键会覆盖)
     */
    public void batchSet(Map<String, String> keyAndValue) {
        redisTemplate.opsForValue().multiSet(keyAndValue);
    }

    /**
     * 批量添加 key-value 只有在键不存在时,才添加
     * map 中只要有一个key存在,则全部不添加
     */
    public void batchSetIfAbsent(Map<String, String> keyAndValue) {
        redisTemplate.opsForValue().multiSetIfAbsent(keyAndValue);
    }

    /**
     * 对一个 key-value 的值进行加减操作,
     * 如果该 key 不存在 将创建一个key 并赋值该 number
     * 如果 key 存在,但 value 不是长整型 ,将报错
     */
    public Long increment(String key, Long number) {
        return redisTemplate.opsForValue().increment(key, number);
    }

    /**
     * 对一个 key-value 的值进行加减操作,
     * 如果该 key 不存在 将创建一个key 并赋值该 number
     * 如果 key 存在,但 value 不是 纯数字 ,将报错
     */
    public Double increment(String key, double number) {
        return redisTemplate.opsForValue().increment(key, number);
    }

    //- - - - - - - - - - - - - - - - - - - - -  set类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 将数据放入set缓存
     */
    public void sSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 获取变量中的值
     */
    public Set<Object> members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取变量中指定个数的元素
     */
    public void randomMembers(String key, long count) {
        redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取变量中的元素
     */
    public Object randomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 弹出变量中的元素
     */
    public Object pop(String key) {
        return redisTemplate.opsForSet().pop(String.valueOf(key));
    }

    /**
     * 获取变量中值的长度
     */
    public Long size(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public Boolean sHasKey(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 检查给定的元素是否在变量中。
     *
     * @param key 键
     * @param obj 元素对象
     * @return true 存在 false不存在
     */
    public Boolean isMember(String key, Object obj) {
        return redisTemplate.opsForSet().isMember(key, obj);
    }

    /**
     * 转移变量的元素值到目的变量。
     *
     * @param key     键
     * @param value   元素对象
     * @param destKey 元素对象
     * @return true 存在 false不存在
     */
    public Boolean move(String key, String value, String destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * 批量移除set缓存中元素
     *
     * @param key    键
     * @param values 值
     */
    public void remove(String key, Object... values) {
        redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 通过给定的key求2个set变量的差值
     *
     * @param key     键
     * @param destKey 键
     */
    public Set<Object> difference(String key, String destKey) {
        return redisTemplate.opsForSet().difference(key, destKey);
    }


    //- - - - - - - - - - - - - - - - - - - - -  hash类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 加入缓存
     *
     * @param key Key
     * @param map Map
     */
    public void add(String key, Map<String, String> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 获取 key 下的所有 HashKey 和 value
     *
     * @param key Key
     * @return Map
     */
    public Map<Object, Object> getHashEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 验证指定 key 下 有没有指定的 hashkey
     */
    public Boolean hashKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取指定key的值string
     */
    public String getMapString(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey).toString();
    }

    /**
     * 获取指定的值Int
     */
    public Integer getMapInt(String key, String hashKey) {
        return (Integer) redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 弹出元素并删除
     */
    public String popValue(String key) {
        return redisTemplate.opsForSet().pop(key).toString();
    }

    /**
     * 删除指定 hash 的 HashKey
     *
     * @param key      Key
     * @param hashKeys HashKey
     * @return 删除成功的数量
     */
    public Long delete(String key, String... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 给指定 hash 的 HashKey 做增减操作
     */
    public Long increment(String key, String hashKey, long number) {
        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * 给指定 hash 的 HashKey 做增减操作
     */
    public Double increment(String key, String hashKey, Double number) {
        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * 获取 key 下的 所有 HashKey 字段
     */
    public Set<Object> hashKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取指定 hash 下面的 键值对 数量
     */
    public Long hashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    //- - - - - - - - - - - - - - - - - - - - -  list类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 在变量左边添加元素值
     */
    public void leftPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 获取集合指定位置的值。
     */
    public Object index(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取指定区间的值。
     */
    public List<Object> range(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 把最后一个参数值放到指定集合的第一个出现中间参数的前面，
     * 如果中间参数值存在的话。
     */
    public void leftPush(String key, String pivot, String value) {
        redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 向左边批量添加参数元素。
     */
    public void leftPushAll(String key, String... values) {
        // redisTemplate.opsForList().leftPushAll(key,"w","x","y");
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 向集合最右边添加元素。
     */
    public void leftPushAll(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向左边批量添加参数元素。
     */
    public void rightPushAll(String key, String... values) {
        // redisTemplate.opsForList().leftPushAll(key,"w","x","y");
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 向已存在的集合中添加元素。
     */
    public void rightPushIfPresent(String key, Object value) {
        redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 向已存在的集合中添加元素。
     */
    public long listLength(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 移除集合中的左边第一个元素。
     */
    public void leftPop(String key) {
        redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移除集合中左边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。
     */
    public void leftPop(String key, long timeout, TimeUnit unit) {
        redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移除集合中右边的元素。
     */
    public void rightPop(String key) {
        redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移除集合中右边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。
     */
    public void rightPop(String key, long timeout, TimeUnit unit) {
        redisTemplate.opsForList().rightPop(key, timeout, unit);
    }
}
