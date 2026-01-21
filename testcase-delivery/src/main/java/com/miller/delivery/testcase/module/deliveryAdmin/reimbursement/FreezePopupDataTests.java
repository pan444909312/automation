package com.miller.delivery.testcase.module.deliveryAdmin.reimbursement;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KEBGNKRR1QH2DQP8GWV36MTS",
        scenarioName = "骑手个人薪资-冻结弹窗",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("获取冻结弹窗数据")
public class FreezePopupDataTests {

    @DisplayName("获取冻结弹窗数据")
    @Test
    void shouldGetFreezePopupData() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 冻结弹窗-骑手收入
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/reimbursement/getDriverInvoiceDataForReimbursement";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body1 = "{\r\n" +
                "    \"driverId\": 1398721886,\r\n" +
                "    \"amountType\": 2,\r\n" +
                "    \"cityList\": [],\r\n" +
                "    \"invoiceType\": 0\r\n" +
                "}";
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri, null, headers, body1);

        // 3) 断言第一个请求
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("message").isEqualTo("成功");

        // 4) 冻结弹窗-垫付
        String body2 = "{\r\n" +
                "    \"driverId\": 1398721886,\r\n" +
                "    \"amountType\": 1,\r\n" +
                "    \"cityList\": [],\r\n" +
                "    \"invoiceType\": 1\r\n" +
                "}";
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri, null, headers, body2);

        // 5) 断言第二个请求
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");
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
         
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

