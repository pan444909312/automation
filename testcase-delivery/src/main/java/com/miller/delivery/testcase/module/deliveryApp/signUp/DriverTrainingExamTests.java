package com.miller.delivery.testcase.module.deliveryApp.signUp;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 入驻骑手获取培训考试详情
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JWG86SJ7E1K202JFFKZHBDSA",
        scenarioName = "入驻骑手获取培训考试详情",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 2)
@DisplayName("入驻骑手获取培训考试详情")
public class DriverTrainingExamTests {

    @DisplayName("获取培训考试详情")
    @Test
    void shouldGetTrainingExamDetail() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("18100334477", "AA2010aa");

        // 2) 获取培训考试资料列表
        String cultivateCode = getTrainingExamList(driverAccessToken);
        
        // 3) 获取培训考试详情
        getTrainingExamDetail(driverAccessToken, cultivateCode);
    }

    /**
     * 获取培训考试资料列表
     */
    private String getTrainingExamList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/cultivate/cultivateExamInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        
        // 提取 cultivateCode
        return TestCaseHelpful.extractValue(responseBody, "$.result.cultivateExamList[0].cultivateCode").toString();
    }

    /**
     * 获取培训考试详情
     */
    private void getTrainingExamDetail(String driverAccessToken, String cultivateCode) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/cultivate/driverDetail";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"cultivateCode\":\"%s\"}", cultivateCode);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216726");
        headers.put("latitude", "30.203548");
        headers.put("version", "5.66.0");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "HUAWEI");
        headers.put("uniquetoken", "dd9959880e28753f");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");
         
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

