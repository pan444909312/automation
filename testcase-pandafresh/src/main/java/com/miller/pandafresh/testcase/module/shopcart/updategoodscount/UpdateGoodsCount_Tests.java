package com.miller.pandafresh.testcase.module.shopcart.updategoodscount;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.JSONUtils;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * updateGoodsCount
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/06/18 16:48:08
 */
@Scenario(
        scenarioID = "01JY13JR7Q0VQE5AQ58F5HAEZM", // 自动生成，不要修改
        scenarioName = "修改购物车商品数量-增加",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("修改购物车商品数量-增加")
public class UpdateGoodsCount_Tests {
    // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
    String uri = TestcaseConfig.HpfHost + "/shopcart/updateGoodsCount";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求头。默认从 resources 目录下读取文件。
    String headers = "module/updategoodscount/request/headers.json";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
    String params = null;
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
    String body = "module/updategoodscount/request/body.json";
    // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
    String assertFullField = "module/updategoodscount/response/assert_full_field.json";
    String shopCartId = "0";
    String goodsCount = "0";
    @BeforeAll
    void beforeAll() {
        //查询来源为融合的购物车记录
        String sql = "SELECT t.* FROM shop_cart t left join goods g on g.goods_id=t.goods_id where t.user_id=249222 and t.add_source=1 and g.status=1 and g.portal_id=3  limit 10";
        // 查询多条记录
        List<Map<String, Object>> selectListSql = FreshTestDBHelpful.executeSelectListSql(sql);
        // 获取查询结果的第1行数据中的数据库列明为“add_id”的值
        try {
            shopCartId = selectListSql.get(0).get("shop_cart_id").toString();
            goodsCount = selectListSql.get(0).get("goods_Count").toString();
        }catch (Exception e){
            System.out.println("购物车无商品！！");
        }

    }
    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        //登录用户
        requestHeaders.put("userid",249222);
        requestHeaders.put("authorization",TestCaseHelpful.login("18968046019","123456"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        requestBody = JSONUtils.updateJsonValue(requestBody,"goodsCount",goodsCount);
        requestBody = JSONUtils.updateJsonValue(requestBody,"shopCartId",shopCartId);
        requestBody = JSONUtils.updateJsonValue(requestBody,"isAdd",1);

        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.result").isEqualTo(true);

    }
} 