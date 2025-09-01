package com.miller.pandafresh.testcase.module.goods;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * 商详页-关联商品
 * 相似商品和搭配购买商品
 */
@Scenario(
        scenarioID = "01JWG74KCTFV96ZPCSGK9GYHRS",
        scenarioName = "商品详情-关联商品:搭配商品",
        author = "zhangpei@hungrypandagroup.com",
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 2)
@DisplayName("商品详情-关联商品:搭配商品")
public class RelateCombineTests {
    String uri = TestcaseConfig.HOST + "/goods/relate";
    String method = "POST";
    String headers = "module/headersPF.json";
    String body = "module/goods/request/relate.json";
    String assert1 = "module/goods/response/assert_relate.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccessfully() {
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        var requestBody = TestCaseHelpful.getJsonRequestBody(body);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1);// 使用 JsonPath 方式
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data").isNotNull();

        JSONArray goodsList = TestCaseHelpful.extractValue(responseBody, "data.collocationGoodsList");
        String sql = "SELECT * FROM pf_goods_combine s  LEFT JOIN goods g on s.goods_id=g.goods_id WHERE g.portal_id=3 and g.is_del=0 and g.`status`=1 and s.input_goods_id=148717;";
        List<Map<String, Object>> selectListSql = FreshTestDBHelpful.executeSelectListSql(sql);
        for (int i=0;i<goodsList.size();i++){
            Map goods = (Map<Integer, Object>) goodsList.get(i);
            Integer goodsId = (Integer) goods.get("goodsId");
            Boolean actual = selectListSql.toString().contains(goodsId+"");
            TestCaseHelpful.assertThatJson(actual).isEqualTo(true);
        }

    }
}
