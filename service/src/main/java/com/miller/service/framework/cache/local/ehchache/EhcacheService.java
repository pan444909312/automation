package com.miller.service.framework.cache.local.ehchache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

import java.net.MalformedURLException;

/**
 * 实现本地缓存.暂不支持持久到到本地磁盘，从磁盘读取数据会失败。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/18 15:11:49
 */
@Deprecated
public class EhcacheService {
    private static EhcacheService ehcacheService;

    public static Cache<String, String> getCacheInstance() {
        if (ehcacheService == null) {
            ehcacheService = new EhcacheService();
        }
        return ehcacheService.getCacheOfEhcache();
    }

    /**
     * Ehcache 缓存配置。需要注意的是EhCache序列化时不支持任意类型，只有常用的基本类型和String可以被序列化。
     */
    private Cache<String, String> getCacheOfEhcache() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                // 指定持久化磁盘路径
                .with(CacheManagerBuilder.persistence("cache-local/myCache"))
                .withCache("myCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class,
                                String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(1, MemoryUnit.MB)
                                        .offheap(10, MemoryUnit.MB)
                                        // 指定需要持久化到磁盘
                                        .disk(10, MemoryUnit.GB, true))
                        .build())
                .build(true);
        Cache<String, String> myCache = cacheManager.getCache("myCache", String.class, String.class);

        // cacheManager.close();
        return myCache;
    }


    public static void main(String[] args) throws MalformedURLException {
        Cache<String, String> cacheInstance = EhcacheService.getCacheInstance();

//        cacheInstance.put("key1", "value1");
//        cacheInstance.put("key2", "value2");

        // Retrieve data from the cache
        String value1 = cacheInstance.get("key1");
        String value2 = cacheInstance.get("key2");

        System.out.println("Value for key1: " + value1);
        System.out.println("Value for key2: " + value2);
    }
}
