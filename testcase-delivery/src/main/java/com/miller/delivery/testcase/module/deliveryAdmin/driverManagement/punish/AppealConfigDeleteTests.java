package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.punish;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管后台-新增骑手禁止上线配置
 */
@Scenario(
        scenarioID = "01KJD2QSQ42JVSHM37TWVCKN4H",
        scenarioName = "删除骑手禁止上线配置",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("删除骑手禁止上线配置")
public class AppealConfigDeleteTests {

    @DisplayName("删除骑手禁止上线配置")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        AppealConfigAddNewTests PunishAppealConfigAddNewTests = new AppealConfigAddNewTests();
        String configNo = PunishAppealConfigAddNewTests.add(token);
        delete(token, configNo);
    }

    public void delete(String token, String configNo) {


        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/punishAppealContentConfig/delete";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\"configNo\":\""+configNo+"\"}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");


    }

}

