package com.miller.testcase.工具;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON工具类，提供JSON相关的操作方法
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/5/27 14:56:18
 */
public class JSON工具 {
    /**
     * 读取 Json 文件内容并转换为 Map 对象
     *
     * @param jsonFilePath Json 文件路径
     * @return Map对象，如果发生异常则返回空Map
     */
    static Map<String, Object> readJsonFileToMap(String jsonFilePath) {
        try {
            String fileContent = getFileContent(jsonFilePath);

            // 校验JSON格式
            if (!isValidJson(fileContent)) {
                throw new IllegalArgumentException("Invalid JSON format in file: " + jsonFilePath);
            }
            // 转换为Map对象
            return JSON.parseObject(fileContent, Map.class);
        } catch (JSONException e) {
            System.err.println("Error parsing JSON from file: " + jsonFilePath + ", error: " + e.getMessage());
            return new HashMap<>();
        }
    }

    /**
     * 校验字符串是否为合法的JSON格式
     *
     * @param jsonStr JSON字符串
     * @return 是否为合法的JSON
     */
    private static boolean isValidJson(String jsonStr) {
        try {
            JSON.parse(jsonStr);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }


    /**
     * 获取 resources 目录下指定文件内容
     * @param filePath 文件路径
     * @return 文件内容
     */
    static String getFileContent(String filePath) {
        try {
            ClassLoader classLoader = JSON工具.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new IllegalArgumentException("找不到文件: " + filePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                return content.toString();
            }
        } catch (IOException e) {
            throw new RuntimeException("读取文件失败: " + filePath, e);
        }
    }
}
