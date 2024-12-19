package com.miller.market.order.orderList;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.order.orderList.flow.MarketOrderListFlow;
import com.miller.market.order.orderList.request.MarketOrderListlRequestDTO;
import com.miller.market.order.orderList.response.MarketOrderListlResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 订单列表
 */
@EnvTag.Test
@TestFramework
@DisplayName("订单列表")
public class MarketOrderListTests {
    @MethodSource("staticOrderDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_已登录_获取PF订单列表：全部-待支付-进行中-已完成")
    void getOrderDetailSuccessfully(MarketOrderListlRequestDTO requestDTO) {
        //查询全部订单
        MarketOrderListlResponseDTO responseDTO = MarketOrderListFlow.orderList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();
        //查询待支付的订单
        requestDTO.setType(1);
        responseDTO = MarketOrderListFlow.orderList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();
        //查询进行中的订单
        requestDTO.setType(2);
        responseDTO = MarketOrderListFlow.orderList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();
        //查询已完成的订单
        requestDTO.setType(3);
        responseDTO = MarketOrderListFlow.orderList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticOrderDataProvider() {
        MarketOrderListlRequestDTO requestDTO = new MarketOrderListlRequestDTO();
        requestDTO.setType(0);
        requestDTO.setIshpf(false);
        Page page = new Page<>();
        page.setSize(10);
        page.setCurrent(1);
        requestDTO.setPage(page);
        return Stream.of(Arguments.of(requestDTO));
    }

}
