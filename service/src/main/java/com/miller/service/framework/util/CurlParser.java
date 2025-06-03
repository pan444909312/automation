package com.miller.service.framework.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析 curl 命令，注意：这里并不是完整的cURL 解析器，仅用于解析cURL命令，不一定能完全解析所有cURL命令。
 *
 * 1. 实现 cURL 字符串的结构化解析，为了兼容性所以使用了 Java 原生支持的能力。
 * 2. 将解析的内容分别提取。比如：请求的域名uri、请求头header、请求路径path、请求方法method、
 *    请求体（只需要考虑application/json和form-data，其他当作文本处理）、
 *    响应结果(只需要考虑响应结果是json格式，其他当作文本text处理)，
 *    需要注意不同的请求method可能数据结构不同，比如GET请求可能有Params参数，
 *    比如queryParm参数通常是以？key=value形式。。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/6/03 21:54:43
 */
public class CurlParser {

    public static class ParsedRequest {
        private String method;
        private String uri;
        private String path;
        private Map<String, String> headers = new LinkedHashMap<>();
        private Map<String, String> params = new LinkedHashMap<>();
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
            return headers;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    public ParsedRequest parse(String curlCommand) throws URISyntaxException {
        ParsedRequest result = new ParsedRequest();

        // 标准化输入：移除换行符和多余空格
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
        } catch (URISyntaxException e) {
            result.setPath(null);
        }

        // 解析查询参数
        parseQueryParams(result, url);

        // 解析headers
        parseHeaders(curlCommand, result);

        // 解析请求体
        parseBody(curlCommand, result);

        return result;
    }

    private String extractUrl(String curlCommand) {
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

    private void parseQueryParams(ParsedRequest request, String url) throws URISyntaxException {
        URI uri = new URI(url);
        String query = uri.getQuery();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                String key = (idx > 0) ? pair.substring(0, idx) : pair;
                String value = (idx > 0 && pair.length() > idx + 1) ? pair.substring(idx + 1) : null;
                request.getParams().put(key, value);
            }
        }
    }

    private void parseHeaders(String curlCommand, ParsedRequest request) {
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
                    request.getHeaders().put(key, value);
                }
            }

            index = endIndex;
        }
    }

    private void parseBody(String curlCommand, ParsedRequest request) {
        String[] bodyMarkers = {"--data-binary", "-d", "--data"};
        for (String marker : bodyMarkers) {
            if (curlCommand.contains(marker)) {
                String bodyValue = extractValue(curlCommand, marker);
                if (!bodyValue.isEmpty()) {
                    // 移除JSON数据周围的引号和转义
                    bodyValue = bodyValue.replaceAll("^[\"']|[\"']$", "").replace("\\\"", "\"");
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

    private String extractValue(String curlCommand, String option) {
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
            return curlCommand.substring(start, end + 1);
        } else {
            // 查找下一个空格或选项
            while (end < curlCommand.length() && curlCommand.charAt(end) != ' ' && !curlCommand.startsWith("-", end)) {
                end++;
            }
            return curlCommand.substring(start, end);
        }
    }
}