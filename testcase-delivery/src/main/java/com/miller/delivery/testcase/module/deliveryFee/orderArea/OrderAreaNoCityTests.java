package com.miller.delivery.testcase.module.deliveryFee.orderArea;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 接单商圈-无城市
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01K8W4DMRX247F1R6CPB1ZHEX7",
        scenarioName = "对外接口-接单商圈-无城市",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 60)
@DisplayName("接单商圈-无城市")
public class OrderAreaNoCityTests {

    @DisplayName("查询接单商圈（入参无城市）")
    @Test
    void shouldQueryOrderAreaNoCity() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/orderArea/gps/v2";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/orderArea/noCity/request/headers.json");
         
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/orderArea/noCity/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "33.3222000002");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "120.60000066666");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @DisplayName("查询接单商圈（入参无城市）-无经度")
    @Test
    void shouldQueryOrderAreaNoCityWithoutLat() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/orderArea/gps/v2";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/orderArea/noCity/request/headers.json");
         
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/orderArea/noCity/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "120.12253238164111");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(9999);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("GPS信息为空");
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @DisplayName("查询接单商圈（入参无城市）-无纬度")
    @Test
    void shouldQueryOrderAreaNoCityWithoutLon() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/orderArea/gps/v2";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/orderArea/noCity/request/headers.json");
         
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/orderArea/noCity/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "30.42243149124935");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(9999);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("GPS信息为空");
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

