package com.miller.bdm.app.shopTask;

import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.bdm.login.flow.ERPLoginFlow;
import com.miller.bdm.app.shopTask.flow.ShopTaskFlow;
import com.miller.bdm.app.shopTask.request.ShopTaskRequestDTO;
import com.miller.bdm.app.shopTask.respones.ShopTaskResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@DisplayName("bdm-商家详情页活动任务详情")


@Scenario(
        scenarioID="01J5N1X0M0JW76719Wooorx",
        scenarioName = "bdm-商家详情页活动任务详情",
        author = "lipan@hungrypandagroup.com", developmentTime = 30,
        maintenanceTime = 0 ,
        manualTestTime = 5
)
public class ShopTaskTests {

    @BeforeAll
    static void beforeAll() {
        // 测试前置条件
        ERPLoginFlow.loginByDefaultUser();
    }


    @MethodSource("com.miller.bdm.app.shopTask.provider.ShopTaskProvider#ShopTagList")
    @ParameterizedTest
    @DisplayName("bdm-商家详情页任务详情")

    void ShowTask(ShopTaskRequestDTO shopTaskRequestDTO) {
        ShopTaskResponseDTO responseDTO = ShopTaskFlow.getShowTask(shopTaskRequestDTO);
        assertThat(responseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
        CacheUtils.set(BusinessConstantOfERP.SHOP_TAG_KEY, responseDTO);
    }



}

