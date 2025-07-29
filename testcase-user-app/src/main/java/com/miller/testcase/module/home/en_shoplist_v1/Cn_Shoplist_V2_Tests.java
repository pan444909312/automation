package com.miller.testcase.module.home.en_shoplist_v1;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Arrays;

import static com.miller.testcase.utils.TestCaseHelpful.assertThat;

/**
 * cn shoplist v2
 *
 * @author yancancan
 * @version 2.0
 * @since 2025/07/29 18:21:48
 */
@Scenario(
        scenarioID = "01K1AQ3FN2XKGRW8GCXBMFW4FJ", // 自动生成，不要修改
        scenarioName = "cn shoplist v2 demo",
        author = "yancancan@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("cn shoplist v2")
public class Cn_Shoplist_V2_Tests {

    @BeforeAll
    static void beforeAll(){
        // 所有 @Test 方法执行之前会执行  @BeforeAll 注解的方法, 这里的代码当前测试类期间只会执行一次
        // 你可以在这里执行前置的操作，比如: SQL 初始化用例的前置条件
    }
    @AfterAll
    static void afterAll(){
        // 所有 @Test 方法执行之后会执行  @@AfterAll 注解的方法, 这里的代码当前测试类期间只会执行一次
        // 你可以在这里执行后置的操作，比如: 销毁测试数据、还原数据库、清理环境等
    }

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        var responseShopBody = TestCaseHelpful.getShopVOByShopId("288825673", "17700000077", "123456");
        
        // 检查响应体不为空
        assertThat(responseShopBody).isNotNull();
        // 检查响应体不为空
        assertThat(responseShopBody).isNull();
        // 获取并校验 deliveryAndStatus
        Integer deliveryAndStatus = responseShopBody.getInteger("deliveryAndStatus");
        Assertions.assertNotNull(deliveryAndStatus, "deliveryAndStatus 不应为空");
        assertThat(deliveryAndStatus).isEqualTo(4);
    }
} 