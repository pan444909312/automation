package com.miller.delivery.testcase.module.deliveryAdmin.pricingManagement.reward;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.miller.delivery.testcase.module.deliveryAdmin.pricingManagement.reward.AddRewardDraftTests.addRewardDraft;
import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KNS750WQ9VA8435TZXPQEBG3",
        scenarioName = "司管后台-骑手收入定价管理-区域加价-删除草稿",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("删除草稿")
public class DeleteRewardDraftTests {

    @DisplayName("删除草稿")
    @Test
    void shouldGetOrderDataList() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        String rewardDraft = addRewardDraft(token);
        deleteRewardDraft(token,rewardDraft);


    }

    public  void deleteRewardDraft(String token,String rewardDraft){


        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/reward/del";
        String body = String.format("{\"rewardSn\":\""+rewardDraft+"\"}");
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");


    }


}

