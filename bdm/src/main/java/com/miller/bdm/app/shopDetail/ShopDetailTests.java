package com.miller.bdm.app.shopDetail;

import com.miller.bdm.app.shopDetail.flow.ShopDetailFlow;
import com.miller.bdm.app.shopDetail.respones.ShopDetailResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.bdm.login.flow.ERPLoginFlow;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@DisplayName("bdm-商家详情页活动任务详情")


//@Scenario(
//        scenarioID="01J5N1X0M0JW76719Wwooorx",
//        scenarioName = "bdm-商家详情页基本信息页",
//        developmentTime = 30,
//        maintenanceTime = 0 ,
//        manualTestTime = 5
//)
public class ShopDetailTests {

    @BeforeAll
    static void beforeAll() {
        // 测试前置条件
        ERPLoginFlow.loginByDefaultUser();
    }


    @MethodSource("com.miller.bdm.app.shopDetail.provider.ShopDetailProvider#ShopTagList")
    @ParameterizedTest
    @DisplayName("bdm-商家详情页基本信息页")

    void ShowTask() {
        ShopDetailResponseDTO responseDTO = ShopDetailFlow.getShowDetail();
        assertThat(responseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        assertThat(responseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessageTwo);
        CacheUtils.set(BusinessConstantOfERP.SHOP_TAG_KEY, responseDTO);
    }



}

