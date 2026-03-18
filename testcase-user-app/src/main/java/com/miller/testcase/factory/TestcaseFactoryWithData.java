package com.miller.testcase.factory;

import com.miller.testcase.utils.TestCaseGenerator;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.launcher.TestCaseRunnerLauncher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * 测试用例工厂
 *
 */
@Scenario(scenarioID = "01KEGYNHVG4R5N4MAGFBVZASDI", scenarioName = "C测试用例工厂_一键自动创建用例待参数",
        author = "zhangpei@hungrypandagroup.com",
        developmentTime = 8 * 60, maintenanceTime = 2 * 60, manualTestTime = 10)
@DisplayName("测试用例工厂")
public class TestcaseFactoryWithData {
    // 用户可自定义的子目录（如 "account/address"），默认为空字符串，则默认在 module 目录下
    public static String CUSTOM_PATH = "user/shop";
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

    public static void main(String[] args) {
        // 检查参数个数
        if (args.length < 2) {
            System.err.println("用法: java " + TestcaseFactory.class.getName() + " <测试用例名称> <cURL命令>");
            System.err.println("示例: java " + TestcaseFactory.class.getName() + " \"user create address\" \"curl -X POST ...\"");
            System.exit(1);
        }

        String testCaseName = args[0];
        String curlCommand = args[1];

        try {
            createTestCase(testCaseName, curlCommand);
            System.out.println("测试用例生成成功！");
            new TestCaseRunnerLauncher().runTestMethod(TestcaseFactory.class, "reportedData");
        } catch (IOException e) {
            System.err.println("生成测试用例失败：" + e.getMessage());
            System.exit(1);
        }
    }
    /**
     * 统一对外方法，自动创建测试用例
     */
    public static void createTestCase(String testCaseName, String curlCommand) throws IOException {
        String javaPath = JAVA_BASE_PATH + (CUSTOM_PATH.isEmpty() ? "" : "/" + CUSTOM_PATH);
        String resourcesPath = RESOURCES_BASE_PATH + (CUSTOM_PATH.isEmpty() ? "" : "/" + CUSTOM_PATH);
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
