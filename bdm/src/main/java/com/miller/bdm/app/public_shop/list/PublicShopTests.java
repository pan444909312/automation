package com.miller.bdm.app.public_shop.list;

import com.miller.bdm.app.public_shop.list.flow.PublicShopFlow;
import com.miller.bdm.app.public_shop.list.request.PublicShopRequestDTO;


import com.miller.bdm.app.public_shop.list.response.PublicShopResponseDTO;
import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.common.base.BasePageResponse;
import com.panda.common.base.Result;
import com.panda.erp.server.dal.dto.bdm.shop.OpportunityShopMobilePageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_客户服务-退款审核-根据订单查询特殊单ID
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 11:11:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("bdm-移动端公海池-公海池列表")
public class PublicShopTests {
    @MethodSource("com.miller.bdm.app.public_shop.list.provider.PublicShopDataProvider#PublicShopList")
    @ParameterizedTest
    @DisplayName("bdm-移动端公海池-公海池列表")
    void shouldQueryRefundListSuccessfully(PublicShopRequestDTO publicShopRequestDTO) {
        PublicShopResponseDTO publicShopResponseDTO = PublicShopFlow.queryRefundList(publicShopRequestDTO);
        assertThat(publicShopResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        assertThat(publicShopResponseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
    }

}
