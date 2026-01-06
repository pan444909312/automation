package com.miller.delivery.testcase.module.deliveryFee.customerArea;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 用户商圈-无城市
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01K8W5C4N4SY730VGF9V4GDRMK",
        scenarioName = "对外接口-用户商圈-无城市",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 60)
@DisplayName("用户商圈-无城市")
public class CustomerAreaNoCityTests {

    @DisplayName("查询用户商圈-无城市")
    @Test
    void shouldQueryCustomerAreaNoCity() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/customerArea/gps/v2";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/customerArea/noCity/request/headers.json");
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/customerArea/noCity/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "30.334326");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "120.060963");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    @DisplayName("查询用户商圈-无城市-不传精度")
    @Test
    void shouldQueryCustomerAreaNoCityWithoutLat() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/customerArea/gps/v2";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/customerArea/noCity/request/headers.json");
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/customerArea/noCity/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "120.060963");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(9999);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("GPS信息为空");
    }

    @DisplayName("查询用户商圈-无城市-不传纬度")
    @Test
    void shouldQueryCustomerAreaNoCityWithoutLon() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/customerArea/gps/v2";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/customerArea/noCity/request/headers.json");
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/customerArea/noCity/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "30.334326");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(9999);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("GPS信息为空");
    }
}

