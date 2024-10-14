package com.miller.market.order.orderDetail;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.mapper.order.OrderMapper;
import com.miller.market.order.orderDetail.flow.MarketOrderDetailFlow;
import com.miller.market.order.orderDetail.request.MarketOrderDetailRequestDTO;
import com.miller.market.order.orderDetail.response.MarketOrderDetailResponseDTO;
import com.miller.market.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.market.dal.entity.Order;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 订单详情
 */
@EnvTag.Test
@TestFramework
@DisplayName("订单详情")
public class MarketOrderDetailTests {
    private static OrderMapper orderMapper;
    @BeforeAll
    static void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfFreshTest();
        orderMapper = sqlSession.getMapper(OrderMapper.class);

    }
    @MethodSource("staticOrderDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_已登录_获取订单详情")
    void getOrderDetailSuccessfully(MarketOrderDetailRequestDTO marketOrderDetailRequestDTO) {
        MarketOrderDetailResponseDTO marketOrderDetailResponseDTO = MarketOrderDetailFlow.orderDetail(marketOrderDetailRequestDTO);

        Assertions.assertThat(marketOrderDetailResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketOrderDetailResponseDTO.getData()).isNotNull();
        Assertions.assertThat(marketOrderDetailResponseDTO.getData().getOrderId()).isEqualTo(marketOrderDetailRequestDTO.getOrderId());

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticOrderDataProvider() {
        MarketOrderDetailRequestDTO requestDTO = new MarketOrderDetailRequestDTO();
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("portal_id", BusinessConstant.portalId);
        orderQueryWrapper.eq("user_id", BusinessConstant.userId);
        orderQueryWrapper.orderByDesc("order_id");
        orderQueryWrapper.last("limit 1");
        Order order = orderMapper.selectOne(orderQueryWrapper);
        requestDTO.setOrderId(order.getOrderId());

        return Stream.of(Arguments.of(requestDTO));
    }

}
