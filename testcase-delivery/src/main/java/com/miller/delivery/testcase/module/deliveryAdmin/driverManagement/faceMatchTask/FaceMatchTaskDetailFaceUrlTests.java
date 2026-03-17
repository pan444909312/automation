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
 * 司管后台-骑手管理-人脸检测配置-查看此次采集照片
 */
@Scenario(
        scenarioID = "01KJF8WND16HPSH31G5YCTRE1M",
        scenarioName = "人脸检测记录查看此次采集照片",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("人脸检测记录查看此次采集照片")
public class FaceMatchTaskDetailFaceUrlTests {

    @DisplayName("人脸检测记录查看此次采集照片")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        list(token);
    }
    //
    public  void  list(String token) {

        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/faceMatchTask/detailFaceUrl";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\n" +
                "    \"taskNo\": \"506824717099649824\"\n" +
                "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

    }

}
