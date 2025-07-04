package com.miller.market.specialOrder.getPageList;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.specialOrder.getPageList.flow.MarketSpecialOrderListFlow;
import com.miller.market.specialOrder.getPageList.request.MarketSpecialOrderListRequestDTO;
import com.miller.market.specialOrder.getPageList.response.MarketSpecialOrderListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 特殊单订单列表
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_特殊单订单列表")
public class MarketSpecialOrderListlTests {
    @MethodSource("staticOrderDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_已登录_获取特殊单订单列表")
    void getOrderDetailSuccessfully(MarketSpecialOrderListRequestDTO requestDTO) {
        MarketSpecialOrderListResponseDTO responseDTO = MarketSpecialOrderListFlow.specialOderList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData().getRecords().get(0).getSpecialOrderId()).isNotNull();
        BusinessConstant.specialOrderId = responseDTO.getData().getRecords().get(0).getSpecialOrderId();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticOrderDataProvider() {
        MarketSpecialOrderListRequestDTO requestDTO = new MarketSpecialOrderListRequestDTO();
        Page page = new Page();
        page.setCurrent(1L);
        page.setSize(20L);
        requestDTO.setPage(page);

        return Stream.of(Arguments.of(requestDTO));
    }

}
