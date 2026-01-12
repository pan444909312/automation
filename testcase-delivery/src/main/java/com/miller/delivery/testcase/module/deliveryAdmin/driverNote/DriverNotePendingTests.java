package com.miller.delivery.testcase.module.deliveryAdmin.driverNote;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-骑手笔记列表-待审核
 */
@Scenario(
        scenarioID = "01KDSZR7YE5RYS5W55BRYG72T9",
        scenarioName = "骑手列表-骑手笔记列表-待审核tab",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("骑手笔记列表-待审核")
public class DriverNotePendingTests {

    @DisplayName("骑手笔记列表-待审核")
    @Test
    void shouldGetPendingDriverNoteList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取骑手笔记列表-待审核
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverCertificate/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"cityList\":[],\"dataKeyList\":[],\"pageNo\":1,\"pageSize\":10,\"dataStatus\":0}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言获取成功
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
        headers.put("priority", "u=1, i");

        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

