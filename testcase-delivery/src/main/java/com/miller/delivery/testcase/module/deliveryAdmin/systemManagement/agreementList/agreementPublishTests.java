package com.miller.delivery.testcase.module.deliveryAdmin.systemManagement.agreementList;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 协议管理-列表
 *
 * @author 江彪
 * @version 2.0
 * @since 2026/03/20
 */
@Scenario(
        scenarioID = "01KM4M0SSRYB58SAQ5PDB4CZFM",
        scenarioName = "协议管理-协议开启-协议关闭 ",
        author = "jiangbiao@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("协议管理-协议开启-协议关闭")
public class agreementPublishTests {

    @DisplayName("协议管理-协议开启-协议关闭")
    @Test
    void shouldGetAgreementListTestsDefaultSearch() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 协议管理-各版本协议骑手操作记录列表（默认搜索）
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/system/agreement/publishAgreement";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"agreementNo\":\"LAW428912229786533792\",\"publishStatus\":1}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 4) 协议管理-各版本协议骑手操作记录列表，翻页
        String uri2 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/system/agreement/publishAgreement";
        String body2 = "{\"agreementNo\":\"LAW428912229786533792\",\"publishStatus\":0}";
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri2, null, headers, body2);

        // 5) 断言
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }


    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

