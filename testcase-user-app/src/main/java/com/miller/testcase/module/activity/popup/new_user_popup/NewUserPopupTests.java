package com.miller.testcase.module.activity.popup.new_user_popup;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.PandaTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * new user popup
 *
 * @author yancancan
 * @version 2.0
 * @since 2025/06/17 15:13:15
 */
@Scenario(
        scenarioID = "01JXYBRA0EMA6P11H464S8B6MZ", // 自动生成，不要修改
        scenarioName = "new user popup",
        author = "yancancan@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("new user popup:新用户自动发券（新人权益）")
public class NewUserPopupTests {
    // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
    String uri = TestcaseConfig.HOST_APP + "/api/app/user/popup/redpacket";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求头。默认从 resources 目录下读取文件。
    String headers = "module/activity/popup/new_user_popup/request/headers.json";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
    String params = null;
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
    String body = "module/activity/popup/new_user_popup/request/should_success.json";
    // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
    String assert1 = "module/activity/popup/new_user_popup/response/assert_full_field.json";

    @BeforeAll()
    public static void beforeAll() {
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("delete from `user_log` where  device_id='2365086D-71E6-4761-9C6B-75234AEEB0BF' ;\n" +
                "delete from `hp_user_benefit_red_packet_record` where device_id='2365086D-71E6-4761-9C6B-75234AEEB0BF' ;" +
                "delete from `hp_user_new_red_packet_record` where   device_id='2365086D-71E6-4761-9C6B-75234AEEB0BF' ;\n" +
                "delete FROM hp_invite_award_benefit_record WHERE device_id in ('2365086D-71E6-4761-9C6B-75234AEEB0BF');\n" +
                "delete From hp_new_user_cdkey_record where user_id=1398717334;\n" +
                "delete From user_cdkey where user_id=1398717334;");
    }
    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("authorization", TestCaseHelpful.login("17700000044","123456"));
        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assert1);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

    }
} 