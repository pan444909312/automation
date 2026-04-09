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

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KNS5MBT0AK2BN9SYVTJCAWWE",
        scenarioName = "司管后台-骑手收入定价管理-区域加价-停用活动",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("停用活动")
public class StopRewardTests {

    @DisplayName("停用活动")
    @Test
    void shouldGetOrderDataList() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        String rewardSn = addReward(token);


        // 3) 获取订单数据列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/reward/stop";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = String.format("{\"rewardSn\":\""+rewardSn+"\",\"openType\":0,\"autoOpenTime\":\"\"}");
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }


    public static String addReward(String token){

        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String activityName = "自动化创建加价活动" + todayDate;

        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/reward/addSubmitRewardContinue";
        String body = String.format("{\"driverScope\":0,\"useFor\":5,\"scene\":3,\"target\":2,\"targetValue\":1,\"mapShow\":1,\"customerAreaIdList\":[],\"checkedGroupIdList\":[],\"excludeGroupIdList\":[],\"excludeGroupList\":[],\"checkedGroupList\":[],\"rewardTypeDesc\":\"\",\"tobaccoAlcoholType\":0,\"cityId\":1,\"city\":\"杭州市\",\"areaIdList\":[51],\"driverType\":0,\"driverRunType\":null,\"name\":\"%s\",\"rewardType\":2,\"rewardMethod\":0,\"diffDistance\":0,\"distanceRuleList\":[{}],\"rule\":1,\"startDate\":\"%s\",\"endDate\":\"%s\",\"startTime\":\"00:07:07\",\"endTime\":\"23:07:07\",\"budget\":111,\"rewardReason\":\"自动化创建%s\",\"pushMsg\":null,\"deliveryOrderType\":0,\"orderTargetType\":0,\"tobaccoType\":0,\"shopDataType\":0,\"shopGroupList\":[],\"shopGroupIdList\":[]}",
                activityName, todayDate, todayDate, todayDate);
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // 数据库校验（按 apifox：查询是否已新增）
        Map<String, Object> result = PandaTestDBHelpful.executeSelectOneSql(
                String.format("select * from panda_test.hp_delivery_reward where `name`='%s' order by id desc limit 1", activityName));
        Object rewardSn = result.get("reward_sn");
        return rewardSn.toString();
    }

}

