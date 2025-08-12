package com.miller.pandafresh.testcase.module.index.getindex;

import com.miller.service.framework.annotation.Scenario;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * getIndex
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/07/29 14:28:58
 */
@Scenario(
        scenarioID = "01K0E6ZB3Z1N0FXPJR0RVHKW65", // 自动生成，不要修改
        scenarioName = "pf首页-顶部banner-隐藏：生效渠道不符合",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("pf首页-顶部banner-隐藏：生效渠道不符合")
public class GetIndexBannerChannelNotFit_Tests {

    Map<String, Object> selectOneSql;
    @BeforeAll
     void beforeAll() throws InterruptedException {
        //查找渠道包含app的生效的非新人用户可见的优先级最高的banner广告
        //达达-1、h5-2、h5&达达-3、app-4、app&达达-5、app&h5-6、app&h5&达达-7
        String sql = "SELECT * FROM ad a WHERE a.model=1 and a.del_status=0 and a.`status`=1 " +
                "and a.is_shield_nested_web>3 and a.portal_id=3 and a.push_user!=1 and a.end_date>NOW()  ORDER BY a.sort DESC LIMIT 1;";
        try {
            // 查询1条记录
            selectOneSql = FreshTestDBHelpful.executeSelectOneSql(sql);
            String sql1 = "update ad a set a.is_shield_nested_web=3 where a.id="+selectOneSql.get("id")+"";
            //将此广告的渠道更新为非app
            FreshTestDBHelpful.executeInsertOrUpdateOrDelete(sql1);
        }catch (Exception e){
            System.out.println("无符合的banner广告数据！！");
        }
    }
    @AfterAll
     void afterAll(){
        String sql2 = "update ad a set a.is_shield_nested_web=7 where a.id="+selectOneSql.get("id")+"";
        //将此广告的渠道更新为三个渠道可用
        FreshTestDBHelpful.executeInsertOrUpdateOrDelete(sql2);
    }

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
        String uri = TestcaseConfig.HpfHost + "/index/getIndex";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/index/getindex/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/index/getindex/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/index/getindex/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        //登录老用户
        requestHeaders.put("userId","1398661332");
        requestHeaders.put("authorization",TestCaseHelpful.login("17700004444","123456"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,请求app渠道的首页
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1000);

        JSONArray vajraList = TestCaseHelpful.extractValue(responseBody,"$.result.indexList[?(@.type=='banner')].items");

        //返回的banner广告不能包含此广告
        TestCaseHelpful.assertThatJson(vajraList.toString().contains(selectOneSql.get("id").toString())).isEqualTo(false);

    }
} 