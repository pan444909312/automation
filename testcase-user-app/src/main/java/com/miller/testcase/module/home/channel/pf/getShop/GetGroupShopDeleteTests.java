package com.miller.testcase.module.home.channel.pf.getShop;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.miller.testcase.utils.TestCaseHelpful.assertThat;

@Scenario(
        scenarioID = "01JWSS1KB2H2X5WV4YW6GE43GH" ,
        scenarioName = "品类专题_店铺召回：店铺有效状态-已删除 - 不召回",
        author = "zhangpei@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("品类专题_店铺召回：店铺有效状态-已删除 - 不召回")
public class GetGroupShopDeleteTests {

    @DisplayName("品类专题_店铺召回：店铺有效状态-已删除 - 不召回")
    @Test
    void shouldReturnSuccessfully() {
        var responseShopBody = TestCaseHelpful.channelPfGetShopVOByShopId(581207334,0,55,0, null, null, null, null,null,null,null);
        // 检查响应体为空
        assertThat(responseShopBody).isNull();

    }
}