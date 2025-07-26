package com.miller.testcase.module.topic.getspecialtopic;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.JSONUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.FreshTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * getSpecialTopic
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/07/25 16:06:40
 */
@Scenario(
        scenarioID = "01JXVNHMWT5RNN4K9N1CQPDJRY", // 自动生成，不要修改
        scenarioName = "获取专题信息-随心配专题",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("获取专题信息-随心配专题")
public class GetSpecialTopicFollow_Tests {
    // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
    String uri = TestcaseConfig.H5HOST + "/topic/getSpecialTopic";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求头。默认从 resources 目录下读取文件。
    String headers = "module/getspecialtopic/request/headers.json";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
    String params = null;
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
    String body = "module/getspecialtopic/request/should_success.json";
    // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
    String assert1 = "module/getspecialtopic/response/assert_full_field.json";
    Object topicId =1832;
    Map<String, Object> selectOneSql;
    @BeforeAll
    void beforeAll() {
        //获取随心配专题(启用生效中未删除的专题)
        String sql = "SELECT t.* FROM special_topic t  where t.portal_id=3 and t.is_del=0 and t.effect_status=1 and t.special_topic_status=1 and t.type=8 limit 1";
        // 查询多条记录
        selectOneSql = FreshTestDBHelpful.executeSelectOneSql(sql);
        // 获取查询结果的第1行数据中的数据库列明为“add_id”的值
        topicId = selectOneSql.get("special_topic_id");
    }
    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody,"$.pd.specialTopicId",topicId);

        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1);// 使用 JsonPath 方式
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.topicName").isEqualTo(selectOneSql.get("name"));
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.pic").isEqualTo(selectOneSql.get("pic"));
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.backgroundColor").isEqualTo(selectOneSql.get("background_color"));
    }
} 