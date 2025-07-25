package com.miller.testcase.module.topic.getspecialtopicgoods;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.JSONUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.FreshTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * getSpecialTopicGoods
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/07/25 11:09:19
 */
@Scenario(
        scenarioID = "01K0ZRWYDRT11FGY6KYBEX37NF", // 自动生成，不要修改
        scenarioName = "专题商品-组合促销专题：展示专题关联的上架商品",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("专题商品-组合促销专题：展示专题关联的上架商品")
public class GetSpecialTopicGoodsCombine_Tests {

    //兜底值
    String topicId = "1832";

    @BeforeAll
    void beforeAll(){
        //查询宁波站点下生效中的组合促销专题
        String sql = "SELECT * FROM special_topic t WHERE t.type=6 and t.is_del=0 and t.effect_status=1 and t.portal_id=3 LIMIT 1;";
        // 查询1条记录
        Map<String, Object> selectSql = FreshTestDBHelpful.executeSelectOneSql(sql);
        topicId = selectSql.get("special_topic_id").toString();
    }
    @AfterAll
    static void afterAll(){
        // 所有 @Test 方法执行之后会执行  @@AfterAll 注解的方法, 这里的代码当前测试类期间只会执行一次
        // 你可以在这里执行后置的操作，比如: 销毁测试数据、还原数据库、清理环境等
    }

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
        String uri = TestcaseConfig.H5HOST + "/topic/getSpecialTopicGoods";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/topic/getspecialtopicgoods/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/topic/getspecialtopicgoods/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/topic/getspecialtopicgoods/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody,"$.pd.specialTopicId",topicId);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

        //查找专题下上架、当前配送区域下的商品
        String sql = "SELECT * FROM special_topic_data d " +
                "LEFT JOIN goods g on g.goods_id=d.goods_id " +
                "LEFT JOIN goods_delivery_area a on a.goods_id=g.goods_id " +
                "WHERE g.is_del=0 and g.`status`=1 and d.special_topic_id="+topicId+" " +
                "and a.delivery_area_id=3 GROUP BY d.goods_id ORDER BY d.sort DESC;";
        List<Map<String, Object>> selectListSql = FreshTestDBHelpful.executeSelectListSql(sql);

        //提取返回结果里的商品数据
        JSONArray goodsList = TestCaseHelpful.extractValue(responseBody,"$.result.records");

        for (int i=0;i<goodsList.size();i++){
            String exceptGoodsId = selectListSql.get(i).get("goods_id").toString();
            Boolean actual = goodsList.toString().contains(exceptGoodsId);
            //包含
            TestCaseHelpful.assertThatJson(actual).isEqualTo(true);
        }
    }
} 