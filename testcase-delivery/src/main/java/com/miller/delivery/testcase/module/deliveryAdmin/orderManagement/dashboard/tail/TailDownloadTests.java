package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.dashboard.tail;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管后台-新增骑手禁止上线配置
 */
@Scenario(
        scenarioID = "01KJHHGMXJBZWY90C97NQ2QR0R",
        scenarioName = "司管后台-尾单率-尾单定责导出",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("司管后台-尾单率-尾单定责导出")
public class TailDownloadTests {

    @DisplayName("司管后台-尾单率-尾单定责导出")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        capacityAreaInfo(token);


    }

    public void capacityAreaInfo(String token) {


        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/tail/download";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();

        headers.put("authorization", token);
        String body = "{\n" +
                "    \"cityList\": [\n" +
                "        \"杭州市\"\n" +
                "    ],\n" +
                "    \"deliveryAreaIdList\": [],\n" +
                "    \"runTypeList\": [],\n" +
                "    \"pageNo\": 1,\n" +
                "    \"pageSize\": 10,\n" +
                "    \"excludeDuty\": 0,\n" +
                "    \"excludeWeather\": 0\n" +
                "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);



        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");


    }

}

