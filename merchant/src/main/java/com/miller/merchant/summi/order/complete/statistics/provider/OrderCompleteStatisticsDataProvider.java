package com.miller.merchant.summi.order.complete.statistics.provider;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.summi.order.complete.statistics.request.OrderCompleteStatisticsRequestDTO;
import com.miller.merchant.util.RequestUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 订单列表数据提供者
 *
 * @author yuwei
 * @version 1.0
 * @since 2024/04/24 15:45:30
 */
@SuppressWarnings("unused")
public class OrderCompleteStatisticsDataProvider {
    /**
     * 订单列表数据提供者
     *
     * @return Stream<Arguments>
     */
    static Stream<Arguments> orderListOfStatus() {
        // TODO 从数据库中获取数据
        OrderCompleteStatisticsRequestDTO orderCompleteStatisticsRequestDTO = new OrderCompleteStatisticsRequestDTO();
        // 这个请求体里面的tokens是从登录返回的字段获取的
        var tokens = RequestUtils.getHeaders().get(BusinessConstant.authorization).toString();
        orderCompleteStatisticsRequestDTO.setTokens(tokens);
        orderCompleteStatisticsRequestDTO.setAllLoginToken(tokens);
        /*
         * 订单状态: 5（已完成订单）
         */
        orderCompleteStatisticsRequestDTO.setMerchantOrderStatus(5);
        /*
         * 查询可结算订单日期
         */
        orderCompleteStatisticsRequestDTO.setQueryDate("2024-04-16");

        return Stream.of(Arguments.of(orderCompleteStatisticsRequestDTO));
    }
}
