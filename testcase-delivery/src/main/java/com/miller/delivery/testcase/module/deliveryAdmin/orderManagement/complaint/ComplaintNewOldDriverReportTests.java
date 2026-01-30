package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.complaint;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01JPS9NB3TW7G2KTR10R789F84",
        scenarioName = "司管后台-订单管理-实时看板-客诉单看板-新老骑手报表",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("新老骑手客诉单报表")
public class ComplaintNewOldDriverReportTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("新老骑手客诉单报表")
    @Test
    void shouldGetComplaintNewOldDriverReport() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取新老骑手客诉单报表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryDashboard/complaint/dashBoard/newDriver";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"city\":\"杭州市\",\"deliveryAreaIdList\":[],\"pageNo\":1,\"pageSize\":10,\"cityList\":[\"杭州市\"]}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }
}

