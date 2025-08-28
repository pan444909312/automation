package com.miller.pandafresh.testcase.module.ad.getonebyhomepage;

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
 * getOneByHomePage
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/08/28 20:30:23
 */
@Scenario(
        scenarioID = "01JY12J8A5J7T45GMANKDE92AY", // 自动生成，不要修改
        scenarioName = "首页弹窗-状态不符",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("首页弹窗-状态不符")
public class GetOneByHomePageStatusNotFit_Tests {


    Map<String, Object> selectOneSql;
    @BeforeAll
    void beforeAll() throws InterruptedException {
        //查找渠道包含app的生效的所有用户可见的优先级最高的弹窗
        //达达-1、h5-2、h5&达达-3、app-4、app&达达-5、app&h5-6、app&h5&达达-7
        //广告位置 0 首页弹窗 1 个人中心中通广告 2 订单详情广告 3 支付成功广告 4-首页浮窗 5-个人中心金刚区 6-首页瀑布流广告
        String sql = "SELECT * FROM ad_manage a WHERE a.ad_position=0 and a.is_del=0 and a.ad_status=1 " +
                "and a.sync_web>3 and a.portal_id=24 and a.push_user!=1 and a.end_date>NOW()  ORDER BY a.sort DESC LIMIT 1;";
        try {
            // 查询1条记录
            selectOneSql = FreshTestDBHelpful.executeSelectOneSql(sql);
            String sql1 = "update ad_manage a set a.ad_status=0 where a.id="+selectOneSql.get("id")+"";
            //将此广告的更新为禁用
            FreshTestDBHelpful.executeInsertOrUpdateOrDelete(sql1);
        }catch (Exception e){
            System.out.println("无符合的浮窗广告数据！！");
        }
    }
    @AfterAll
    void afterAll(){
        String sql2 = "update ad_manage a set a.ad_status=1 where a.id="+selectOneSql.get("id")+"";
        //将此广告更新为启用
        FreshTestDBHelpful.executeInsertOrUpdateOrDelete(sql2);
    }


    // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
    String uri = TestcaseConfig.HpfHost + "/ad/getOneByHomePage";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求头。默认从 resources 目录下读取文件。
    String headers = "module/getonebyhomepage/request/headers.json";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
    String params = null;
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
    String body = "module/getonebyhomepage/request/body.json";
    // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
    String assertFullField = "module/getonebyhomepage/response/assert_full_field.json";

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
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result").isEqualTo("{}");

    }
} 