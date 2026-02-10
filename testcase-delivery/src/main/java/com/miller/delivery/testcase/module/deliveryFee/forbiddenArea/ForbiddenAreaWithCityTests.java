package com.miller.delivery.testcase.module.deliveryFee.forbiddenArea;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 酒水-有城市
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01K8W5KYBWSN837YMCWZFHHGXP",
        scenarioName = "对外接口-酒水-有城市",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 60)
@DisplayName("酒水-有城市")
public class ForbiddenAreaWithCityTests {

    @DisplayName("酒水-有城市")
    @Test
    void shouldQueryForbiddenAreaWithCity() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/forbiddenArea/gps";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/forbiddenArea/withCity/request/headers.json");
         
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/forbiddenArea/withCity/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.city", "杭州市");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "30.552191");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "119.974286");

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

    @DisplayName("酒水-有城市-不传城市")
    @Test
    void shouldQueryForbiddenAreaWithoutCity() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/forbiddenArea/gps";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/forbiddenArea/withCity/request/headers.json");
         
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/forbiddenArea/withCity/request/body-nocity.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lat", "30.552191");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.lon", "119.974286");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(400);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("Parameter error");
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }



}

