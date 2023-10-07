package com.miller.service.framework.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * HTTP 请求的抽象类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023-10-2 10:39:13
 */
public class JSONUtils {
    private JSONUtils() {
    }

    private static ObjectMapper defaultMapperCreator() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModules(ObjectMapper.findModules());

        // 设置为false表示不检测失败字段映射, 在反序列化成 Java 对象时忽略不存在的属性。有些时候由于请求的数据不同返回的对象数据结构不一定每次都固定。
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, /* 默认值true */false);
        // 设置序列化时如果对象的属性值为 null 则不参与序列化和反序列化。
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 启用了 FAIL_ON_TRAILING_TOKENS 选项，以确保如果有效JSON后面除了空白之外还有任何文本，则验证将失败。
        mapper.enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);

        //mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);


        //是否允许使用注释
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, false);
        //字段允许去除引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
        //允许单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, false);
        //严格重复检测
        mapper.configure(JsonParser.Feature.STRICT_DUPLICATE_DETECTION, false);
        //时间字段输出时间戳
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        //时间输出为毫秒而非纳秒
        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        //时间读取为毫秒而非纳秒
        mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        //空对象不出错
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
        //不输出空值字段
        // mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        // mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        return mapper;
    }

    /**
     * 判断字符串是否是合法的 JSON
     *
     * @param text 字符串
     * @return Boolean
     */
    public static Boolean isJSONFormat(String text) {
        ObjectMapper objectMapper = defaultMapperCreator();
        try {
            objectMapper.readTree(text);
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("不是一个合格的JSON格式:" + text);
            //return false;
        }
    }

    /**
     * 字符串转换为对象
     *
     * @param json      字符串
     * @param valueType 预期类型
     * @param <T>       泛型
     * @return 预期类型
     */
    public static <T> T jsonToObject(String json, Class<T> valueType) {
        isJSONFormat(json);
        ObjectMapper objectMapper = defaultMapperCreator();
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonProcessingException jsonProcessingException) {
            System.err.format("String to Java Object failure, because %s not to transform %s. Error Message: %s", json, valueType, jsonProcessingException);
            throw new UnsupportedOperationException("String to Java Object failure, because " + json + " not to transform " + valueType + ". Error Message:", jsonProcessingException);
        }
    }

    /**
     * 对象转 JSON
     *
     * @param value Object
     * @return JSON 字符串
     */
    public static String toJSONString(Object value) {
        ObjectMapper objectMapper = defaultMapperCreator();
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Object to JSON String failure.", e);
        }
    }

    public static JsonNode parseObject(String json) {
        isJSONFormat(json);
        ObjectMapper objectMapper = defaultMapperCreator();
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("String to JsonNode failure.", e);
        }
    }
}
