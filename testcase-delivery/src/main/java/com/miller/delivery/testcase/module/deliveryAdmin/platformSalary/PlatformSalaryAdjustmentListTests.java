package com.miller.delivery.testcase.module.deliveryAdmin.platformSalary;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 平台薪资调整-列表
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KDQNKJ8T42692NSMNNW2R8YN",
        scenarioName = "平台薪资调整-列表",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("平台薪资调整-列表")
public class PlatformSalaryAdjustmentListTests {

    @DisplayName("获取所有平台薪资调整")
    @Test
    void shouldGetAllPlatformSalaryAdjustment() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        // 2) 获取所有平台薪资调整
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/platformSalary/platformSalaryAdjustmentList";
        String method = "POST";
        String body = "{\"amountType\":null,\"driverName\":null,\"mobile\":null,\"city\":null,\"areaIdList\":[],\"siteIdList\":[],\"driverBusinessType\":null,\"createStartTime\":null,\"createEndTime\":null,\"adjustStartTime\":null,\"adjustEndTime\":null,\"pageNo\":1,\"pageSize\":15}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("data").isNotNull();
    }

    @DisplayName("获取单个骑手平台薪资调整")
    @Test
    void shouldGetSingleDriverPlatformSalaryAdjustment() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        // 2) 获取单个骑手平台薪资调整
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/platformSalary/platformSalaryAdjustmentList";
        String method = "POST";
        // 注意：mobile 需要根据实际情况调整
        String body = "{\n" +
                "    \"amountType\": null,\n" +
                "    \"driverName\": null,\n" +
                "    \"mobile\": 13300010623,\n" +
                "    \"city\": null,\n" +
                "    \"areaIdList\": [],\n" +
                "    \"siteIdList\": [],\n" +
                "    \"driverBusinessType\": null,\n" +
                "    \"createStartTime\": null,\n" +
                "    \"createEndTime\": null,\n" +
                "    \"adjustStartTime\": null,\n" +
                "    \"adjustEndTime\": null,\n" +
                "    \"pageNo\": 1,\n" +
                "    \"pageSize\": 15\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("data").isNotNull();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

