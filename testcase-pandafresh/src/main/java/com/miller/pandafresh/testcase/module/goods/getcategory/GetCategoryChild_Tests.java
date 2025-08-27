package com.miller.pandafresh.testcase.module.goods.getcategory;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
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
        scenarioID = "01K3MSEQVZPMK6YZ3VMZMN4AET", // 自动生成，不要修改
        scenarioName = "分类页：二级分组返回正确",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("分类页：二级分组返回正确")
public class GetCategoryChild_Tests {

    List<Map<String, Object>> childCategoryList;

    @BeforeAll
    void beforeAll(){
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

        JSONArray parnetList = TestCaseHelpful.extractValue(responseBody, "result.categoryList");
        Map category = (Map<String, Object>) parnetList.get(0);
        int id = (int) category.get("categoryId");

        JSONArray childList = TestCaseHelpful.extractValue(responseBody, "result.categoryList.[0].childCategory");

        //查询一级分组下的启用的二级分组
        String sql = "SELECT * FROM front_groups fg WHERE  fg.portal_id=3 and fg.`status`=1 and fg.parent_id="+id+"";
        // 查询多条记录
        childCategoryList = FreshTestDBHelpful.executeSelectListSql(sql);

        for (int i = 0; i < childList.size(); i++) {
            Map child = (Map<String, Object>) childList.get(i);
            String name = (String) child.get("categoryName");
            //校验返回的二级分组是否正确
            TestCaseHelpful.assertThatJson(childCategoryList.toString().contains(name)).isEqualTo(true);

        }


    }
} 