package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.blacklist;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * blacklist thirdList
 *
 * @author chenchunxia
 * @version 2.0
 * @since 2026/01/28 21:09:50
 */
@Scenario(
        scenarioID = "01KGHR0EM1SSVSXY5RM2AAKGGB", // 自动生成，不要修改
        scenarioName = "三方拉黑-解除",
        author = "chenchunxia@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("解除三方拉黑")
public class BlacklistThirdRemoveTests {

    // 1) 司管登录获取 token



    @DisplayName("解除三方拉黑")
    @Test
    void shouldSuccess() {
        BlacklistThirdAddTests addTests = new BlacklistThirdAddTests();
        String recordNo = addTests.add();
        remove(recordNo);

    }

    public void remove(String recordNo){

        // 1) 司管登录获取 token
        String token = erpLogin();


        // 2) 新增拉黑关系失败
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/blacklist/thirdRemove";
        String body = String.format("{\"recordNoList\":[\"%s\"]}", recordNo);

        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言获取成功
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");


    }
    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");

        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
} 