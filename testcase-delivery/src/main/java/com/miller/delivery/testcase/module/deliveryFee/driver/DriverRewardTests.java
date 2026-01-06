package com.miller.delivery.testcase.module.deliveryFee.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 打赏相关接口
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01K8W5VF3MR0E3297NZWPGNWYG",
        scenarioName = "对外接口-打赏",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("打赏相关接口")
public class DriverRewardTests {

    @DisplayName("打赏1")
    @Test
    void shouldGetDriverRewardByOrder() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/driver/byOrder";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/driver/reward/request/headers.json");
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/driver/reward/request/reward1_body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.orderSn", "525448405336971574311");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    @DisplayName("打赏2")
    @Test
    void shouldGetDriverRewardBaseInfo() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/driver/byOrder/baseInfo";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/driver/reward/request/headers.json");
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/driver/reward/request/reward2_body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.orderSn", "525448405336971574311");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    @DisplayName("打赏3-批量")
    @Test
    void shouldGetDriverRewardBaseInfoBatch() {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryFee/driver/byOrder/baseInfo/batch";
        String method = "POST";
        
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/deliveryFee/driver/reward/request/headers.json");
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/deliveryFee/driver/reward/request/reward3_body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.orderSnList[0]", "");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.orderSnList[1]", "573146189376070138886");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }
}

