package com.miller.testcase.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miller.common.util.ULIDUtils;
import com.miller.service.framework.util.CurlParser;
import com.miller.service.framework.util.JGitUtils;

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
     * 生成包名
     * 将测试用例名称转换为包名格式，例如：
     * "用户_创建地址" -> "用户/创建地址"
     *
     * @param testCaseName 测试用例名称
     * @return 包名
     */
    private static String generatePackageName(String testCaseName) {
        return testCaseName.toLowerCase()
                .replace("_", "/")
                .replace(" ", "");
    }

    /**
     * 生成类名
     * 将测试用例名称转换为类名格式，例如：
     * "用户_创建地址" -> "CreateAddressTests"
     *
     * @param testCaseName 测试用例名称
     * @return 类名
     */
    private static String generateClassName(String testCaseName) {
        String[] words = testCaseName.split("_");
        StringBuilder className = new StringBuilder();
        for (String word : words) {
            className.append(word.substring(0, 1).toUpperCase())
                    .append(word.substring(1).toLowerCase());
        }
        return className + "Tests";
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
        String headersPath = "module/" + packageName + "/request/headers.json";
        String paramsPath = !parser.getParams().isEmpty() ? "module/" + packageName + "/request/params.json" : "null";
        String paramsValue = paramsPath.equals("null") ? "null" : "\"" + paramsPath + "\"";
        String bodyPath = "module/" + packageName + "/request/should_success.json";
        String assertPath = "module/" + packageName + "/response/assert_full_field.json";
        String scenarioId = ULIDUtils.generateULID();
        String gitName = JGitUtils.getGitName();
        String authorEmail = JGitUtils.getGitEmail();
        String template = loadTemplate(templateFile);
        String content = String.format(template,
                packageName.replace("/", "."),
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
        // 生成请求头文件
        String headersPath = resourcesBasePath + "/" + packageName + "/request/headers.json";
        File headersFile = new File(headersPath);
        File headersParent = headersFile.getParentFile();
        if (headersParent != null && !headersParent.exists()) {
            headersParent.mkdirs();
        }
        try (FileWriter writer = new FileWriter(headersFile)) {
            JSONObject headersJson = new JSONObject();
            // 将解析出的headers转换为JSON对象
            for (Map.Entry<String, String> entry : parser.getHeaders().entrySet()) {
                headersJson.put(entry.getKey(), entry.getValue());
            }
            writer.write(JSON.toJSONString(headersJson, true));
        }

        // 生成请求参数文件（如果有参数）
        String paramsPath = null;
        if (!parser.getParams().isEmpty()) {
            paramsPath = resourcesBasePath + "/" + packageName + "/request/params.json";
            File paramsFile = new File(paramsPath);
            File paramsParent = paramsFile.getParentFile();
            if (paramsParent != null && !paramsParent.exists()) {
                paramsParent.mkdirs();
            }
            try (FileWriter writer = new FileWriter(paramsFile)) {
                JSONObject paramsJson = new JSONObject();
                // 将解析出的params转换为JSON对象
                for (Map.Entry<String, String> entry : parser.getParams().entrySet()) {
                    paramsJson.put(entry.getKey(), entry.getValue());
                }
                writer.write(JSON.toJSONString(paramsJson, true));
            }
        }

        // 生成请求体文件
        if (parser.getBody() != null) {
            String requestPath = resourcesBasePath + "/" + packageName + "/request/should_success.json";
            File file = new File(requestPath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            try (FileWriter writer = new FileWriter(file)) {
                // 将请求体字符串解析为JSONObject，然后使用格式化输出
                JSONObject bodyJson = JSON.parseObject(parser.getBody());
                writer.write(JSON.toJSONString(bodyJson, true));
            }
        }

        // 生成响应体文件
        String responsePath = resourcesBasePath + "/" + packageName + "/response/assert_full_field.json";
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