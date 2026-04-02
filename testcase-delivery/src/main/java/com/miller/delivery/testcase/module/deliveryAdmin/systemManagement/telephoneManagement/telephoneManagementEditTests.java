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
        scenarioID = "01KMYP74GXMYACP2H7C2W1S9K9",
        scenarioName = "电话管理-列表-编辑",
        author = "jiangbiao@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("电话管理-列表-编辑")
public class telephoneManagementEditTests {

    @DisplayName("电话管理-列表-编辑")
    @Test
    void telephoneManagementListDefaultSearch() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 编辑杭州市电话管理
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/city/contact/edit";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"cityName\":\"杭州市\",\"dispatchTelephone\":\"13777842155\",\"deliveryTelephone\":\"13777842155\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }


    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

