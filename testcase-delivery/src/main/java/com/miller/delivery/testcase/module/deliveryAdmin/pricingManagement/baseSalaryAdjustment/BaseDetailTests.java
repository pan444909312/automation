package com.miller.delivery.testcase.module.deliveryAdmin.pricingManagement.baseSalaryAdjustment;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管后台-补贴调整配置
 */
@Scenario(
        scenarioID = "01KGS6TY5M4XHJD9T0CXHCT64H",
        scenarioName = "查看基配调整配置",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("查看基配调整配置")
public class BaseDetailTests {

    private static final Long DRIVER_ID = 1398714012L; // 假设一个driverId

    @DisplayName("查看基配调整配置")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        BaseAddTests subsidyAddTests = new BaseAddTests();
        String id = subsidyAddTests.add(token);
        detail(token,id);
        BaseDeleteTests subsidyDeleteTests = new BaseDeleteTests();
        subsidyDeleteTests.delete(token,id);

    }

    public void detail(String token,String id) {



        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/basePriceAdjustConfig/detail";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\"configNo\":\""+id+"\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);


        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");


    }

}