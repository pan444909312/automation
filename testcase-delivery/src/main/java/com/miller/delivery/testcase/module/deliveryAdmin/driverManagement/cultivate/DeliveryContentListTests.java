package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.cultivate;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-培训内容列表
 */
@Scenario(
        scenarioID = "01KDSPPY7G6WJEYBW5H2Z5JXDD",
        scenarioName = "骑手列表-培训内容列表",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("配送内容列表")
public class DeliveryContentListTests {

    @DisplayName("培训内容列表")
    @Test
    void shouldGetCultivateList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取培训内容列表
        getCultivateList(token);
    }

    private void getCultivateList(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/cultivate/cultivatePage";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"pageNo\":1,\"pageSize\":10,\"cityNameList\":[],\"cultivateName\":\"\",\"isEnable\":\"\",\"applyLanguageType\":\"\",\"vehicleTypeList\":[]}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        // 可以提取cultivateCode用于后续操作: TestCaseHelpful.extractValue(responseBody, "$.data.list.[0].cultivateCode")
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

