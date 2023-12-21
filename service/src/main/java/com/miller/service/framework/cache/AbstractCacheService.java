package com.miller.service.framework.cache;

import java.util.Collection;

/**
 * 抽象缓存服务
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/18 11:20:37
 */
public abstract class AbstractCacheService {
    public abstract void set(String key, Object value);

    public abstract Object get(String key);

    /**
     * 获取制定 key 的自定义对象，并转换为指定类型。
     * <p>
     * 1. 先查找出指定 key 的值，这个值一定要是一个对象类型。
     * 2. 将这个对象值序列化为json之后转换为指定类型，并返回。
     * </p>
     *
     * @param key       键
     * @param classType 参数类型，也是返回类型
     * @param <T>       泛型占位符
     * @return T        泛型类型
     */
    public abstract <T> T get(String key, Class<T> classType);

    public abstract Boolean delete(String key);
    public abstract Long delete(Collection<String> keys);
}
