package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.faceMatchTask;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管后台-骑手管理-人脸检测记录-导出人脸检测记录
 */
@Scenario(
        scenarioID = "01KJHZMZRAAERTB1VWQB1HH4FK",
        scenarioName = "导出人脸检测记录",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("导出人脸检测记录")
public class ExportTaskListTests {

    @DisplayName("导出人脸检测记录")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        list(token);
    }
    //
    public  void  list(String token) {

        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/faceMatchTask/exportTaskList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\n" +
                "    \"checkTimeStart\": \"2026-01-29\",\n" +
                "    \"checkTimeEnd\": \"2026-02-27\"\n" +
                "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

    }

}
