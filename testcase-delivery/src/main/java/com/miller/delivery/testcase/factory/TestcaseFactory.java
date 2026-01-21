package com.miller.delivery.testcase.factory;

import com.miller.delivery.testcase.utils.TestCaseGenerator;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.launcher.TestCaseRunnerLauncher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

/**
 * 测试用例工厂
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/5/30 11:46:53
 */
@Scenario(scenarioID = "01KEGYNHVG4R5N4MAGFBVZASDE", scenarioName = "D测试用例工厂_一键自动创建用例",
        author = "panjuxiang@hungrypandagroup.com",
        developmentTime = 8 * 60, maintenanceTime = 2 * 60, manualTestTime = 10)
@DisplayName("测试用例工厂")
public class TestcaseFactory {
    // 用户可自定义的子目录（如 "account/address"），默认为空字符串，则默认在 module 目录下
    public static String CUSTOM_SUB_PATH = "dispatch/test11";

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
            new TestCaseRunnerLauncher().runTestMethod(TestcaseFactory.class, "reportedData");
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
    public static final String MODULE_NAME = "testcase-delivery";

    // 默认测试用例生成路径，不建议修改
    public static final String JAVA_BASE_PATH = System.getProperty("user.dir") + "/" + MODULE_NAME + "/src/main/java/com/miller/delivery/testcase/module";
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

    @Test
    @DisplayName("一键自动创建用例")
    void reportedData() {
        // 什么都不需要做，仅仅是作为数据上报，复用现在测试框架功能
    }
}
