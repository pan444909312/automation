package com.miller.pandafresh.testcase.module.goods.getcategory;

import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.service.framework.annotation.Scenario;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.PandaTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * getCategory
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/08/27 11:34:29
 */
@Scenario(
        scenarioID = "01K3MSEQVZPMK6YZ3VMZMN4AER", // 自动生成，不要修改
        scenarioName = "分类页：获取当前站点下启用的一级分类",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("分类页：获取当前站点下启用的一级分类")
public class GetCategory_Tests {

    List<Map<String, Object>> parentCategoryList;

    @BeforeAll
    void beforeAll(){
        //查询宁波站点下所有启用的一级分组
        String sql = "SELECT * FROM front_groups fg WHERE fg.parent_id=0 and fg.portal_id=3 and fg.`status`=1;";
        // 查询多条记录
        parentCategoryList = FreshTestDBHelpful.executeSelectListSql(sql);
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
        String uri = TestcaseConfig.HpfHost + "/goods/getCategory";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/goods/getcategory/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/goods/getcategory/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/goods/getcategory/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

        JSONArray categoryList = TestCaseHelpful.extractValue(responseBody, "result.categoryList");

        for (int i = 0; i < categoryList.size(); i++) {
            Map category = (Map<String, Object>) categoryList.get(i);
            String name = (String) category.get("categoryName");
            //校验返回的一级分组是否是宁波站点的启用的一级分组
            TestCaseHelpful.assertThatJson(parentCategoryList.toString().contains(name)).isEqualTo(true);

        }


    }
} 