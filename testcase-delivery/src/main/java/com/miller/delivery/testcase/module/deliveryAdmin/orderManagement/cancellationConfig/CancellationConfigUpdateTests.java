package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.cancellationConfig;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Scenario(
        scenarioID = "01JPSC0BSQY7TXFK7PRS8ZD938",
        scenarioName = "司管后台-订单管理-撤单配置-修改撤单配置",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("修改撤单配置")
public class CancellationConfigUpdateTests {

    private static final Long CONFIG_ID = 151L; // 假设一个configId
    private static final Long DRIVER_GROUP_ID = 1288L; // 假设一个driverGroupId

    @DisplayName("编辑骑手群组提交")
    @Test
    void shouldUpdateCancellationConfig() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 编辑骑手群组提交
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/cancellationConfig/cancellationDriverGroup";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"configId\":%d,\"driverGroupIdList\":[%d,1490,1595,1633,1634,1635,1636,1637,1638,1639,1641,1642,1643,1664]}", CONFIG_ID, DRIVER_GROUP_ID);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("h5xxx", "1754374901864_request");
        headers.put("priority", "u=1, i");

        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

