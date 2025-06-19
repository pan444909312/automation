package com.miller.testcase.factory;

import com.miller.testcase.utils.TestCaseGenerator;

import java.io.IOException;
import java.util.Scanner;

/**
 * 测试用例工厂
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/5/30 11:46:53
 */
public class TestcaseFactory {
    // 用户可自定义的子目录（如 "account/address"），默认为空字符串，则默认在 module 目录下
    public static String CUSTOM_SUB_PATH = "erp/merchant";

    // 自动创建测试用例入口
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入测试用例名称，支持空格或-分割名称，建议英文空格分割（例如：user create address 或 用户-创建地址):");
        String testCaseName = scanner.nextLine();
        System.out.println("请粘贴从 Charles 拷贝过来的 cURL 命令：");
        String curlCommand = scanner.nextLine();
        try {
            createTestCase(testCaseName, curlCommand);
            System.out.println("测试用例生成成功！");
        } catch (IOException e) {
            System.err.println("生成测试用例失败：" + e.getMessage());
        }
    }

    // 测试用例模版文件
    public static final String TEMPLATE_FILE = "TestClassTemplate.txt";

    /**
     * 不同项目模块名称不同，第一次需要配置。例如：
     * C + P 端："testcase-user-app"
     * B端：testcase-merchant
     * D端：testcase-delivery
     * PF：testcase-pandafresh
     */
    public static final String MODULE_NAME = "testcase-user-app";

    // 默认测试用例生成路径，不建议修改
    public static final String JAVA_BASE_PATH = System.getProperty("user.dir") + "/" + MODULE_NAME + "/src/main/java/com/miller/testcase/module";
    public static final String RESOURCES_BASE_PATH = System.getProperty("user.dir") + "/" + MODULE_NAME + "/src/main/resources/module";

    /**
     * 统一对外方法，自动创建测试用例
     */
    public static void createTestCase(String testCaseName, String curlCommand) throws IOException {
        String javaPath = JAVA_BASE_PATH + (CUSTOM_SUB_PATH.isEmpty() ? "" : "/" + CUSTOM_SUB_PATH);
        String resourcesPath = RESOURCES_BASE_PATH + (CUSTOM_SUB_PATH.isEmpty() ? "" : "/" + CUSTOM_SUB_PATH);
        TestCaseGenerator.generateTestCase(
                testCaseName,
                curlCommand,
                javaPath,
                resourcesPath,
                TEMPLATE_FILE
        );
    }
}
