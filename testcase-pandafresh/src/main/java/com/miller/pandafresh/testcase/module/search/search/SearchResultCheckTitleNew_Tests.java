package com.miller.pandafresh.testcase.module.search.search;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * search
 *
 * @author zhangpei
 * @version 2.0
 * @since 2026/01/06 17:56:04
 */
@Scenario(
        scenarioID = "01K0C3VPR6T8F1HQ70SVSH36JW", // 自动生成，不要修改
        scenarioName = "搜索-检查商品副标题:新索引",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("搜索-检查商品副标题：新索引")
public class SearchResultCheckTitleNew_Tests {

    @BeforeAll
    static void beforeAll(){
        // 所有 @Test 方法执行之前会执行  @BeforeAll 注解的方法, 这里的代码当前测试类期间只会执行一次
        // 你可以在这里执行前置的操作，比如: SQL 初始化用例的前置条件
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
        String uri = TestcaseConfig.HpfHost + "/search/search";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/search/search/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/search/search/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/search/search/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        String testGroupNew = "I_R_TEST_GROUP,I_R_TEST_GROUP,SUPERMARKET_SCENES_TEST_GROUP,17,S_H_R_L_TEST_GROUP_4,18,22,23,28,29,30,31,32,NUMBER_MASKING_00,33,34,36,35,40,39,45,49,52,53,55,56,HPF,FASTD01,YSDCS02,IST01,HYBQ01,SKEQ01,XRJ01,TJBQ01,HYXBQ01,TJTCX01,YBXS02,CCPRO01,SKXRB01,ABT02,QYTCD01,SMSS01,XMLM01,RRREC01,ZFBMM01,SPSS01,MRBX01,PLCC01,SXAU01,PAYTO02,LXTZ01,JQSJ01,SYGB01,JSYXR01,GDJ02,ZTKP01,ZKTS02,RTR01,SYUI01,SWS01,DWC01,HHAB01,YHTX01,TCZT01,XTZA01,QDJS01,XGBSS02,SYSKA02,WLTC01,SPM02,XGBFU01,SDDAB01,TCSHW02,ZNYX01,JSYHA01,DPCDA01,DPHD01,YRSZT01,SKBQ01,TSRW02,LLQX01,XDRS01,RDMU01,YHMGD01,NTCZT01,DPCDB01,CZHG01,WLTCN01,ESFI02,ABCS01,DPYGB01,HBCY01,GWCYC01,HYUI01,SKBD02,SKYS01,GGCLA01,MGDD01,YFYHA01,SKYH01,XRSY01,HDMR01,SYMK01,CMRT01,CPYHA01,SKYX01,VOOPT01,YHLL01,YJSDA01,XGSPA01,LXCYH01,TCZKB01,XRQSD01,HANLP01,JLYZR01,CDQC01,JGYH01,LLBD01,HYXY02,SSZHY01,SSZHX01,TOPBQ01,SSZHJ01";
        requestHeaders.replace("testgroup",testGroupNew);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1000);

        // 对json文件中排除的字段进行更加复杂的断言
        JSONArray goodsList = TestCaseHelpful.extractValue(responseBody, "result.records");
        for (int i = 0; i < 1; i++) {
            Map goods = (Map<String, Object>) goodsList.get(i);
            String name = (String) goods.get("goodsTitle");
            int id = (int) goods.get("goodsId");
            //查询商品名称信息
            String sql = "SELECT * FROM goods fg WHERE fg.goods_id=" + id + "";
            Map<String, Object> expect = FreshTestDBHelpful.executeSelectOneSql(sql);
            String expectName = expect.get("title").toString();
            TestCaseHelpful.assertThatJson(name.contains(expectName)).isEqualTo(true);
        }

    }
} 