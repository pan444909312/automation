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
 * 司管后台-基配调整配置
 */
@Scenario(
        scenarioID = "01KGS6TZHER7T5QW8D9HT5K2PH",
        scenarioName = "编辑基配调整配置",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("编辑基配调整配置")
public class BaseUpdateTests {

    private static final Long DRIVER_ID = 1398714012L; // 假设一个driverId

    @DisplayName("编辑基配调整配置")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        BaseAddTests subsidyAddTests = new BaseAddTests();
        String id = subsidyAddTests.add(token);
        modify(token,id);
        BaseDeleteTests subsidyDeleteTests = new BaseDeleteTests();
        subsidyDeleteTests.delete(token,id);

    }

    public void modify(String token,String id) {



        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/basePriceAdjustConfig/save";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\"configName\":\"基配调整杭州\",\"ruleList\":[{\"tipStart\":0.5,\"tipEnd\":2,\"reduceAmount\":1}],\"cityList\":[\"杭州市\"],\"configNo\":\""+id+"\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);


        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");


    }

}