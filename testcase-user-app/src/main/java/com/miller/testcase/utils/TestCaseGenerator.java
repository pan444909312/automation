package com.miller.testcase.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miller.common.util.ULIDUtils;
import com.miller.service.framework.util.CurlParser;
import com.miller.service.framework.util.JGitUtils;
import com.miller.testcase.factory.TestcaseFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 测试用例生成器
 * 该工具类用于根据cURL命令自动生成测试用例，包括：
 * 1. 自动生成测试类文件
 * 2. 自动生成请求体JSON文件
 * 3. 自动生成响应体JSON文件，内容为空，因为 cURL命令没有响应体
 * 4. 自动生成目录结构
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/5/29
 */
public class TestCaseGenerator {
    /**
     * 生成测试用例的主方法
     * 1. 解析cURL命令
     * 2. 生成包名、类名和方法名
     * 3. 创建目录结构
     * 4. 生成测试类文件
     * 5. 生成JSON文件
     *
     * @param testCaseName 测试用例名称
     * @param curlCommand cURL命令
     * @param javaBasePath Java源代码根目录
     * @param resourcesBasePath 资源文件根目录
     * @param templateFile 模板文件名
     * @throws IOException 如果文件操作失败
     */
    public static void generateTestCase(String testCaseName, String curlCommand, String javaBasePath, String resourcesBasePath, String templateFile) throws IOException {
        // 解析cURL命令
        CurlParser.ParsedRequest parser = CurlParser.parse(curlCommand);

        // 生成包名和类名
        String packageName = generatePackageName(testCaseName);
        String className = generateClassName(testCaseName);

        // 创建包目录
        createPackageDirectory(packageName, javaBasePath, resourcesBasePath);

        // 生成测试类文件
        generateTestClassFile(packageName, className, testCaseName, curlCommand, parser, javaBasePath, resourcesBasePath, templateFile);

        // 生成请求体和响应体文件
        generateJsonFiles(packageName, className, parser, resourcesBasePath);
    }

    private static void createPackageDirectory(String packageName, String javaBasePath, String resourcesBasePath) throws IOException {
        // 创建Java源文件目录
        Path javaPath = Paths.get(javaBasePath, packageName);
        Files.createDirectories(javaPath);
        // 创建资源文件目录
        Path resourcePath = Paths.get(resourcesBasePath, packageName);
        Files.createDirectories(resourcePath);
    }

    /**
     * 规范化测试用例名称，使其符合Java命名规范
     * 1. 将特殊字符（如-、空格等）转换为下划线
     * 2. 移除其他非法字符
     * 3. 确保首字符是字母或下划线
     *
     * @param testCaseName 原始测试用例名称
     * @return 规范化后的名称
     */
    private static String normalizeTestCaseName(String testCaseName) {
        if (testCaseName == null || testCaseName.isEmpty()) {
            return "unnamed";
        }

        // 1. 将特殊字符转换为下划线
        String normalized = testCaseName
                .replaceAll("[-\\s\\.,;:!?@#$%^&*()+=<>\\[\\]{}|\\\\/]", "_") // 将特殊字符转换为下划线
                .replaceAll("_+", "_") // 将多个连续的下划线替换为单个下划线
                .replaceAll("^_+|_+$", ""); // 移除首尾的下划线

        // 2. 如果首字符不是字母或下划线，添加前缀
        if (!normalized.isEmpty() && !Character.isJavaIdentifierStart(normalized.charAt(0))) {
            normalized = "test_" + normalized;
        }

        // 3. 确保名称不为空
        if (normalized.isEmpty()) {
            normalized = "unnamed";
        }

        return normalized;
    }

    /**
     * 生成包名
     * 将测试用例名称转换为合法的Java包名
     *
     * @param testCaseName 测试用例名称
     * @return 包名
     */
    private static String generatePackageName(String testCaseName) {
        String normalized = normalizeTestCaseName(testCaseName);
        // 包名全部小写
        return normalized.toLowerCase();
    }

    /**
     * 生成类名
     * 如果是英文，将每个单词首字母转换为大写；如果是中文，保持原样
     *
     * @param testCaseName 测试用例名称
     * @return 类名
     */
    private static String generateClassName(String testCaseName) {
        String normalized = normalizeTestCaseName(testCaseName);
        
        // 判断是否包含中文字符
        boolean containsChinese = normalized.matches(".*[\\u4e00-\\u9fa5].*");
        
        if (containsChinese) {
            // 如果包含中文，直接添加Tests后缀
            return normalized + "_Tests";
        } else {
            // 如果是英文，将每个单词首字母转换为大写
            if (normalized.isEmpty()) {
                return "_Tests";
            }
            
            // 使用下划线分割单词
            String[] words = normalized.split("_");
            StringBuilder result = new StringBuilder();
            
            // 将每个单词首字母转换为大写
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (!word.isEmpty()) {
                    result.append(word.substring(0, 1).toUpperCase())
                          .append(word.substring(1).toLowerCase());
                }
                // 如果不是最后一个单词，添加下划线
                if (i < words.length - 1) {
                    result.append("_");
                }
            }
            
            return result.toString() + "_Tests";
        }
    }

    /**
     * 从 resources/template 目录下加载模板内容
     */
    private static String loadTemplate(String templateName) throws IOException {
        try (var is = TestCaseGenerator.class.getClassLoader().getResourceAsStream("template/" + templateName)) {
            if (is == null) throw new IOException("模板文件未找到: " + templateName);
            return new String(is.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        }
    }

    /**
     * 生成测试类文件
     * 1. 使用模板生成测试类内容
     * 2. 将内容写入文件
     *
     * @param packageName 包名
     * @param className 类名
     * @param testCaseName 测试用例名称
     * @param curlCommand cURL命令
     * @param parser cURL解析器
     * @param javaBasePath Java源代码根目录
     * @param resourcesBasePath 资源文件根目录
     * @param templateFile 模板文件名
     * @throws IOException 如果文件写入失败
     */
    private static void generateTestClassFile(String packageName, String className, String testCaseName,
                                              String curlCommand, CurlParser.ParsedRequest parser,
                                              String javaBasePath, String resourcesBasePath, String templateFile) throws IOException {
        String filePath = javaBasePath + "/" + packageName + "/" + className + ".java";
        
        // 构建完整的包名，包含子目录
        String fullPackageName = packageName;
        if (!TestcaseFactory.CUSTOM_SUB_PATH.isEmpty()) {
            String customPackage = TestcaseFactory.CUSTOM_SUB_PATH.replace("/", ".");
            fullPackageName = customPackage + "." + packageName;
        }
        
        // 构建资源文件路径，确保包含子目录
        String modulePrefix = "module/" + (TestcaseFactory.CUSTOM_SUB_PATH.isEmpty() ? "" : TestcaseFactory.CUSTOM_SUB_PATH + "/");
        String headersPath = modulePrefix + packageName + "/request/headers.json";
        String paramsPath = !parser.getParams().isEmpty() ? modulePrefix + packageName + "/request/params.json" : "null";
        String paramsValue = paramsPath.equals("null") ? "null" : "\"" + paramsPath + "\"";
        String bodyPath = modulePrefix + packageName + "/request/should_success.json";
        String assertPath = modulePrefix + packageName + "/response/assert_full_field.json";
        
        String scenarioId = ULIDUtils.generateULID();
        String gitName = JGitUtils.getGitName();
        String authorEmail = JGitUtils.getGitEmail();
        String template = loadTemplate(templateFile);
        String content = String.format(template,
                fullPackageName,  // 使用完整的包名
                testCaseName,
                gitName,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),
                scenarioId,
                testCaseName,
                authorEmail,
                testCaseName,
                className,
                parser.getPath(),
                parser.getMethod(),
                headersPath,
                paramsValue,
                bodyPath,
                assertPath
        );
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        }
    }

    /**
     * 生成JSON文件
     * 1. 生成请求头文件
     * 2. 生成请求参数文件（如果有参数）
     * 3. 生成请求体文件
     * 4. 生成响应体文件
     *
     * @param packageName 包名
     * @param className 类名
     * @param parser cURL解析器
     * @param resourcesBasePath 资源文件根目录
     * @throws IOException 如果文件写入失败
     */
    private static void generateJsonFiles(String packageName, String className, CurlParser.ParsedRequest parser, String resourcesBasePath) throws IOException {
        // 构建资源文件基础路径
        String baseResourcePath = resourcesBasePath + "/" + packageName;

        // 生成请求头文件
        String headersPath = baseResourcePath + "/request/headers.json";
        File headersFile = new File(headersPath);
        File headersParent = headersFile.getParentFile();
        if (headersParent != null && !headersParent.exists()) {
            headersParent.mkdirs();
        }
        try (FileWriter writer = new FileWriter(headersFile)) {
            JSONObject headersJson = new JSONObject();
            for (Map.Entry<String, String> entry : parser.getHeaders().entrySet()) {
                headersJson.put(entry.getKey(), entry.getValue());
            }
            writer.write(JSON.toJSONString(headersJson, true));
        }

        // 生成请求参数文件（如果有参数）
        if (!parser.getParams().isEmpty()) {
            String paramsPath = baseResourcePath + "/request/params.json";
            File paramsFile = new File(paramsPath);
            File paramsParent = paramsFile.getParentFile();
            if (paramsParent != null && !paramsParent.exists()) {
                paramsParent.mkdirs();
            }
            try (FileWriter writer = new FileWriter(paramsFile)) {
                JSONObject paramsJson = new JSONObject();
                for (Map.Entry<String, String> entry : parser.getParams().entrySet()) {
                    paramsJson.put(entry.getKey(), entry.getValue());
                }
                writer.write(JSON.toJSONString(paramsJson, true));
            }
        }

        // 生成请求体文件
        if (parser.getBody() != null) {
            String requestPath = baseResourcePath + "/request/should_success.json";
            File file = new File(requestPath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            try (FileWriter writer = new FileWriter(file)) {
                Object obj = JSON.parse(parser.getBody());
                // 这样能保留所有 null 字段
                writer.write(JSON.toJSONString(obj, com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue, com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat));
            }
        }

        // 生成响应体文件
        String responsePath = baseResourcePath + "/response/assert_full_field.json";
        File responseFile = new File(responsePath);
        File responseParent = responseFile.getParentFile();
        if (responseParent != null && !responseParent.exists()) {
            responseParent.mkdirs();
        }
        JSONObject responseJson = new JSONObject();
        responseJson.put("resultCode", 1000);
        try (FileWriter writer = new FileWriter(responseFile)) {
            writer.write(JSON.toJSONString(responseJson, true));
        }
    }
}