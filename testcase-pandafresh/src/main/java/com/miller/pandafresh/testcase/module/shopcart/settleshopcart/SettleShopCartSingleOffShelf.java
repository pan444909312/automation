package com.miller.pandafresh.testcase.module.shopcart.settleshopcart;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.JSONUtils;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * settleShopCart
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/10/28 16:51:03
 */

//用于组合场景
@TestFramework
@DisplayName("步骤：购物车-去结算-结算单个下架商品")
public class SettleShopCartSingleOffShelf {

    String shopCartId = "0";
    String goodsId = "0";
    String goodsSkuId = "0";
    String goodsCount = "1";
    @BeforeAll
    void beforeAll(){
        //查找用户普通商品的加购记录
        String sql = "SELECT t.* FROM shop_cart t left join goods g on g.goods_id=t.goods_id where t.user_id=252344 and t.add_source=1 and g.status=1 and g.portal_id=3 and g.type=1  limit 10";
        // 查询多条记录
        List<Map<String, Object>> selectListSql = FreshTestDBHelpful.executeSelectListSql(sql);
        // 获取查询结果的第1行数据中的数据库列明为“add_id”的值
        try {
            shopCartId = selectListSql.get(0).get("shop_cart_id").toString();
            goodsId = selectListSql.get(0).get("goods_id").toString();
            goodsSkuId = selectListSql.get(0).get("goods_sku_id").toString();
            goodsCount = selectListSql.get(0).get("goods_count").toString();
            //下架商品
            String sql1 = "update goods g set g.status=0 where g.goods_id="+goodsId;
            FreshTestDBHelpful.executeInsertOrUpdateOrDelete(sql1);
        }catch (Exception e){
            System.out.println("购物车无商品！！");

        }

    }

    @AfterAll
     void afterAll(){
        //上架商品
        String sql1 = "update goods g set g.status=1 where g.goods_id="+goodsId;
        FreshTestDBHelpful.executeInsertOrUpdateOrDelete(sql1);
    }

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
        String uri = TestcaseConfig.HpfHost + "/shopcart/settleShopCart";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/shopcart/settleshopcart/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/shopcart/settleshopcart/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/shopcart/settleshopcart/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        requestHeaders.replace("userid","1398661332");
        requestHeaders.put("authorization",TestCaseHelpful.login("17700004444","123456"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody,"$.mulitiSettleAllShopCartIdList.[0]",shopCartId);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody,"$.shopCartList.[0].shopCartId",shopCartId);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody,"$.shopCartList.[0].goodsId",goodsId);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody,"$.shopCartList.[0].goodsSkuId",goodsSkuId);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody,"$.shopCartList.[0].goodsCount",goodsCount);

        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);


        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(5037);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.error").isEqualTo("包含已下架商品，无法进行结算(5037)");

    }
} 