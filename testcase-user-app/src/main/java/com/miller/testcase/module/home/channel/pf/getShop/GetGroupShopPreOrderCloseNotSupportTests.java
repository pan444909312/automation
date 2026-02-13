package com.miller.testcase.module.home.channel.pf.getShop;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.miller.testcase.utils.TestCaseHelpful.assertThat;

@Scenario(
        scenarioID = "01JWSS1KB2H2X5WV4YW6GE43GG",
        scenarioName = "品类专题-店铺召回：店铺营业状态-打烊可预约",
        author = "zhangpei@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("品类专题-店铺召回：店铺营业状态-打烊不可预约")
public class GetGroupShopPreOrderCloseNotSupportTests {

    @DisplayName("品类专题-店铺召回：店铺营业状态-打烊不可预约")
    @Test
    void shouldReturnSuccessfully() {
        var responseShopBody = TestCaseHelpful.channelPfGetShopVOByShopId(335765650,0,55,0, null, null, null, null,null,null,null);
        // 检查响应体不为空
        assertThat(responseShopBody).isNotNull();

    }
}