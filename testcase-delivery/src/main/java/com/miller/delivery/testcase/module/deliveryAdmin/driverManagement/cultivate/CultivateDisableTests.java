package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.cultivate;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-禁用培训内容
 */
@Scenario(
        scenarioID = "01KDSQTRWHBEFXC0B9J0S8ER96",
        scenarioName = "骑手列表-禁用培训内容",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("禁用培训内容")
public class CultivateDisableTests {

    @DisplayName("禁用培训内容")
    @Test
    void shouldDisableCultivate() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取培训内容列表，提取 cultivateCode
        String cultivateCode = getCultivateCode(token);

        // 3) 禁用培训内容
        disableCultivate(token, cultivateCode);
    }

    private String getCultivateCode(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/cultivate/cultivatePage";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"pageNo\":1,\"pageSize\":10,\"cityNameList\":[],\"cultivateName\":\"\",\"isEnable\":\"\",\"applyLanguageType\":\"\",\"vehicleTypeList\":[]}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        Object cultivateCodeObj = TestCaseHelpful.extractValue(responseBody, "$.data.list.[0].cultivateCode");
        return cultivateCodeObj != null ? cultivateCodeObj.toString() : null;
    }

    public void disableCultivate(String token, String cultivateCode) {
        if (cultivateCode == null || cultivateCode.isEmpty()) {
            throw new RuntimeException("无法获取cultivateCode，无法执行禁用操作");
        }
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/cultivate/cultivateStatus";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"cultivateCode\":\"%s\",\"isEnable\":0,\"applyLanguageType\":0}", cultivateCode);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

