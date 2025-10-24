package com.miller.testcase.module.activity.popup.new_user_popup_nologin;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.PandaTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * new user popup nologin
 *
 * @author yancancan
 * @version 2.0
 * @since 2025/08/05 16:47:35
 */
@Scenario(
        scenarioID = "01K1WPM85EC5R6HG9QP2VFFM87", // 自动生成，不要修改
        scenarioName = "new user popup nologin",
        author = "yancancan@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("new user popup nologin:未登陆新人权益弹窗")
public class NewUserPopupNologinTests {

    @BeforeAll
    static void beforeAll(){
        // 所有 @Test 方法执行之前会执行  @BeforeAll 注解的方法, 这里的代码当前测试类期间只会执行一次
        // 你可以在这里执行前置的操作，比如: SQL 初始化用例的前置条件
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("delete from `user_log` where  device_id='2365086D-71E6-4761-9C6B-75234AEEB0BF' ;\n" +
                "delete from `hp_user_benefit_red_packet_record` where device_id='2365086D-71E6-4761-9C6B-75234AEEB0BF' ;" +
                "delete from `hp_user_new_red_packet_record` where   device_id='2365086D-71E6-4761-9C6B-75234AEEB0BF' ;\n" +
                "delete FROM hp_invite_award_benefit_record WHERE device_id in ('2365086D-71E6-4761-9C6B-75234AEEB0BF');\n" +
                "delete From hp_new_user_cdkey_record where user_id=1398717334;\n" +
                "delete From user_cdkey where user_id=1398717334;\n"+"");}
    @AfterAll
    static void afterAll(){
        // 所有 @Test 方法执行之后会执行  @@AfterAll 注解的方法, 这里的代码当前测试类期间只会执行一次
        // 你可以在这里执行后置的操作，比如: 销毁测试数据、还原数据库、清理环境等
    }

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
        String uri = TestcaseConfig.HOST_APP + "/api/user/popup";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "GET";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/activity/popup/new_user_popup_nologin/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = "module/activity/popup/new_user_popup_nologin/request/params.json";
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
//        String body = "module/activity/popup/new_user_popup_nologin/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/activity/popup/new_user_popup_nologin/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
//        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, null);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

    }
} 