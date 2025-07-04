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
 * 解析 curl 命令，支持多种来源的 cURL 命令解析
 * 特别注意：
 * 1. 保持参数的原始顺序对于接口调用非常重要，因此使用 LinkedHashMap 来存储参数
 * 2. JSON 字符串中的字段顺序会被保持
 * 3. 请求头（Headers）的顺序会被保持
 * 4. URL 查询参数（Query Parameters）的顺序会被保持
 *
 * @author Miller Shan
 * @version 1.1
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
        // 使用 LinkedHashMap 保持 headers 的插入顺序，确保请求头的顺序与原始 cURL 命令一致
        private final LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        // 使用 LinkedHashMap 保持 params 的插入顺序，确保查询参数的顺序与原始 URL 一致
        private final LinkedHashMap<String, String> params = new LinkedHashMap<>();
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

        /**
         * 获取请求头，返回不可修改的 Map 以保持顺序
         * @return 保持顺序的请求头 Map
         */
        public Map<String, String> getHeaders() {
            return Collections.unmodifiableMap(headers);
        }

        /**
         * 获取查询参数，返回不可修改的 Map 以保持顺序
         * @return 保持顺序的查询参数 Map
         */
        public Map<String, String> getParams() {
            return Collections.unmodifiableMap(params);
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            // 检查 content-type 是否为 application/x-www-form-urlencoded
            String contentType = headers.get("content-type");
            if (body != null && contentType != null && 
                contentType.toLowerCase().contains("application/x-www-form-urlencoded")) {
                // 将 form-data 转换为 JSON 格式
                this.body = convertFormDataToJson(body);
                return;
            }
            
            // 如果是 JSON 字符串，使用 LinkedHashMap 重新格式化以保持顺序
            if (body != null && (body.trim().startsWith("{") || body.trim().startsWith("["))) {
                try {
                    // 使用 OrderedField 特性解析 JSON
                    Object jsonObj = JSON.parse(body, Feature.OrderedField);
                    // 重新格式化 JSON 字符串，保持字段顺序并保留 null 字段
                    this.body = JSON.toJSONString(jsonObj,
                        com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue,
                        com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat
                    );
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

        /**
         * 添加请求头，保持顺序
         * @param key 请求头名称
         * @param value 请求头值
         */
        public void addHeader(String key, String value) {
            headers.put(key, value);
        }

        /**
         * 添加查询参数，保持顺序
         * @param key 参数名
         * @param value 参数值
         */
        public void addParam(String key, String value) {
            params.put(key, value);
        }

        /**
         * 获取所有请求头的字符串表示，保持顺序
         * @return 格式化的请求头字符串
         */
        public String getHeadersString() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            return sb.toString();
        }

        /**
         * 获取所有查询参数的字符串表示，保持顺序
         * @return 格式化的查询参数字符串
         */
        public String getParamsString() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            }
            return sb.toString();
        }

        /**
         * 将 form-data 格式的字符串转换为 JSON 格式
         * 例如：areaCode=86&account=18968046019 转换为 {"areaCode":"86","account":"18968046019"}
         * 
         * @param formData form-data 格式的字符串
         * @return JSON 格式的字符串
         */
        private String convertFormDataToJson(String formData) {
            if (formData == null || formData.trim().isEmpty()) {
                return "{}";
            }
            
            try {
                // 使用 LinkedHashMap 保持参数顺序
                LinkedHashMap<String, String> formParams = new LinkedHashMap<>();
                
                // 按 & 分割参数
                String[] pairs = formData.split("&");
                for (String pair : pairs) {
                    int idx = pair.indexOf("=");
                    if (idx > 0) {
                        String key = pair.substring(0, idx);
                        String value = pair.substring(idx + 1);
                        // URL 解码
                        try {
                            key = java.net.URLDecoder.decode(key, "UTF-8");
                            value = java.net.URLDecoder.decode(value, "UTF-8");
                        } catch (Exception e) {
                            // 如果解码失败，保持原始值
                        }
                        formParams.put(key, value);
                    }
                }
                
                // 转换为 JSON 格式，保持顺序
                return JSON.toJSONString(formParams,
                    com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue,
                    com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat
                );
            } catch (Exception e) {
                // 如果转换失败，返回原始字符串
                return formData;
            }
        }
    }

    /**
     * 解析cURL命令为结构化请求对象，保持参数的原始顺序
     * @param curlCommand cURL命令
     * @return 结构化请求对象
     */
    public static ParsedRequest parse(String curlCommand) {
        // 检测 cURL 命令来源
        CurlSource source = detectCurlSource(curlCommand);
        
        // 根据来源选择不同的解析方法
        switch (source) {
            case CHARLES:
                return parseCharlesCurl(curlCommand);
            case CHROME:
                // TODO: 待完成
                return parseChromeCurl(curlCommand);
            default:
                // 默认使用 Charles 解析方式
                return parseCharlesCurl(curlCommand);
        }
    }

    /**
     * 检测 cURL 命令的来源类型
     * @param curlCommand cURL命令
     * @return cURL命令来源类型
     */
    private static CurlSource detectCurlSource(String curlCommand) {
        // 标准化输入：移除换行符和多余空格
        String normalized = curlCommand.replaceAll("\\s+", " ").trim();
        
        // Chrome 特征：
        // 1. URL 用单引号包裹
        // 2. 请求头用单引号包裹
        // 3. 整个命令中单引号使用更频繁，且不包含双引号
        if (curlCommand.contains("'") && 
            (curlCommand.contains("curl '") || curlCommand.contains(" -H '")) &&
            !curlCommand.contains("\"")) {
            return CurlSource.CHROME;
        }
        
        // Charles 特征：
        // 1. 使用双引号包裹参数
        // 2. 或者虽然包含单引号，但主要是双引号格式
        if (normalized.contains("\"")) {
            return CurlSource.CHARLES;
        }
        
        // 如果只包含单引号，检查是否是 Chrome 格式
        if (normalized.contains("'")) {
            // 检查是否包含典型的 Chrome 格式特征
            if (normalized.contains("curl '") || normalized.contains(" -H '")) {
                return CurlSource.CHROME;
            }
        }
        
        // 默认使用 Charles 格式
        return CurlSource.CHARLES;
    }

    /**
     * 解析 Charles 格式的 cURL 命令
     */
    private static ParsedRequest parseCharlesCurl(String curlCommand) {
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
            throw new RuntimeException("Charles cURL解析失败: " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * 解析 Chrome 格式的 cURL 命令
     */
    private static ParsedRequest parseChromeCurl(String curlCommand) {
        ParsedRequest result = new ParsedRequest();
        try {
            // 标准化输入：处理换行符
            String normalized = curlCommand.replaceAll("\\\\\n", " ").trim();
            
            // 解析URL（Chrome格式的URL通常用单引号包裹）
            String url = extractChromeUrl(normalized);
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

            // 解析方法（默认GET）
            result.setMethod("GET");
            if (normalized.contains("-X ")) {
                result.setMethod(extractValue(normalized, "-X").toUpperCase());
            }

            // 解析headers和body
            parseChromeHeadersAndBody(normalized, result);
        } catch (Exception e) {
            throw new RuntimeException("Chrome cURL解析失败: " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * 提取 Chrome 格式的 URL
     */
    private static String extractChromeUrl(String curlCommand) {
        // 尝试多种 URL 模式
        Pattern[] urlPatterns = {
            // 模式1: curl 'URL'
            Pattern.compile("curl\\s+'([^']+)'"),
            // 模式2: curl -H ... 'URL' (URL 在最后)
            Pattern.compile("'([^']+)'\\s*$"),
            // 模式3: curl -H ... -H ... 'URL'
            Pattern.compile("'([^']+)'\\s*(?:-H|--data|--compressed|$)"),
            // 模式4: 查找最后一个单引号包围的 URL
            Pattern.compile("'([^']*https?://[^']*)'")
        };
        
        for (Pattern pattern : urlPatterns) {
            Matcher matcher = pattern.matcher(curlCommand);
            if (matcher.find()) {
                String url = matcher.group(1);
                // 验证是否是有效的 URL
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    return url;
                }
            }
        }
        
        // 如果上述模式都失败，尝试从 Charles 格式的 extractUrl 方法
        try {
            return extractUrl(curlCommand);
        } catch (Exception e) {
            throw new IllegalArgumentException("URL not found in Chrome cURL command");
        }
    }

    /**
     * 解析 Chrome 格式的 headers 和 body
     */
    private static void parseChromeHeadersAndBody(String curlCommand, ParsedRequest request) {
        // 解析 headers - 支持多种格式
        Pattern[] headerPatterns = {
            // 模式1: -H 'key: value'
            Pattern.compile("-H\\s+'([^:]+):\\s*([^']+)'"),
            // 模式2: -H "key: value" (Chrome 有时也会用双引号)
            Pattern.compile("-H\\s+\"([^:]+):\\s*([^\"]+)\""),
            // 模式3: -H 'key:value' (没有空格)
            Pattern.compile("-H\\s+'([^:]+):([^']+)'")
        };
        
        for (Pattern pattern : headerPatterns) {
            Matcher matcher = pattern.matcher(curlCommand);
            while (matcher.find()) {
                String key = matcher.group(1).trim();
                String value = matcher.group(2).trim();
                request.addHeader(key, value);
            }
        }

        // 解析 body - 支持多种 body 标记
        String[] bodyMarkers = {"--data-raw", "--data-binary", "-d", "--data"};
        for (String marker : bodyMarkers) {
            if (curlCommand.contains(marker)) {
                String bodyValue = extractChromeBodyValue(curlCommand, marker);
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

    /**
     * 提取 Chrome 格式的 body 值
     */
    private static String extractChromeBodyValue(String curlCommand, String marker) {
        // 尝试多种 body 模式
        Pattern[] bodyPatterns = {
            // 模式1: --data-raw 'value'
            Pattern.compile(marker + "\\s+'([^']+)'"),
            // 模式2: --data-raw "value" (Chrome 有时也会用双引号)
            Pattern.compile(marker + "\\s+\"([^\"]+)\""),
            // 模式3: --data-raw 'value' 后面还有其他参数
            Pattern.compile(marker + "\\s+'([^']+)'\\s*(?:-H|--compressed|$)")
        };
        
        for (Pattern pattern : bodyPatterns) {
            Matcher matcher = pattern.matcher(curlCommand);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        
        return "";
    }

    private static void parseHeadersAndBody(String curlCommand, ParsedRequest request) {
        // 使用更可靠的方式解析headers
        Pattern headerPattern = Pattern.compile("-H\\s+[\"']([^:]+):\\s*([^\"']+)[\"']");
        Matcher matcher = headerPattern.matcher(curlCommand);
        
        while (matcher.find()) {
            String key = matcher.group(1).trim();
            String value = matcher.group(2).trim();
            request.addHeader(key, value);
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
        // 只做 unescape，不做 fastjson 解析
        result = result.replace("\\\"", "\"").replace("\\\\", "\\");
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