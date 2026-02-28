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
 * 司管后台-骑手管理-人脸检测记录-启用禁用人脸检测配置
 */
@Scenario(
        scenarioID = "01KJF44C29GQVZ8D3Q7KZ4QEEE",
        scenarioName = "启用禁用人脸检测配置",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("启用禁用人脸检测配置")
public class ModifyStatusTests {

    @DisplayName("启用禁用人脸检测配置")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        // 获取杭州市的配置 configNo
        FaceMatchConfigListTests faceMatchConfigListTests = new FaceMatchConfigListTests();
        String configNo = faceMatchConfigListTests.list(token);
        //打印configNo
        System.out.println("打印configNo+"+configNo);
        //先启用人脸配置-杭州
        Active(token,configNo);
        System.out.println("启用成功");
        //禁用指定的人脸配置-杭州
        Disabledlist(token,configNo);
        System.out.println("禁用成功");
    }
    //禁用指定的人脸配置
    public  void  Disabledlist(String token,String configNo) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/faceMatchConfig/modifyStatus";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\n" +
                "    \"configNo\": \""+configNo+"\",\n" +
                "    \"enable\": 0\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

    }
    //启用指定的人脸配置
    public  void  Active (String token,String configNo) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/faceMatchConfig/modifyStatus";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\n" +
                "    \"configNo\": \""+configNo+"\",\n" +
                "    \"enable\": 1\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

    }
}
