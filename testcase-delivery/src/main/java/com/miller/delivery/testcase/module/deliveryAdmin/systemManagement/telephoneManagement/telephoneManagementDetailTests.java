package com.miller.delivery.testcase.module.deliveryAdmin.systemManagement.telephoneManagement;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 电话管理-列表
 * @author 江彪
 * @version 2.0
 * @since 2026/03/30
 */
@Scenario(
        scenarioID = "01KMYP758ZF6QA089R47EQ77PN",
        scenarioName = "电话管理-列表-详情",
        author = "jiangbiao@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("电话管理-列表-详情")
public class telephoneManagementDetailTests {

    @DisplayName("电话管理-列表-详情")
    @Test
    void telephoneManagementDetailDefaultSearch() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取杭州市电话管理详情
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/city/contact/detail";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"cityName\":\"杭州市\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        
        // 4）验证响应中包含"全城"
        String responseText = responseBody.toString();
        assert responseText.contains("杭州市") : "响应中未包含杭州市";
    }


    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

