package com.miller.userapp.pay.balance;

import com.hungrypanda.app.server.vo.order.OrderListVO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.erp.login.flow.ERPLoginFlow;
import com.miller.erp.manage.customer.order.list.flow.OrderDetailOfVoucherFlow;
import com.miller.erp.manage.customer.order.list.flow.response.OrderDetailResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.order.list.flow.OrderListFlow;
import com.miller.userapp.order.list.response.OrderListResponseDTO;
import com.miller.userapp.pay.balance.flow.PayByBalanceFlow;
import com.miller.userapp.pay.balance.request.PayByBalanceRequestDTO;
import com.miller.userapp.pay.balance.response.PayByBalanceResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_余额支付-代金券合单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/24 20:47:37
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-支付-余额支付-代金券合单")
public class PayByBalanceWithVoucherTests {

    @MethodSource("com.miller.userapp.pay.balance.provider.PayByBalanceDataProvider#payByBalanceWithCombined")
    @ParameterizedTest
    @DisplayName("正常流程_余额支付-代金券合单")
    void shouldPayByBalanceWithMemberSuccessfully(PayByBalanceRequestDTO payByBalanceWithMemberRequestDTO) {
        PayByBalanceResponseDTO payByBalanceResponseDTO = PayByBalanceFlow.payByBalance(payByBalanceWithMemberRequestDTO);
        assertThat(payByBalanceResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(payByBalanceResponseDTO.getSuccess()).isTrue();
        // 代金券购买之后使用就核销了，所以无需清除购买的代金券记录
        // 获取合单 ID
        // String orderSn = payByBalanceWithMemberRequestDTO.getOrderSn();
    }

    @AfterEach
    void afterEach() {
        // 校验订单列表中存在对应代金券订单。由于普通订单接口没有返回对应的代金券订单ID，代金券订单里面也没有返回对应普通订单的ID，
        // 所以只能通过查询DB或ERP后台的方式获取订单对应的代金券ID。

        // 从缓存中获取订单ID
        var orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();

        // 通过ERP后台查询订单详情
        ERPLoginFlow.loginByDefaultUser();
        OrderDetailResponseDTO voucherByOrderId = OrderDetailOfVoucherFlow.getVoucherByOrderId(orderSn);

        // 获取所有代金券ID
        var voucherIds = new ArrayList<String>();
        voucherByOrderId.getData().getRowList().forEach(row -> voucherIds.add(String.valueOf(row.get(0))));

        OrderListResponseDTO allOrder = OrderListFlow.getAllOrder();
        List<OrderListVO> historyOrderList = allOrder.getResult().getHistoryOrderList();
        for (int i = 0; i < historyOrderList.size(); i++) {
            OrderListVO orderListVO = historyOrderList.get(i);
            String voucherOrderSn = orderListVO.getVoucherOrderSn();
            if (voucherIds.contains(voucherOrderSn)) {
                break;
            }
            if (i == historyOrderList.size() - 1) {
                Assertions.fail("订单列表中不存在代金券购买的订单");
            }
        }
    }
}
