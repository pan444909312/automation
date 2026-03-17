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
        scenarioID = "01KJHDS675E5KRZ3JV2SH9NZP8",
        scenarioName = "编辑骑手禁止上线配置",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("编辑骑手禁止上线配置")
public class AppealConfigUpdateTests {

    @DisplayName("编辑骑手禁止上线配置")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        AppealConfigAddNewTests appealConfigAddNewTests = new AppealConfigAddNewTests();
        String select = appealConfigAddNewTests.select();
        if (select==null){
            AppealConfigAddNewTests PunishAppealConfigAddNewTests = new AppealConfigAddNewTests();
            String configNo = PunishAppealConfigAddNewTests.add(token);
            update(token,configNo,"成功",1);
            //case1：删除不存在的配置
            update(token, "abctest","数据不存在",10022);
            //case2：配置ID为空
            AppealConfigDeleteTests punishAppealConfigDeleteTests = new AppealConfigDeleteTests();
            punishAppealConfigDeleteTests.delete(token,configNo,"成功",1);
        }else {
            update(token,select,"成功",1);
        }




    }

    public void update(String token, String configNo,String response,Integer code) {


        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/punishAppealContentConfig/save";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\n" +
                "    \"configNo\": \""+configNo+"\",\n" +
                "    \"cityList\": [\n" +
                "        \"九江市\"\n" +
                "    ],\n" +
                "    \"canAppeal\": 0,\n" +
                "    \"controlReason\": 0,\n" +
                "    \"controlAudit\": 0,\n" +
                "    \"personRemind\": 0,\n" +
                "    \"controlContent\": \"3\",\n" +
                "    \"controlContentEn\": \"33\",\n" +
                "    \"isDefault\": 0\n" +
                "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);



        Integer codeActual = Integer.valueOf(TestCaseHelpful.extractValue(responseBody, "$.code").toString());
        assert codeActual.equals(code);
        String message = TestCaseHelpful.extractValue(responseBody, "$.message").toString();
        assert message.equals(response);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

}

