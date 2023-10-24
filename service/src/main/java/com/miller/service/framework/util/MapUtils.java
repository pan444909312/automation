package com.miller.service.framework.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Map 操作封装
 *
 * @author Miller Shan
 * @version 1.0.0
 */
public class MapUtils {
    /**
     * 删除 Map 中 Value 为 null 或者 空字符串的 Key
     *
     * @param map 待处理的Map
     * @return 去空之后的 Map
     */
    public static Map<String, Object> removeKeyIfValueIsNullOrEmptyString(Map<String, Object> map) {
        if (Objects.isNull(map)) throw new UnsupportedOperationException("Map can not empty");

        map.values().removeIf(Objects::isNull);

        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = map.get(key);
            if (value instanceof String) {
                // value 的值为空字符串也删除对应的 key. 这里需要通过调用 iterator.remove() 而不能用 map.remove(key)
                if (((String) value).length() < 1) iterator.remove();
            }
        }
        return map;
    }

    /**
     * 移除 Map 中 value  为 null 的 key
     */
    public static Map<String, Object> removeNullValue(Map<String, Object> map) {
        if (Objects.isNull(map)) throw new UnsupportedOperationException("Map can not empty");

        map.values().removeIf(Objects::isNull);
        return map;
    }
}
