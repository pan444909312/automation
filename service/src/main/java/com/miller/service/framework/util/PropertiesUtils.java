package com.miller.service.framework.util;

import com.miller.service.framework.clz.ClassFindService;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

/**
 * 处理不同的配置文件，参考: SpringBoot 的 application.properties 使用方式
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/5/31 14:48:03
 */
@Slf4j
public class PropertiesUtils {
    /**
     * 根据当前类获取当前类模块下的资源文件中的属性值
     *
     * @param clazz 当前类必须要传，否则在多模块 SpringBoot 环境下获取的永远都是运行jar的资源文件中的属性值
     * @param key   属性key
     * @return 属性key对应的value值
     */
    public String getProperty(Class<?> clazz, String key) {
        String property = loadProperties(clazz).getProperty(key);
        if (Objects.isNull(property) || property.isBlank()) {
            log.error("property key not found: {}", key);
            throw new RuntimeException("property key not found: " + key);
        }
        return property;
    }


    /**
     * 加载配置文件，默认会加载“application.properties”配置文件，
     * 然后根据配置文件中的key spring.profiles.active 动态加载对应的配置文件，最终会合并两个配置文件中的配置项，重复的会覆盖。
     * <p>
     * 默认会读取resources目录下的 application.properties 作为配置文件。
     * 可以通过 spring.profiles.active 来配置多个配置文件。
     * 如果 存在 application-{profile}.properties 则会读取该配置文件内容，最后合并。
     * </p>
     *
     * @return {@link Properties}
     */
    private synchronized Properties loadProperties(Class<?> clazz) {
        Properties mergeProperties = new Properties();
        // 首先加载默认的配置文件
        Properties defalutProperties = loadConfig(clazz, "application.properties");
        mergeProperties.putAll(defalutProperties);

        String property = defalutProperties.getProperty("spring.profiles.active");
        if (null != property && !property.isEmpty()) {
            // 添加环境配置文件
            Properties properties2 = loadConfig(clazz, "application-" + property + ".properties");

            // 将当前文件的属性添加到最终的属性集合中这将覆盖之前加载的同名属性
            mergeProperties.putAll(properties2);
        }
        return mergeProperties;
    }


    /**
     * 加载配置文件
     *
     * @param clazz          当前类必须要传，否则在多模块 SpringBoot 环境下获取的永远都是运行jar的资源文件中的属性值
     * @param configFilePath 配置文件路径
     * @return {@link Properties}
     */
    public Properties loadConfig(Class<?> clazz, String configFilePath) {
        Properties properties = new Properties();
        // 传统的方式，默认加载的是当前类所在的包路径下的配置文件
//        try (InputStream inputStream = Resources.getResourceAsStream(configFilePath); Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
        // 使用 ClassFindService 根据类获取类所在的配置文件
        URL inputStream = ClassFindService.getResourcePathByClz(clazz, configFilePath);
        try (
                Reader reader = new InputStreamReader(inputStream.openStream(), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException e) {
            log.error("Unable to load properties from {}", configFilePath);
            throw new RuntimeException("Unable to load properties from " + configFilePath, e);
        }
        return properties;
    }

    public Properties loadAndMergeProperties(String... filePaths) {
        Properties finalProperties = new Properties();

        // 按顺序加载每个文件
        for (String filePath : filePaths) {
            try (InputStream input = new FileInputStream(filePath)) {
                // 加载当前文件的属性
                Properties currentProperties = new Properties();
                currentProperties.load(input);

                // 将当前文件的属性添加到最终的属性集合中
                // 这将覆盖之前加载的同名属性
                finalProperties.putAll(currentProperties);
            } catch (IOException ex) {
                // 处理异常，例如打印错误信息或抛出运行时异常
                log.error("Failed to load properties from file: {}", Arrays.asList(filePaths));
                // 可以选择抛出异常或继续加载其他文件
                throw new RuntimeException("Failed to load properties.", ex);
            }
        }
        return finalProperties;
    }
}
