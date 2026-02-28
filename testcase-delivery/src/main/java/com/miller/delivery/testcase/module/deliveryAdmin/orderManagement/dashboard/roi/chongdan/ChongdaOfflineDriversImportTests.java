package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.dashboard.roi.chongdan;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
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
        scenarioID = "01KJHNMHJ44S3652Y11FYGWR43",
        scenarioName = "司管后台-roi-冲单-活动群组下线司机数-导出",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("司管后台-roi-冲单-活动群组下线司机数-导出")
public class ChongdaOfflineDriversImportTests {

    @DisplayName("司管后台-roi-冲单-活动群组下线司机数-导出")
    @Test
    void shouldAddDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        capacityAreaInfo(token);


    }

    public void capacityAreaInfo(String token) {


        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryDashboard/activity/roi/offlineDriver/export";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        //数据库中查询
        Map<String, Object> roiResult = PandaTestDBHelpful.executeSelectOneSql(
                String.format("select * from panda_delivery_dashboard_test.hp_delivery_activity_roi where activity_type=2 and city='杭州市' order by id desc limit 1"));
        String ROIid = String.valueOf(roiResult.get("id"));
        headers.put("authorization", token);
        String body = "{\"id\":"+ROIid+",\"activityType\":2}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);



        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");


    }

}

