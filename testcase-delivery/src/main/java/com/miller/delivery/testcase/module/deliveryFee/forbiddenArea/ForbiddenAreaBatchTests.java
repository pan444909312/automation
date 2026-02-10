package com.miller.delivery.testcase.module.deliveryFee.forbiddenArea;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 酒水-无城市-批量
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01KGHQ9C5ZTSKXEAP8JGXJMEMR",
        scenarioName = "对外接口-酒水-批量",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 60)
@DisplayName("酒水-无城市-批量")
public class ForbiddenAreaBatchTests {

    @DisplayName("酒水-无城市-批量")
    @Test
    void shouldQueryForbiddenAreaBatch() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/forbiddenArea/gps/batch";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/forbiddenArea/batch/request/headers.json");
         
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/forbiddenArea/batch/request/batch_body.json");

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

    @DisplayName("酒水-无城市-批量-不传精度")
    @Test
    void shouldQueryForbiddenAreaBatchWithoutLon() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/forbiddenArea/gps/batch";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/forbiddenArea/batch/request/headers.json");
         
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/forbiddenArea/batch/request/batch_no_lon_body.json");

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

    @DisplayName("酒水-无城市-批量-不传维度")
    @Test
    void shouldQueryForbiddenAreaBatchWithoutLat() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/forbiddenArea/gps/batch";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/forbiddenArea/batch/request/headers.json");
         
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/forbiddenArea/batch/request/batch_no_lat_body.json");

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

