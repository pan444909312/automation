package com.miller.service.framework.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * JSON 工具类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/2 10:39:13
 */
public class JSONUtils {
    private JSONUtils() {
    }

    /**
     * Jackson 相关配置
     * @return {@link ObjectMapper}
     */
    private static ObjectMapper defaultMapperCreator() {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModules(ObjectMapper.findModules());
//
//        // 设置为false表示不检测失败字段映射, 在反序列化成 Java 对象时忽略不存在的属性。有些时候由于请求的数据不同返回的对象数据结构不一定每次都固定。
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, /* 默认值true */false);
//        // 设置序列化时如果对象的属性值为 null 则不参与序列化和反序列化。
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        // 启用了 FAIL_ON_TRAILING_TOKENS 选项，以确保如果有效JSON后面除了空白之外还有任何文本，则验证将失败。
//        mapper.enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
//
//        //mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//
//
//        //是否允许使用注释
//        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, false);
//        //字段允许去除引号
//        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
//        //允许单引号
//        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, false);
//        //严格重复检测
//        mapper.configure(JsonParser.Feature.STRICT_DUPLICATE_DETECTION, false);
//        //时间字段输出时间戳
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
//        //时间输出为毫秒而非纳秒
//        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
//        //时间读取为毫秒而非纳秒
//        mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
//        //空对象不出错
//        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
//        //不输出空值字段
//        // mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
//        // mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        return mapper;
    }

    /**
     * 判断字符串是否是合法的 JSON，使用 Fastjson
     *
     * @param text 字符串
     * @return Boolean
     */
    public static Boolean isJSONFormat(String text) {

        JSON.parse(text);
        try {
            JSON.parse(text);
            return true;
        } catch (JSONException e) {
            return false;
        }

    }

    /**
     * 字符串转换为对象，使用 Fastjson
     *
     * @param json      字符串
     * @param valueType 预期类型
     * @param <T>       泛型
     * @return 预期类型
     */
    public static <T> T jsonToObject(String json, Class<T> valueType) {
        isJSONFormat(json);
        return JSON.parseObject(json, valueType);
    }


    /**
     * 字符串转换为对象, 使用Jackson
     *
     * @param json      字符串
     * @param valueType 预期类型
     * @param <T>       泛型
     * @return 预期类型
     */
    public static <T> T jsonToObjectByJackson(String json, Class<T> valueType) {
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
     * 对象转 JSON 字符串，使用 Fastjson
     *
     * @param value Object
     * @return JSON 字符串
     */
    public static String toJSONString(Object value) {
        return JSON.toJSONString(value);
    }

    /**
     * 对象转 JSON 字符串，使用 Jackson
     *
     * @param value Object
     * @return JSON 字符串
     */
    public static String toJSONStringByJackson(Object value) {
        ObjectMapper objectMapper = defaultMapperCreator();
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Object to JSON String failure.", e);
        }
    }

    /**
     * 字符串转为 JSONObject，使用 Fastjson
     * @param text 文本
     * @param features 配置，可选
     * @return JSONObject
     */
    public static JSONObject parseObject(String text, Feature... features) {
        return JSON.parseObject(text, features);
    }

    /**
     *  json字符串转jsonArray
     * @param text json字符串
     * @return JSONArray
     */
    public static JSONArray parseArray(String text) {
        return parseArray(text, ParserConfig.global);
    }

    /**
     * json字符串转jsonArray
     * @param text json字符串
     * @param parserConfig 配置
     * @return JSONArray
     */
    public static JSONArray parseArray(String text, ParserConfig parserConfig) {
        return JSON.parseArray(text, parserConfig);
    }


    /**
     * 更新 JSON 字符串中指定 key 的值
     *
     * @param jsonStr 原始 JSON 字符串
     * @param key 需要更新的键
     * @param newValue 新的值
     * @return 更新后的 JSON 字符串
     * @throws JSONException 当输入的字符串不是有效的 JSON 格式时抛出
     */
    public static String updateJsonValue(String jsonStr, String key, Object newValue) {
        if (!isJSONFormat(jsonStr)) {
            throw new JSONException("Invalid JSON format");
        }

        JSON json = JSON.parseObject(jsonStr);
        if (json instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) json;
            jsonObject.put(key, newValue);
            return jsonObject.toJSONString();
        }
        throw new JSONException("Input JSON is not an object");
    }

}
