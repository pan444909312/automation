package com.miller.common.util;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: panjuxiang
 * @Since: 2025/8/5
 */
public class StringToListUtils {
    /**
     * 将逗号分隔的字符串转换为 List<String>
     *
     * @param str 以逗号分隔的字符串
     * @return 转换后的 List<String>，如果输入为 null 或空字符串则返回空 List
     */
    public static List<String> stringToList(String str) {
// 处理 null 和空字符串情况
        if (str == null || str.trim().isEmpty()) {
            return List.of(); // 返回空列表
        }
// 按逗号分割字符串，同时处理可能的空格
        String[] elements = str.split(",");
// 转换为 List 并返回
        return Arrays.asList(elements);
    }

    /**
     * 带 trim 选项的字符串转 List 方法
     *
     * @param str  以逗号分隔的字符串
     * @param trim 是否去除每个元素的前后空格
     * @return 转换后的 List<String>
     */
    public static List<String> stringToList(String str, boolean trim) {
        if (str == null || str.trim().isEmpty()) {
            return List.of();
        }
        String[] elements = str.split(",");
// 如果需要 trim，处理每个元素
        if (trim) {
            for (int i = 0; i < elements.length; i++) {
                elements[i] = elements[i].trim();
            }
        }
        return Arrays.asList(elements);
    }
}
