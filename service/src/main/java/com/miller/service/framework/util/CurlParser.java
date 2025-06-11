package com.miller.service.framework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析 curl 命令，注意：这里并不是完整的cURL 解析器，仅用于解析cURL命令，不一定能完全解析所有cURL命令。
 * 特别注意：保持参数的原始顺序对于接口调用非常重要，因此使用 LinkedHashMap 来存储参数。
 * 同时，JSON 字符串中的字段顺序也会被保持。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/6/03 21:54:43
 */
public class CurlParser {
    // 配置 FastJSON 使用 LinkedHashMap 来保持字段顺序
    static {
        JSON.DEFAULT_PARSER_FEATURE |= Feature.OrderedField.getMask();
    }

    public static class ParsedRequest {
        private String method;
        private String uri;
        private String path;
        // 使用 LinkedHashMap 保持 headers 的插入顺序
        private final Map<String, String> headers = new LinkedHashMap<>();
        // 使用 LinkedHashMap 保持 params 的插入顺序
        private final Map<String, String> params = new LinkedHashMap<>();
        private String body;

        // Getters and setters
        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public Map<String, String> getHeaders() {
            return Collections.unmodifiableMap(headers);
        }

        public Map<String, String> getParams() {
            return Collections.unmodifiableMap(params);
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            // 如果是 JSON 字符串，使用 LinkedHashMap 重新格式化以保持顺序
            if (body != null && (body.trim().startsWith("{") || body.trim().startsWith("["))) {
                try {
                    // 使用 OrderedField 特性解析 JSON
                    Object jsonObj = JSON.parse(body, Feature.OrderedField);
                    // 重新格式化 JSON 字符串，保持字段顺序
                    this.body = JSON.toJSONString(jsonObj, true);
                } catch (Exception e) {
                    // 如果解析失败，保持原始字符串
                    this.body = body;
                }
            } else {
                this.body = body;
            }
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        // 添加 header，保持顺序
        public void addHeader(String key, String value) {
            headers.put(key, value);
        }

        // 添加 param，保持顺序
        public void addParam(String key, String value) {
            params.put(key, value);
        }
    }

    /**
     * 解析cURL命令为结构化请求对象，保持参数的原始顺序
     * @param curlCommand cURL命令
     * @return 结构化请求对象
     */
    public static ParsedRequest parse(String curlCommand) {
        ParsedRequest result = new ParsedRequest();
        try {
            // 标准化输入：移除换行符和多余空格，但保留必要的空格
            curlCommand = curlCommand.replaceAll("\\s+", " ").trim();

            // 解析方法（默认GET）
            result.setMethod("GET");
            if (curlCommand.contains("-X ")) {
                result.setMethod(extractValue(curlCommand, "-X").toUpperCase());
            }

            // 解析URL
            String url = extractUrl(curlCommand);
            result.setUri(url);

            // 从uri中提取path
            try {
                URI uriObj = new URI(url);
                result.setPath(uriObj.getPath());
                // 解析查询参数
                parseQueryParams(result, uriObj);
            } catch (URISyntaxException e) {
                result.setPath(null);
            }

            // 解析headers和body
            parseHeadersAndBody(curlCommand, result);
        } catch (Exception e) {
            throw new RuntimeException("cURL解析失败: " + e.getMessage(), e);
        }
        return result;
    }

    private static void parseHeadersAndBody(String curlCommand, ParsedRequest request) {
        // 使用更可靠的方式解析headers
        int index = curlCommand.indexOf("-H");
        while (index != -1) {
            // 找到下一个header位置
            int endIndex = curlCommand.indexOf("-H", index + 2);
            String headerBlock = curlCommand.substring(index + 2, endIndex != -1 ? endIndex : curlCommand.length()).trim();

            // 提取header值（可能带引号）
            Matcher matcher = Pattern.compile("[\"'](.*?)[\"']").matcher(headerBlock);
            if (matcher.find()) {
                String header = matcher.group(1);
                int colonIndex = header.indexOf(':');
                if (colonIndex != -1) {
                    String key = header.substring(0, colonIndex).trim();
                    String value = header.substring(colonIndex + 1).trim();
                    request.addHeader(key, value);
                }
            }

            index = endIndex;
        }

        // 解析body
        String[] bodyMarkers = {"--data-binary", "-d", "--data"};
        for (String marker : bodyMarkers) {
            if (curlCommand.contains(marker)) {
                String bodyValue = extractBodyValue(curlCommand, marker);
                if (!bodyValue.isEmpty()) {
                    request.setBody(bodyValue);
                    // 如果方法未指定且有body，默认为POST
                    if ("GET".equals(request.getMethod())) {
                        request.setMethod("POST");
                    }
                    break;
                }
            }
        }
    }

    private static void parseQueryParams(ParsedRequest request, URI uri) {
        String query = uri.getQuery();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                if (idx > 0) {
                    String key = pair.substring(0, idx);
                    String value = pair.substring(idx + 1);
                    request.addParam(key, value);
                }
            }
        }
    }

    private static String extractUrl(String curlCommand) {
        // 查找URL位置（最后一个非选项参数）
        String[] parts = curlCommand.split(" ");
        for (int i = parts.length - 1; i >= 0; i--) {
            if (!parts[i].startsWith("-") && !parts[i].equals("curl")) {
                // 移除可能的引号
                return parts[i].replaceAll("^[\"']|[\"']$", "");
            }
        }
        throw new IllegalArgumentException("URL not found in cURL command");
    }

    private static String extractBodyValue(String curlCommand, String marker) {
        int index = curlCommand.indexOf(marker);
        if (index == -1) return "";

        int start = index + marker.length();
        // 跳过空格
        while (start < curlCommand.length() && curlCommand.charAt(start) == ' ') {
            start++;
        }

        if (start >= curlCommand.length()) return "";

        // 找到下一个引号开始的位置
        while (start < curlCommand.length() && curlCommand.charAt(start) != '"' && curlCommand.charAt(start) != '\'') {
            start++;
        }

        if (start >= curlCommand.length()) return "";

        char quoteChar = curlCommand.charAt(start);
        StringBuilder body = new StringBuilder();
        boolean escaped = false;

        // 从引号后开始读取
        for (int i = start + 1; i < curlCommand.length(); i++) {
            char c = curlCommand.charAt(i);
            
            if (escaped) {
                body.append(c);
                escaped = false;
                continue;
            }

            if (c == '\\') {
                escaped = true;
                continue;
            }

            if (c == quoteChar) {
                // 检查是否是转义的引号
                if (i > 0 && curlCommand.charAt(i - 1) == '\\') {
                    body.append(c);
                } else {
                    break;
                }
            } else {
                body.append(c);
            }
        }

        String result = body.toString();
        // 如果是 JSON 字符串，使用 LinkedHashMap 重新格式化以保持顺序
        if (result.trim().startsWith("{") || result.trim().startsWith("[")) {
            try {
                // 使用 OrderedField 特性解析 JSON
                Object jsonObj = JSON.parse(result, Feature.OrderedField);
                // 重新格式化 JSON 字符串，保持字段顺序
                return JSON.toJSONString(jsonObj, true);
            } catch (Exception e) {
                // 如果解析失败，返回原始字符串
                return result;
            }
        }
        return result;
    }

    private static String extractValue(String curlCommand, String option) {
        int index = curlCommand.indexOf(option);
        if (index == -1) return "";

        int start = index + option.length();
        // 跳过空格
        while (start < curlCommand.length() && curlCommand.charAt(start) == ' ') {
            start++;
        }

        if (start >= curlCommand.length()) return "";

        char quoteChar = curlCommand.charAt(start);
        boolean quoted = (quoteChar == '\'' || quoteChar == '"');

        int end = start + 1;
        if (quoted) {
            // 查找匹配的引号
            while (end < curlCommand.length() && curlCommand.charAt(end) != quoteChar) {
                // 处理转义引号
                if (curlCommand.charAt(end) == '\\' && end + 1 < curlCommand.length()) {
                    end++;
                }
                end++;
            }
            return curlCommand.substring(start + 1, end);
        } else {
            // 查找下一个空格或选项
            while (end < curlCommand.length() && curlCommand.charAt(end) != ' ' && !curlCommand.startsWith("-", end)) {
                end++;
            }
            return curlCommand.substring(start, end);
        }
    }
}