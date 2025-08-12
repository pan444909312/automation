package com.miller.pandafresh.testcase.module.goods.getgoods;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.JSONUtils;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * getGoods
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/07/16 16:44:06
 */
@Scenario(
        scenarioID = "01K096FFBCK2DP4VCJ1CRE5FFN", // 自动生成，不要修改
        scenarioName = "商品详情-含税的原价",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("商品详情-含税的原价")
public class GetGoodsHasTax_Tests {

    String goodsId = "0";
    Map<String, Object> oneGoods;
    @BeforeAll
    void beforeAll(){
        //查询普通商品
        String sql = "SELECT g.goods_id,gs.original_price,g.custom_tax FROM goods g left join goods_sku gs on gs.goods_id=g.goods_id where g.status=1 " +
                "and g.portal_id=3 and g.is_del=0 and gs.is_del=0 and type=1 and g.custom_tax!=0 limit 1";
        // 查询1条记录
        oneGoods = FreshTestDBHelpful.executeSelectOneSql(sql);
        goodsId = oneGoods.get("goods_id").toString();
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
        String uri = TestcaseConfig.HpfHost + "/goods/getGoods";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/goods/getgoods/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/goods/getgoods/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/goods/getgoods/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        requestBody  = JSONUtils.updateJsonValue(requestBody,"goodsId",goodsId);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1000);
        Integer x = (Integer) oneGoods.get("original_price")/100;
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.originalPrice").isNotEqualTo("\""+x+"\"");
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.customTax").isNotEqualTo(oneGoods.get("custom_tax"));
    }
} 