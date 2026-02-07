package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.dashboard.complaint;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

//@Scenario(
//        scenarioID = "01JPS9P0WQ69JHTFA13TYS2WF2",
//        scenarioName = "司管后台-订单管理-实时看板-客诉单看板-骑手top30",
//        author = "chenchunxia@hungrypandagroup.com",
//        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
//@DisplayName("客诉单骑手top30报表")
public class ComplaintDriverTop30ReportTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("客诉单骑手top30报表")
    @Test
    void shouldGetComplaintDriverTop30Report() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取客诉单骑手top30报表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryDashboard/complaint/dashBoard/driverData";
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

