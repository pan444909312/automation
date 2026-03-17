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
 * 司管后台-骑手管理-人脸检测配置-新增人脸检测配置-新增失败
 */
@Scenario(
        scenarioID = "01KJHQP9D72BFB94YAZWNBFWHA",
        scenarioName = "新增人脸检测配置-提示城市已配置",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("新增人脸检测配置-提示城市已配置")
public class FaceMatchConfigAddTests {

    @DisplayName("新增人脸检测配置-提示城市已配置")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Add(token);
    }
    //
    public  void  Add(String token) {

        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/faceMatchConfig/add";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\n" +
                "    \"cityName\": \"杭州市\",\n" +
                "    \"frequency\": 0,\n" +
                "    \"isLive\": 1\n" +
                "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(9999);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("该城市已存在配置");

    }

}
