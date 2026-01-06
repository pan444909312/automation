package com.miller.delivery.testcase.module.deliveryFee.forbiddenArea;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 酒水-无城市
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01K8W5JFC7Y57WHVCD6H3QWFZK",
        scenarioName = "对外接口-酒水-无城市",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 60)
@DisplayName("酒水-无城市")
public class ForbiddenAreaNoCityTests {

    @DisplayName("酒水-无城市")
    @Test
    void shouldQueryForbiddenAreaNoCity() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/forbiddenArea/gps/v2";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/forbiddenArea/noCity/request/headers.json");
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/forbiddenArea/noCity/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "30.552191");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "119.974286");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    @DisplayName("酒水-无城市-不传精度")
    @Test
    void shouldQueryForbiddenAreaNoCityWithoutLon() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/forbiddenArea/gps/v2";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/forbiddenArea/noCity/request/headers.json");
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/forbiddenArea/noCity/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "30.552191");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(9999);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("GPS信息为空");
    }

    @DisplayName("酒水-无城市-不传维度")
    @Test
    void shouldQueryForbiddenAreaNoCityWithoutLat() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/forbiddenArea/gps/v2";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/forbiddenArea/noCity/request/headers.json");
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/forbiddenArea/noCity/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "333");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(9999);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("GPS信息为空");
    }
}

