package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.punish;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;
import static org.jsoup.select.Selector.select;

/**
 * 司管后台-新增骑手禁止上线配置
 */
@Scenario(
        scenarioID = "01KJHF000384ZTQPGQ1P2QA494",
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
        AppealConfigAddNewTests appealConfigAddNewTests = new AppealConfigAddNewTests();
        String select = appealConfigAddNewTests.select();
        if (select!=null){
            AppealConfigDeleteTests punishAppealConfigDeleteTests = new AppealConfigDeleteTests();
            punishAppealConfigDeleteTests.delete(token,select,"成功",1);
        }
        AppealConfigAddNewTests PunishAppealConfigAddNewTests = new AppealConfigAddNewTests();
        String configNo = PunishAppealConfigAddNewTests.add(token);
        //case1：删除不存在的配置
        delete(token, "abctest","数据不存在",10022);
        //case2：配置ID为空
        delete(token, "","Parameter error",4000);
        //case3：正常删除
        delete(token, configNo,"成功",1);

    }

    public void delete(String token, String configNo,String response,Integer code) {


        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/punishAppealContentConfig/delete";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\"configNo\":\""+configNo+"\"}";

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

