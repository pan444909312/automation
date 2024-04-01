package com.miller.service.framework.util;

import com.alibaba.fastjson.JSON;
import org.springframework.cglib.beans.BeanMap;

import java.util.*;

/**
 * Map 操作的封装。
 * Java Bean 对象转换为 Map，反之亦然。
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

    /**
     * 将 Java Bean 对象转换为 Map 对象
     *
     * @param bean Java Bean
     * @param <T>  泛型占位符
     * @return Map<String, Object>
     * @see #beanToMap(Object, Boolean)
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        return beanToMap(bean, true);
    }

    /**
     * 将 Bean 对象序列化为 JSON 之后在转成 Map。一般出现这种是因为需要使用 JSON key名称作为 Map 的Key，比如：
     * 字段名称为 toUserIds[0] 在Java中值不支持这种属性名的，则需要先转换为JSON，然后在转成Map的key。
     *
     * @param bean               Java Bean，通常是键值对对象
     * @param isContainJSONField 字段名称使用 JSON 映射的名称则需要设置为 true.
     * @param <T>                标识参数可以接受泛型
     * @return Map  Map<String, Object>
     */
    public static <T> Map<String, Object> beanToMap(T bean, Boolean isContainJSONField) {
        if (isContainJSONField) {
            // 先将对象转换为字符串，然后在转换为Map，这种一般是key：value的对象
            String jsonString = JSON.toJSONString(bean);
            Map map = JSON.parseObject(jsonString, Map.class);
            return map;
        } else {
            BeanMap beanMap = BeanMap.create(bean);
            Map<String, Object> map = new HashMap<>();
            beanMap.forEach((key, value) -> {
                map.put(String.valueOf(key), value);
            });
            return map;
        }
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T bean = clazz.newInstance();
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<>();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            for (int i = 0, size = maps.size(); i < size; i++) {
                map = maps.get(i);
                T bean = mapToBean(map, clazz);
                list.add(bean);
            }
        }
        return list;
    }
}
