package com.miller.bdm.app.queryContractList;

import com.miller.bdm.app.queryContractList.flow.QueryContractListFlow;
import com.miller.bdm.app.queryContractList.request.QueryContractListRequestDTO;
import com.miller.bdm.app.queryContractList.respones.QueryContractListResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.bdm.login.flow.ERPLoginFlow;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@DisplayName("bdm-商家详情页合同列表")


@Scenario(
        scenarioID="01J5N1X0M0JW7u6719ooorx",
        scenarioName = "bdm-商家详情页合同列表",
        developmentTime = 30,
        maintenanceTime = 0 ,
        manualTestTime = 5
)
public class QueryContractListTests {

    @BeforeAll
    static void beforeAll() {
        // 测试前置条件
        ERPLoginFlow.loginByDefaultUser();
    }


    @MethodSource("com.miller.bdm.app.queryContractList.provider.QueryContractListProvider#ShopTagList")
    @ParameterizedTest
    @DisplayName("bdm-商家详情页合同列表")

    void queryContractList(QueryContractListRequestDTO queryContractListRequestDTO) {
        QueryContractListResponseDTO responseDTO = QueryContractListFlow.getShowTask(queryContractListRequestDTO);
        assertThat(responseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
        CacheUtils.set(BusinessConstantOfERP.SHOP_TAG_KEY, responseDTO);
    }



}

