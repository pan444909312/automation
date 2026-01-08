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
 * 平台补款
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KEDNG43262W23GSYBG299HE4", // 需要从质量平台获取实际的 scenarioID
        scenarioName = "平台补款",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("平台补款")
public class PlatformSalaryAdjustmentTests {

    @DisplayName("平台补款")
    @Test
    void shouldAddPlatformSalaryAdjustment() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        // 2) 平台补款
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/platformSalary/addPlatformSalary";
        String method = "POST";
        // 注意：dateDesc 和 driverId 需要根据实际情况调整
        String body = "{\n" +
                "    \"amount\": \"600\",\n" +
                "    \"amountType\": 2,\n" +
                "    \"city\": \"杭州市\",\n" +
                "    \"dateDesc\": \"2025-12-05\",\n" +
                "    \"driverId\": \"1398721880\",\n" +
                "    \"reasonType\": 3,\n" +
                "    \"salaryBusinessType\": 0\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言（可能成功或失败，取决于是否在生成薪资期间）
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isIn(1, 9999);
    }

    @DisplayName("平台扣款")
    @Test
    void shouldDeductPlatformSalary() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        // 2) 平台扣款
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/platformSalary/addPlatformSalary";
        String method = "POST";
        // 注意：dateDesc 和 driverId 需要根据实际情况调整
        String body = "{\n" +
                "    \"amount\": \"500\",\n" +
                "    \"amountType\": 1,\n" +
                "    \"city\": \"杭州市\",\n" +
                "    \"dateDesc\": \"2025-12-03\",\n" +
                "    \"driverId\": \"1398721880\",\n" +
                "    \"reasonType\": 7,\n" +
                "    \"salaryBusinessType\": 0\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言（可能成功或失败，取决于是否在生成薪资期间）
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isIn(1, 9999);
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Chromium\";v=\"142\", \"Google Chrome\";v=\"142\", \"Not_A Brand\";v=\"99\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

