package com.miller.delivery.testcase.module.deliveryAdmin.systemManagement.agreementList;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 协议管理-列表
 *
 * @author 江彪
 * @version 2.0
 * @since 2026/03/20
 */
@Scenario(
        scenarioID = "01KM4KZHQ2Q95HQADT3K4KR0BZ",
        scenarioName = "协议管理-骑手同意协议列表",
        author = "jiangbiao@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("协议管理-骑手同意协议列表")
public class driverAgreementListTests {

    @DisplayName("协议管理-骑手同意协议列表")
    @Test
    void shouldGetAgreementListTestsDefaultSearch() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取骑手同意协议列表信息（默认搜索）
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/system/agreement/driverAgreementList";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"pageNo\":1,\"pageSize\":10,\"driverId\":\"\",\"driverName\":\"\",\"logId\":1125}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 4) 获取骑手同意协议列表信息，翻页
        String uri2 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/system/agreement/driverAgreementList";
        String body2 = "{\"pageNo\":2,\"pageSize\":10,\"driverId\":\"\",\"driverName\":\"\",\"logId\":1125}";
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri2, null, headers, body2);

        // 5) 断言
        TestCaseHelpful.assertThatJson(responseBody2)
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

