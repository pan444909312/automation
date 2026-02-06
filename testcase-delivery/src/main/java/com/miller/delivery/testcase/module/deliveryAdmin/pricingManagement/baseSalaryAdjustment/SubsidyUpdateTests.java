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
        scenarioID = "01KGS55B8PGA3KCTGPKHS3HJM2",
        scenarioName = "编辑补贴调整配置",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("编辑补贴调整配置")
public class SubsidyUpdateTests {

    private static final Long DRIVER_ID = 1398714012L; // 假设一个driverId

    @DisplayName("编辑补贴调整配置")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        SubsidyAddTests subsidyAddTests = new SubsidyAddTests();
        String id = subsidyAddTests.add(token);
        modify(token,id);
        SubsidyDeleteTests subsidyDeleteTests = new SubsidyDeleteTests();
        subsidyDeleteTests.delete(token,id);

    }

    public void modify(String token,String id) {



        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/subsidyAdjust/modifyConfig";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\"activityScope\":0,\"adjustmentName\":\"自动化配置\",\"cityList\":[\"郑州市\"],\"ruleList\":[{\"tipStart\":1,\"tipEnd\":2,\"rate\":1}],\"adjustmentNo\":\""+id+"\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);


        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");


    }

}