package com.miller.delivery.testcase.module.deliveryFee.orderArea;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 接单商圈-有城市
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01K8W4WDVGMQTD9R6R96MD4MCT",
        scenarioName = "对外接口-接单商圈-有城市",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 60)
@DisplayName("接单商圈-有城市")
public class OrderAreaWithCityTests {

    @DisplayName("通过经纬度查询接单商圈-有城市")
    @Test
    void shouldQueryOrderAreaWithCity() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/orderArea/gps";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/orderArea/withCity/request/headers.json");

        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/orderArea/withCity/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.city", "杭州市");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "30.210938");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "120.222319");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    @DisplayName("通过经纬度查询接单商圈-有城市-不传城市")
    @Test
    void shouldQueryOrderAreaWithCityWithoutCity() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/orderArea/gps";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/orderArea/withCity/request/headers.json");

        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/orderArea/withCity/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.city", "");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "30.210938");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "120.222319");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(9999);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("城市信息为空");
    }

    @DisplayName("通过经纬度查询接单商圈-有城市-不传精度")
    @Test
    void shouldQueryOrderAreaWithCityWithoutLat() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/orderArea/gps";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/orderArea/withCity/request/headers.json");

        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/orderArea/withCity/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.city", "杭州市");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "120.222319");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(9999);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("GPS信息为空");
    }

    @DisplayName("通过经纬度查询接单商圈-有城市-不传纬度")
    @Test
    void shouldQueryOrderAreaWithCityWithoutLon() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/orderArea/gps";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/orderArea/withCity/request/headers.json");

        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/orderArea/withCity/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.city", "杭州市");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "30.125");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(9999);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("GPS信息为空");
    }
}

