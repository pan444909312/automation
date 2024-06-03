package com.miller.service.framework.util;

import org.springframework.data.repository.init.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 文件工具类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/4/30 16:09:10
 */
public class FileUtils {


    /**
     * 读取 resources 目录下的文件内容并将其作为字符串返回。路径应以/开头，这表示从类路径的根开始。
     *
     * @param fileName resources目录下的文件名.例如：/application.properties
     * @return 文件内容
     */
    public static String readFileFromResources(String fileName) {
        // 获取资源的输入流
        InputStream inputStream = ResourceReader.class.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        }
        // 使用BufferedReader读取内容
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
