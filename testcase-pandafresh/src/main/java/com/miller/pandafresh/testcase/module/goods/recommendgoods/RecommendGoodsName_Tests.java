package com.miller.pandafresh.testcase.module.goods.recommendgoods;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.JSONUtils;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * recommendGoods
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/12/09 16:39:31
 */
@Scenario(
        scenarioID = "01JY132Z6G8YJHNKBFJMTNF289", // 自动生成，不要修改
        scenarioName = "加购推荐：商品名称正确",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 2, manualTestTime = 3)
@DisplayName("加购推荐：商品名称正确")
public class RecommendGoodsName_Tests {
    // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
    String uri = TestcaseConfig.HpfHost + "/goods/recommend/goods";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求头。默认从 resources 目录下读取文件。
    String headers = "module/recommendgoods/request/headers.json";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
    String params = null;
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
    String body = "module/recommendgoods/request/body.json";
    // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
    String assertFullField = "module/recommendgoods/response/assert_full_field.json";

    List<Map<String, Object>> goodsSns;
    @BeforeAll
    void beforeAll(){
        //查询加购商品的推荐编码
        String sql = "SELECT a.rec_goods_sn FROM addcart_best_match_manual_config a WHERE a.add_goods_sn='tz0816002';";
        // 查询多条记录
        goodsSns = FreshTestDBHelpful.executeSelectListSql(sql);
    }
    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        requestBody = JSONUtils.updateJsonValue(requestBody,"pageType",0);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.records").isNotNull();

        JSONArray goodsList = TestCaseHelpful.extractValue(responseBody, "result.records");
        for (int i=0;i<goodsList.size();i++) {
            Map goods = (Map<String, Object>) goodsList.get(0);
            String name = (String) goods.get("goodsName");
            int id = (int) goods.get("goodsId");
            //查询商品名称信息
            String sql = "SELECT * FROM goods fg WHERE fg.goods_id=" + id + "";
            Map<String, Object> expect = FreshTestDBHelpful.executeSelectOneSql(sql);
            String expectName = expect.get("goods_name").toString();
            TestCaseHelpful.assertThatJson(name.contains(expectName)).isEqualTo(true);
        }
    }
} 