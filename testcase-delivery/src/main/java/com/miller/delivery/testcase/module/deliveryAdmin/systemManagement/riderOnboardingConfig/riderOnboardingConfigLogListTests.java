package com.miller.delivery.testcase.module.deliveryAdmin.systemManagement.riderOnboardingConfig;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 骑手入驻配置日志列表
 *
 * @author 江彪
 * @version 2.0
 * @since 2026/02/27
 */
@Scenario(
        scenarioID = "01KJFK293HJESEWKHBNDAZ07P8",
        scenarioName = "骑手入驻配置日志列表",
        author = "jiangbiao@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("骑手入驻配置日志-列表")
public class riderOnboardingConfigLogListTests {

    @DisplayName("骑手入驻配置日志-列表-默认搜索")
    @Test
    void shouldGetRiderOnboardingConfigLogListDefaultSearch() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取推送管理列表（默认搜索）
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driver/defaultConfig/getDefaultConfigLogList";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"pageNo\":1,\"pageSize\":10}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        
        // 验证响应中包含"全城"
        String responseText = responseBody.toString();
        assert responseText.contains("启用") : "响应中未包含全城";
    }


    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

