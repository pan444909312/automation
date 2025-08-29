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
 * getAdOfFloatWindow - push_user not fit
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/08/29 10:00:00
 */
@Scenario(
        scenarioID = "01JY1A1H9K2PQ8ZQ3W7B29VQ2F", // 自动生成，不要修改
        scenarioName = "融合首页浮窗：浮窗广告-人群不符",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("融合首页浮窗：浮窗广告-人群不符")
public class GetAdOfFloatWindowPushUserNotFit_Tests {

    Map<String, Object> selectOneSql;
    Object originalPushUser;

    @BeforeAll
    void beforeAll() {
        // 查找渠道包含app的生效的所有用户可见的优先级最高的浮窗广告，且当前 push_user != 1
        // 达达-1、h5-2、h5&达达-3、app-4、app&达达-5、app&h5-6、app&h5&达达-7
        // 广告位置 0 首页弹窗 1 个人中心中通广告 2 订单详情广告 3 支付成功广告 4-首页浮窗 5-个人中心金刚区 6-首页瀑布流广告
        String sql = "SELECT * FROM ad_manage a WHERE a.ad_position=4 and a.is_del=0 and a.ad_status=1 " +
                "and a.sync_web>3 and a.portal_id=3 and a.push_user!=1 and a.end_date>NOW()  ORDER BY a.sort DESC LIMIT 1;";
        try {
            // 查询1条记录
            selectOneSql = FreshTestDBHelpful.executeSelectOneSql(sql);
            originalPushUser = selectOneSql.get("push_user");
            String sqlUpdate = "update ad_manage a set a.push_user=1 where a.id=" + selectOneSql.get("id");
            // 将此广告更新为 push_user=1（人群不符）
            FreshTestDBHelpful.executeInsertOrUpdateOrDelete(sqlUpdate);
        } catch (Exception e) {
            System.out.println("无符合的人群不符浮窗广告数据！！");
        }
    }

    @AfterAll
    void afterAll() {
        if (selectOneSql != null && selectOneSql.get("id") != null && originalPushUser != null) {
            String sqlRevert = "update ad_manage a set a.push_user=" + originalPushUser + " where a.id=" + selectOneSql.get("id");
            // 还原 push_user
            FreshTestDBHelpful.executeInsertOrUpdateOrDelete(sqlRevert);
        }
    }

    // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
    String uri = TestcaseConfig.HpfHost + "/ad/getAdOfFloatWindow";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求头。默认从 resources 目录下读取文件。
    String headers = "module/getadoffloatwindow/request/headers.json";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
    String params = null;
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
    String body = "module/getadoffloatwindow/request/body.json";

    @DisplayName("正向流程")
    @Test
    void shouldExcludeAdWhenPushUserNotFit() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        // 断言：接口正常，且不返回该广告（结果为空对象）
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result").isEqualTo("{}");
    }
}

