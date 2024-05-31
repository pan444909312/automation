package com.miller.service.framework.util;

import org.apache.ibatis.io.Resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 处理不同的配置文件，参考: SpringBoot 的 application.properties 使用方式
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/5/31 14:48:03
 */
public class ApplicationPropertiesUtils {
    static Properties mergeProperties = new Properties();

    /**
     * 加载配置文件
     * <p>
     * 默认会读取resources目录下的 application.properties 作为配置文件。
     * 可以通过 spring.profiles.active 来配置多个配置文件。
     * 如果 存在 application-{profile}.properties 则会读取该配置文件内容，最后合并。
     * </p>
     *
     * @return
     */
    public static Properties loadProperties() {
        // 首先加载默认的配置文件
        Properties defalutProperties = loadConfig("application.properties");
        mergeProperties.putAll(defalutProperties);

        String property = defalutProperties.getProperty("spring.profiles.active");
        if (null != property && !property.isEmpty()) {
            // 添加环境配置文件
            Properties properties2 = loadConfig("application-" + property + ".properties");

            // 将当前文件的属性添加到最终的属性集合中这将覆盖之前加载的同名属性
            mergeProperties.putAll(properties2);
        }
        return mergeProperties;

    }

    private static Properties loadConfig(String configFilePath) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(configFilePath);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public static Properties loadAndMergeProperties(String... filePaths) {
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
                System.err.println("Unable to load properties from file: " + filePath);
                ex.printStackTrace();
                // 可以选择抛出异常或继续加载其他文件
                // throw new RuntimeException("Failed to load properties.", ex);
            }
        }

        return finalProperties;
    }
}
