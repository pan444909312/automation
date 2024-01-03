package com.miller.merchant.order.waiting.lack.provider;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataConstant;
import com.miller.merchant.order.details.flow.OrderDetailsFlow;
import com.miller.merchant.order.details.request.OrderDetailsRequestDTO;
import com.miller.merchant.order.details.response.OrderDetailsResponseDTO;
import com.miller.merchant.order.waiting.lack.request.OrderLackProductRequestDTO;
import com.miller.service.framework.cache.CacheUtils;
import com.panda.merchant.server.api.vo.app.merchant.resp.ProductInfoRespDTO;
import com.panda.merchant.server.api.vo.app.merchant.resp.ProductRespDTO;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.StringJoiner;
import java.util.stream.Stream;

/**
 * 数据提供者-缺菜-退菜
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 16:44:33
 */
@SuppressWarnings("unused")
public class OrderLackProductDataProvider {
    /**
     * 缺菜-退菜-下架1小时
     */
    static Stream<Arguments> orderLackProduct() {
        OrderLackProductRequestDTO orderLackProductRequestDTO = getOrderLackProductRequestDTO(0);
        return Stream.of(Arguments.of(orderLackProductRequestDTO));
    }
    /**
     * 缺菜-换菜-下架1小时-退款x金额
     */
    static Stream<Arguments> orderChangeMenu() {
        OrderLackProductRequestDTO orderLackProductRequestDTO = getOrderLackProductRequestDTO(1);
        // 换菜可能会涉及到部分菜的金额退款
        orderLackProductRequestDTO.setReturnAmount(BigDecimal.ONE);

        return Stream.of(Arguments.of(orderLackProductRequestDTO));
    }

    /**
     * 商家退菜或换菜
     * @param operateType 操作类型，0-退菜 1-换菜
     * @return OrderLackProductRequestDTO
     */
    @NotNull
    private static OrderLackProductRequestDTO getOrderLackProductRequestDTO(Integer operateType) {
        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();

        OrderDetailsRequestDTO orderDetailsRequestDTO = new OrderDetailsRequestDTO();
        orderDetailsRequestDTO.setOrderSn(orderSn);
        OrderDetailsResponseDTO orderDetails = OrderDetailsFlow.getOrderDetails(orderDetailsRequestDTO);

        OrderLackProductRequestDTO orderLackProductRequestDTO = new OrderLackProductRequestDTO();
        orderLackProductRequestDTO.setOrderSn(orderSn);

        // 退单可能存在多个商品。多个之间用都好分割，商品和数量之间用#好分割。比如：退2个商品，没个退1件。id1, id2 # 数量1, 数量2
        StringJoiner orderDetailId = new StringJoiner(",");
        StringJoiner productCount = new StringJoiner(",");
        for (ProductRespDTO productRespDTO : orderDetails.getResult().getOrderProductData()) {
            for (ProductInfoRespDTO productInfoRespDTO : productRespDTO.getProductInfoList()) {
                orderDetailId.add(productInfoRespDTO.getOrderDetailId().toString());
                productCount.add(productInfoRespDTO.getProductCount().toString());
            }
        }
        // 订单详情id+商品缺菜数量（用"#"隔开）
        String orderDetailIdsAndNum = orderDetailId + "#" + productCount;
        orderLackProductRequestDTO.setOrderDetailIdsAndNum(orderDetailIdsAndNum);
        orderLackProductRequestDTO.setOperateType(operateType);
        orderLackProductRequestDTO.setProductStatus(1);
        orderLackProductRequestDTO.setTimeMode(1);
        return orderLackProductRequestDTO;
    }
}
