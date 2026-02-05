package com.miller.delivery.testcase.module.deliveryApp.emergencyContact;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手app-新增紧急联系人
 *
 * Apifox: docs/d-apifox/新增紧急联系人.apifox-cli.json
 */
@Scenario(
        scenarioID = "01KGPZ3E552JA10PC9EYD6JZJM",
        scenarioName = "骑手app-删除紧急联系人",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("删除紧急联系人")
public class DriverEmergencyContactDeleteTests {

    @DisplayName("删除紧急联系人")
    @Test
    void shouldAddEmergencyContact() {
        DriverEmergencyContactAddTests contactAddTests = new DriverEmergencyContactAddTests();

        delete(contactAddTests.add());
    }
    public void delete(int id){
        Map<String, String> driverLoginInfo = TestCaseHelpful.deliveryLoginReturndriverId("13300010676", "Test1234");
        String driverAccessToken = driverLoginInfo.get("accessToken");
        Long driverId = Long.valueOf(driverLoginInfo.get("userId"));


        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/deleteDriverEmergencyContact";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);


        String body = String.format("{\"contactId\":%d}", id);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);


    }

    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.21674955924935");
        headers.put("latitude", "30.20344076263413");
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
        headers.put("enableSign", "false");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

