package com.miller.delivery.testcase.module.dispatch.newDispatch.other;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 新调度-地图-未取餐商家
 */
@Scenario(
        scenarioID = "01KK15JMC3RE8QSA6C1WJX470V",
        scenarioName = "新调度-地图-未取餐商家",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("新调度-地图-未取餐商家")
public class UnTakeList {
    @DisplayName("新调度-地图-未取餐商家")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        System.out.println("获取到的 token: " + token);
        System.out.println("token 长度: " + (token != null ? token.length() : "null"));
        unDispatchList(token);
    }
    //
    public  void  unDispatchList(String token) {

        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/deliveryOrder/unTakeList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", token);
        String body = "{\n" +
                "    \"cityName\": \"杭州市\",\n" +
                "    \"cityLocation\": {\n" +
                "        \"latitude\": \"30.2592444615\",\n" +
                "        \"longitude\": \"120.219375416\"\n" +
                "    },\n" +
                "    \"startDate\": \"2026-01-21 00:00:00\",\n" +
                "    \"endDate\": \"2026-01-24 23:59:59\",\n" +
                "    \"orderBusinessTypeList\": [\n" +
                "        0\n" +
                "    ],\n" +
                "    \"orderTypeList\": [\n" +
                "        0,\n" +
                "        3,\n" +
                "        2,\n" +
                "        1\n" +
                "    ],\n" +
                "    \"timeType\": 0\n" +
                "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

    }

}
