package com.miller.pandafresh.testcase.module.ad.getadoffloatwindow;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * getAdOfFloatWindowInvite
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/08/28 16:44:46
 */
@Scenario(
        scenarioID = "01JY127Z6RWAJ6S9X4HNXH2CB1", // 自动生成，不要修改
        scenarioName = "融合首页浮窗：拉新广告-状态不符",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("融合首页浮窗：拉新广告-状态不符")
public class GetAdOfFloatWindowInviteStatusNotFit_Tests {

    String id = "28";
    @BeforeAll
    void beforeAll() throws InterruptedException {

        String sql1 = "update invite_ad a set a.enable_status=0 where a.id="+id+"";
        //将此广告的更新为禁用
        FreshTestDBHelpful.executeInsertOrUpdateOrDelete(sql1);
    }
    @AfterAll
    void afterAll(){
        String sql2 = "update invite_ad a set a.enable_status=1 where a.id="+id+"";
        //将此广告更新为启用
        FreshTestDBHelpful.executeInsertOrUpdateOrDelete(sql2);
    }

    // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
    String uri = TestcaseConfig.HpfHost + "/ad/getAdOfFloatWindow";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求头。默认从 resources 目录下读取文件。
    String headers = "module/getadoffloatwindowinvite/request/headers.json";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
    String params = null;
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
    String body = "module/getadoffloatwindowinvite/request/body.json";
    // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
    String assertFullField = "module/getadoffloatwindowinvite/response/assert_full_field.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.inviteAdDTO").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.inviteAdDTO.adSubTitle").isEqualTo("活动已下线");

    }
} 