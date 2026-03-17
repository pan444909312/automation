package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.dashboard.cityOverview;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管后台-新增骑手禁止上线配置
 */
@Scenario(
        scenarioID = "01KJHG3YYCCTJW2GZFV3RFV3BN",
        scenarioName = "司管后台-城市概览-获取运力",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("司管后台-城市概览-获取运力")
public class CapacityAreaInfoTests {

    @DisplayName("司管后台-城市概览-获取运力")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        capacityAreaInfo(token);


    }

    public void capacityAreaInfo(String token) {


        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/performanceKanban/capacityAreaInfo";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        Calendar calendar = Calendar.getInstance();
        // 2. 获取当天日期
        Date today = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = sdf.format(today);


        // 5. 输出结果
        System.out.println("当天日期：" + todayStr);
        headers.put("authorization", token);
        String body = "{\n" +
                "    \"cityList\": [\n" +
                "        \"杭州市\"\n" +
                "    ],\n" +
                "    \"startDate\": \""+todayStr+"\",\n" +
                "    \"endDate\": \""+todayStr+"\",\n" +
                "    \"date\": \"\",\n" +
                "    \"areaIdList\": []\n" +
                "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);



        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");


    }

}

