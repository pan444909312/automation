package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.cultivate;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管后台-骑手列表-骑手培训内容-启用骑手培训内容
 */
@Scenario(
        scenarioID = "01KJH4KNTACP74AP70RX150AWG",
        scenarioName = "启用骑手培训内容",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("启用骑手培训内容")
public class CultivateStatusTests {

    @DisplayName("启用骑手培训内容")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        CultivateSaveTests cultivateSaveTests = new CultivateSaveTests();
        String cultivate_code = cultivateSaveTests.saveCultivate(token);
        // 启用
        list(token,cultivate_code);
        System.out.println("启用成功"+cultivate_code);
        //禁用
        CultivateDisableTests cultivateDisableTests = new CultivateDisableTests();
        cultivateDisableTests.disableCultivate(token,cultivate_code);
        System.out.println("禁用成功");
        //删除
        CultivateDeleteTests cultivateDeleteTests = new CultivateDeleteTests();
        cultivateDeleteTests.deleteCultivate(token,cultivate_code);
        System.out.println("删除成功");
    }
    //启用培训内容
    public  void  list(String token,String cultivate_code) {

        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/cultivate/cultivateStatus";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = "{\n" +
                "    \"cultivateCode\": \""+cultivate_code+"\",\n" +
                "    \"isEnable\": 1,\n" +
                "    \"applyLanguageType\": 0\n" +
                "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

    }

}
