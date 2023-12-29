package com.miller.userapp.order.refund.submit.provider;

import com.hungrypanda.app.server.vo.order.RefundDetailVO;
import com.hungrypanda.app.server.vo.order.req.RefundProductReq;
import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataConstant;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.order.refund.apply.flow.ApplyRefundFlow;
import com.miller.userapp.order.refund.apply.request.ApplyRefundRequestDTO;
import com.miller.userapp.order.refund.apply.response.ApplyRefundResponseDTO;
import com.miller.userapp.order.refund.submit.request.SubmitRefundRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 数据提供者-申请退款-提交
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/29 11:24:33
 */
@SuppressWarnings("unused")
public class SubmitRefundDataProvider {

    /**
     * 申请退款提交接口的数据提供者来自于申请退款接口，前置条件就是需要先调用申请退款之后才会有数据
     */
    static Stream<Arguments> submitRefund() {
        SubmitRefundRequestDTO submitRefundRequestDTO = new SubmitRefundRequestDTO();
        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();

        // 提交退款申请之前需要先发起退款申请，然后从申请的响应结果中获取数据
        ApplyRefundRequestDTO applyRefundRequestDTO = new ApplyRefundRequestDTO();
        applyRefundRequestDTO.setOrderSn(orderSn);
        applyRefundRequestDTO.setOrderType(1);
        ApplyRefundResponseDTO applyRefundResponseDTO = ApplyRefundFlow.applyRefund(applyRefundRequestDTO);

        // 构造提交退款请求数据
        submitRefundRequestDTO.setOrderType(1);
        submitRefundRequestDTO.setRefundType(1);
        submitRefundRequestDTO.setSupplementReason("【自动化测试】用户发起退款申请，并提交");
        submitRefundRequestDTO.setOrderSn(orderSn);
        // 退款原因ID: 7-其他原因
        submitRefundRequestDTO.setRefundReasonId("7");
        // 退款默认图片
        submitRefundRequestDTO.setRefundReasonImg("https://static.hungrypanda.co/panda/170382959698093da98e35372419ca8b19d4fd2367057.jpg");

        // 退款金额，数据来自退款申请接口
        submitRefundRequestDTO.setRefundAmount(applyRefundResponseDTO.getResult().getRefundAmount());

        // 申请退款中的商品列表，数据来自退款申请接口的商品列表
        List<RefundProductReq.RefundProductInfo> refundGoodsList = new ArrayList<>();
        // 获取退款商品列表的退款ID、商品数量
        List<RefundDetailVO.RefundProduct> refundProductList = applyRefundResponseDTO.getResult().getProductList();
        for (RefundDetailVO.RefundProduct refundProduct : refundProductList) {
            String refundId = refundProduct.getRefundId();
            Integer productCount = refundProduct.getProductCount();
            // 构造每一个需要退款的商品请求字段
            RefundProductReq.RefundProductInfo refundProductInfo = new RefundProductReq.RefundProductInfo();
            refundProductInfo.setRefundId(refundId);
            refundProductInfo.setRefundCount(productCount);
            // 将退款ID、商品数量 放入到申请退款提交接口的请求字段 refundGoodsList 中
            refundGoodsList.add(refundProductInfo);
        }
        submitRefundRequestDTO.setRefundGoodsList(refundGoodsList);

        return Stream.of(Arguments.of(submitRefundRequestDTO));
    }

}
