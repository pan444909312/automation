package com.miller.testcaseuserapp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miller.common.util.ULIDUtils;
import com.miller.service.framework.util.CurlParser;
import com.miller.service.framework.util.JGitUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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
    /** Java源代码根目录 */
    private static final String MODULE_BASE_PATH = "src/main/java/com/miller/testcaseuserapp/module";
    /** 资源文件根目录 */
    private static final String RESOURCES_BASE_PATH = "src/main/resources/module";
    /** 邮箱后缀 */
    private static final String EMAIL_SUFFIX = "@hungrypandagroup.com";

    /**
     * 测试类模板
     * 使用Java文本块（Text Block）定义模板，包含以下占位符：
     * %s - 包名
     * %s - 测试用例名称
     * %s - 创建时间
     * %s - 场景ID（使用ULIDUtils.generateULID()生成，格式如：01JW68KNTBJSEZ0GPXQ9AF6XFN）
     * %s - 测试用例名称
     * %s - 测试用例名称（用于DisplayName）
     * %s - 类名
     * %s - 测试用例名称（用于方法DisplayName）
     * %s - 方法名
     * %s - 请求路径
     * %s - 请求方法
     * %s - 请求头文件路径
     * %s - 请求体文件路径
     * %s - 断言文件路径
     * %s - 测试用例名称（用于方法DisplayName）
     * %s - 方法名
     * %s - 作者邮箱（使用Git用户名 + @hungrypandagroup.com）
     * %s - 作者名（使用Git用户名）
     */
    private static final String TEST_CLASS_TEMPLATE = """
            package com.miller.testcaseuserapp.module.%s;

            import com.miller.service.framework.annotation.Scenario;
            import com.miller.testcaseuserapp.config.TestcaseConfig;
            import com.miller.testcaseuserapp.utils.TestCaseHelpful;
            import org.junit.jupiter.api.DisplayName;
            import org.junit.jupiter.api.Test;

            /**
             * %s
             *
             * @author %s
             * @version 2.0
             * @since %s
             */
            @Scenario(
                    scenarioID = "%s", // 使用ULIDUtils.generateULID()生成的唯一ID
                    scenarioName = "%s",
                    author = "%s",
                    developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
            @DisplayName("%s")
            public class %s {
                // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
                String uri = TestcaseConfig.HOST + "%s";
                // 接口请求方式。如： GET、POST、PUT、DELETE
                String method = "%s";
                // 请求头。默认从 resources 目录下读取文件。下面的代码表示从 resource 的 module/headers.json 读取该文件内容作为接口请求头
                String headers = "module/headers.json";
                // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
                String body = "%s";
                // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
                String assert1 = "%s";

                @DisplayName("%s")
                @Test
                void should%s() {
                    // 步骤1: 设置请求头。基本固定写法，不需要修改
                    var requestHeaders = TestCaseHelpful.getHeaders(headers);

                    // 步骤2: 设置请求体。基本固定写法，不需要修改
                    var requestBody = TestCaseHelpful.getJsonRequestBody(body);

                    // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
                    var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

                    // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
                    // 方式一： 全匹配， 忽略部分动态字段值。固定写法，不需要修改
                    var expectedStr = TestCaseHelpful.getFileContent(assert1);
                    TestCaseHelpful.assertThatJson(responseBody).isEqualTo(expectedStr);
                }
            }
            """;

    /**
     * 主方法，用于接收用户输入并生成测试用例
     * 1. 提示用户输入测试用例名称
     * 2. 提示用户输入cURL命令
     * 3. 调用generateTestCase方法生成测试用例
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入测试用例名称（例如：用户_创建地址）：");
        String testCaseName = scanner.nextLine();
        System.out.println("请输入cURL命令：");
        String curlCommand = scanner.nextLine();

        try {
            generateTestCase(testCaseName, curlCommand);
            System.out.println("测试用例生成成功！");
        } catch (IOException e) {
            System.err.println("生成测试用例失败：" + e.getMessage());
        }
    }

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
     * @throws IOException 如果文件操作失败
     */
    private static void generateTestCase(String testCaseName, String curlCommand) throws IOException {
        // 解析cURL命令
        CurlParser.ParsedRequest parser = CurlParser.parse(curlCommand);

        // 生成包名和类名
        String packageName = generatePackageName(testCaseName);
        String className = generateClassName(testCaseName);
        String methodName = generateMethodName(testCaseName);

        // 创建包目录
        createPackageDirectory(packageName);

        // 生成测试类文件
        generateTestClassFile(packageName, className, testCaseName, methodName, curlCommand, parser);

        // 生成请求体和响应体文件
        generateJsonFiles(packageName, className, parser);
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
     * 生成方法名
     * 将测试用例名称转换为方法名格式，例如：
     * "用户_创建地址" -> "CreateAddress"
     *
     * @param testCaseName 测试用例名称
     * @return 方法名
     */
    private static String generateMethodName(String testCaseName) {
        String[] words = testCaseName.split("_");
        StringBuilder methodName = new StringBuilder();
        for (String word : words) {
            methodName.append(word.substring(0, 1).toUpperCase())
                    .append(word.substring(1).toLowerCase());
        }
        return methodName.toString();
    }

    /**
     * 创建包目录结构
     * 1. 创建Java源代码目录
     * 2. 创建资源文件目录
     *
     * @param packageName 包名
     * @throws IOException 如果目录创建失败
     */
    private static void createPackageDirectory(String packageName) throws IOException {
        // 创建Java源文件目录
        Path javaPath = Paths.get(MODULE_BASE_PATH, packageName);
        Files.createDirectories(javaPath);

        // 创建资源文件目录
        Path resourcePath = Paths.get(RESOURCES_BASE_PATH, packageName);
        Files.createDirectories(resourcePath);
    }

    /**
     * 生成测试类文件
     * 1. 使用模板生成测试类内容
     * 2. 将内容写入文件
     *
     * @param packageName 包名
     * @param className 类名
     * @param testCaseName 测试用例名称
     * @param methodName 方法名
     * @param curlCommand cURL命令
     * @param parser cURL解析器
     * @throws IOException 如果文件写入失败
     */
    private static void generateTestClassFile(String packageName, String className, String testCaseName,
                                            String methodName, String curlCommand, CurlParser.ParsedRequest parser) throws IOException {
        String filePath = MODULE_BASE_PATH + "/" + packageName + "/" + className + ".java";

        // 生成文件路径
        String bodyPath = "module/" + packageName + "/request/should_success.json";
        String assertPath = "module/" + packageName + "/response/assert_full_field.json";

        // 使用ULIDUtils生成唯一的场景ID
        String scenarioId = ULIDUtils.generateULID();

        // 获取Git用户名并生成邮箱
        String gitName = JGitUtils.getGitName();
        String authorEmail = gitName + EMAIL_SUFFIX;

        String content = String.format(TEST_CLASS_TEMPLATE,
                packageName.replace("/", "."),
                testCaseName,
                gitName,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),
                scenarioId,
                testCaseName,
                authorEmail,
                testCaseName,
                className,
                parser.getUri(),
                parser.getMethod(),
                bodyPath,
                assertPath,
                testCaseName,
                methodName
        );

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        }
    }

    /**
     * 生成JSON文件
     * 1. 生成请求体JSON文件（如果有请求体）
     * 2. 生成响应体JSON文件
     *
     * @param packageName 包名
     * @param className 类名
     * @param parser cURL解析器
     * @throws IOException 如果文件写入失败
     */
    private static void generateJsonFiles(String packageName, String className, CurlParser.ParsedRequest parser) throws IOException {
        // 生成请求体文件
        if (parser.getBody() != null) {
            String requestPath = RESOURCES_BASE_PATH + "/" + packageName + "/request/should_success.json";
            try (FileWriter writer = new FileWriter(requestPath)) {
                writer.write(parser.getBody());
            }
        }

        // 生成响应体文件
        String responsePath = RESOURCES_BASE_PATH + "/" + packageName + "/response/assert_full_field.json";
        JSONObject responseJson = new JSONObject();
        responseJson.put("resultCode", 1000);
        try (FileWriter writer = new FileWriter(responsePath)) {
            writer.write(JSON.toJSONString(responseJson, true));
        }
    }
}