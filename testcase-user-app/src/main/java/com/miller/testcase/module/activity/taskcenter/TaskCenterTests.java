package com.miller.testcase.module.activity.taskcenter;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@Scenario(
        scenarioID = "01JWSS1KB2H2X5WV4YW6GE43GA",
        scenarioName = "任务中心主页面H5",
        author = "yancancan@hungrypandagroup.com",
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("任务中心主页面H5")
public class TaskCenterTests {
    // 接口请求的 path
    String uri = TestcaseConfig.HOST_APP + "/api/app/user/task/center";
    // 请求方式
    String method = "POST";
    // 请求头
    String headers = "module/activity/taskcenter/request/headers_topic.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String body = "module/activity/taskcenter/request/success.json";
    // 断言
    String assert2 = "module/activity/taskcenter/response/assert_some_fields.json";
    @DisplayName("任务中心主页面H5")
    @Test
    void shouldReturnSuccessfully() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("Authorization",TestCaseHelpful.login("17700000066","123456"));
        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
//        HashMap<String,Object> params = new HashMap<>();
//        params.put("moduleId",2411);
//        params.put("pageNo",1);
//        params.put("pageSize",10);
//        params.put("cityName","%E4%B9%9D%E6%B1%9F%E5%B8%82");
//        params.put("sortType","COMPOSITE");
//        params.put("zipCode",null);
        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：部份匹配，
        String expectedStr = TestCaseHelpful.getFileContent(assert2);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
