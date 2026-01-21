package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人中心-协议
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01JPP6X3P0PE0C7QEQX6BNY2Q4",
        scenarioName = "骑手app-个人中心-协议",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("个人中心-协议")
public class PersonalCenterAgreementTests {

    @DisplayName("查看协议并同意")
    @Test
    void shouldCheckAndAgreeAgreement() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 查看是否有未同意的协议列表
        String agreementNo = checkAgreementStatus(driverAccessToken);

        // 3) 如果有协议，则同意协议
        if (agreementNo != null && !agreementNo.isEmpty()) {
            agreeAgreement(driverAccessToken, agreementNo);
        }

        // 4) 协议管理agreement/authList
        getAgreementAuthList(driverAccessToken);
    }

    /**
     * 查看是否有未同意的协议列表
     */
    private String checkAgreementStatus(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/agreement/agreementStatus";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"operation\":0}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);

        // 提取协议号（如果存在）
        try {
            Object agreementNoObj = TestCaseHelpful.extractValue(responseBody, "$.result.agreementNo");
            return agreementNoObj != null ? agreementNoObj.toString() : null;
        } catch (Exception e) {
            // 如果没有协议号，返回null
            return null;
        }
    }

    /**
     * 同意协议
     */
    private void agreeAgreement(String driverAccessToken, String agreementNo) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/agreement/agreeAgreement";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"agreementNo\":\"%s\"}", agreementNo);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
    }

    /**
     * 协议管理agreement/authList
     */
    private void getAgreementAuthList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/agreement/authList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"country\":\"中国\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.21683");
        headers.put("latitude", "30.203575");
        headers.put("version", "5.56.1");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "HUAWEI");
        headers.put("uniquetoken", "dd9959880e28753f");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

