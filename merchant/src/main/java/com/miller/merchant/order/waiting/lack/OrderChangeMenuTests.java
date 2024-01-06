package com.miller.merchant.order.waiting.lack;

import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.manage.product.flow.ProductOnOrOffFlow;
import com.miller.merchant.manage.product.request.ProductOnOrOffRequestDTO;
import com.miller.merchant.order.waiting.lack.flow.OrderLackProductFlow;
import com.miller.merchant.order.waiting.lack.request.OrderLackProductRequestDTO;
import com.miller.merchant.order.waiting.lack.response.OrderLackProductResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_商家-待接单-缺菜-换菜-下架一小时并退款x金额
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/3 10:37:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-待接单-缺菜-换菜-下架一小时并退款x金额")
public class OrderChangeMenuTests {

    /**
     * 上架商品。每个测试用例执行完毕后，都要执行该方法
     */
    @AfterEach
    void afterEach() {
        ProductOnOrOffRequestDTO productOnOrOffRequestDTO = new ProductOnOrOffRequestDTO();
        productOnOrOffRequestDTO.setProductIds(List.of(TestCaseDataForMerchantConstant.productId));
        productOnOrOffRequestDTO.setStatus(0);
        ProductOnOrOffFlow.productOnOrOff(productOnOrOffRequestDTO);
    }

    @MethodSource("com.miller.merchant.order.waiting.lack.provider.OrderLackProductDataProvider#orderChangeMenu")
    @ParameterizedTest
    @DisplayName("商家-待接单-缺菜-换菜-下架一小时")
    void shouldLackProductSuccessfully(OrderLackProductRequestDTO orderLackProductRequestDTO) {
        OrderLackProductResponseDTO orderLackProductResponseDTO = OrderLackProductFlow.orderLackProduct(orderLackProductRequestDTO);
        assertThat(orderLackProductResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(orderLackProductResponseDTO.getSuccess()).isTrue();
        // TODO 订单数据校验
    }
}
