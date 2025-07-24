package com.miller.testcase.module.goods.getgoodsbyfirstcategory;

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

/**
 * getGoodsByFirstCategory
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/07/24 17:13:46
 */
@Scenario(
        scenarioID = "01K0XVBJ5H8Y8NAJT6KW435C40", // 自动生成，不要修改
        scenarioName = "分类页-二级分类商品展示检查：召回上架的商品",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("分类页-二级分类商品展示检查：召回上架的商品")
public class GetGoodsByFirstCategoryGoodsOff_Tests {

    //自动化测试一级分组id
    String firstCategoryId = "887";
    //兜底的二级分组id
    String categoryId = "890";

    String exceptGoodsId = "149252";
    @BeforeAll
     void beforeAll(){
        // 确保887分组下有启用的二级分组890
        String sql = "SELECT * FROM front_groups f WHERE f.parent_id=887 and f.groups_id=890 and f.`status`=1 LIMIT 1";
        String id = FreshTestDBHelpful.executeSelectOneSql(sql).get("groups_id").toString();
        //查找当前配送区域，分组下的下架的商品
        String sql1 = "SELECT g.* FROM goods_groups gg " +
                 "LEFT JOIN goods g on gg.goods_id=g.goods_id " +
                 "LEFT JOIN goods_delivery_area a on g.goods_id=a.goods_id " +
                 "WHERE gg.groups_id="+categoryId+" and g.portal_id=3 and a.delivery_area_id=3" +
                 "  and g.`status`=0 and g.is_del=0 LIMIT 1";
        exceptGoodsId = FreshTestDBHelpful.executeSelectOneSql(sql1).get("goods_id").toString();
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
        String uri = TestcaseConfig.HpfHost + "/goods/getGoodsByFirstCategory";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/goods/getgoodsbyfirstcategory/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/goods/getgoodsbyfirstcategory/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/goods/getgoodsbyfirstcategory/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        requestBody = JSONUtils.updateJsonValue(requestBody,"categoryId",firstCategoryId);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

        JSONArray goodsList = TestCaseHelpful.extractValue(responseBody, "$.result.categoryList[?(@.secondCategoryId=='890')].goodsList");
        Boolean actual = goodsList.toString().contains(exceptGoodsId);
        //不包含下架商品
        TestCaseHelpful.assertThatJson(actual).isEqualTo(false);
    }
} 