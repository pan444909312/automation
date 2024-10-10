package com.miller.userapp.module.order.shopping.settlement.flow;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.api.res.order.OrderAmountVO;
import com.hungrypanda.app.server.common.consants.OrderVirtualConstants;
import com.hungrypanda.app.server.common.enums.order.OrderAmountTypeEnum;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 流程_结算页(由于历史原因，也叫创建虚单)
 * 注意: 购物车和结算页面仅仅只是用于计算商品价格、优惠等信息，并不会真正的入库，所以在主流中可以不用加购物车和结算的逻辑，这块可以单独测试。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/10 15:15:30
 */
public class SettlementFlow {
    /**
     * 接口_结算页
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/v1/order/toCreateVirtual";

    /**
     * 流程_结算
     */
    public static SettlementResponseDTO settlementProduct(SettlementRequestDTO settlementRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(settlementRequestDTO), null, SettlementResponseDTO.class);
    }

    /**
     * 查询结算页订单总金额，结算页计算的金额
     *
     * @param orderType       订单类型, 不同类型价格计算方式不同，可使用枚举值 {@code OrderReqTypeEnum.COMMON_ORDER}
     * @param deliveryType    配送方式, 不同类型价格计算方式不同，可使用枚举值 {@code DeliveryTypeEnum}
     * @param shopId          店铺ID
     * @param productCartList 商品信息字符串，比如："[{\"skuId\":0,\"productId\":81669204}]"
     * @return 总金额
     */
    public static Integer queryOrderPriceFormSettlementPage(Integer orderType, Integer deliveryType, Long shopId, List<ProductCart> productCartList) {
        // 计算订单金额，只需要传3个字段， 订单类型、店铺ID、商品信息
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        // 订单类型
        settlementRequestDTO.setOrderType(orderType);
        // 配送方式
        settlementRequestDTO.setDeliveryType(deliveryType);
        // 店铺ID
        settlementRequestDTO.setShopId(shopId);
        // 商品信息
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return settlementProduct(settlementRequestDTO).getResult().getPriceInfo().getTotalAmount();
    }


    /**
     * 获取结算页收费明细的键值对、结算页订单总金额
     * <p>
     * 注意: 枚举{@link OrderAmountTypeEnum}名称与接口返回名称{@link OrderVirtualConstants}不是对应的关系，所以不能用枚举名称。
     * 优先使用常量名称{@link OrderVirtualConstants}获取key，这样基本能与接口字段对应上，但是可能有些接口返回的字段名称不再常量中，则使用枚举值的名称转化为小写。
     *
     * @param settlementResponseDTO 结算页返回的订单信息
     * @return 结算页收费明细的键值对， key 通过 {@link OrderVirtualConstants}当作key可以进行获取，参考{@link OrderAmountTypeEnum}
     */
    public static HashMap<String, Integer> getSettlementDetailFee(SettlementResponseDTO settlementResponseDTO) {
        var settlementTotalFee = new HashMap<String, Integer>();
/*
        // 商品小记费
        Integer product = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("product"))
                .findFirst().map(OrderAmountVO::getItemAmount).orElse(0);
        settlementTotalFee.put(OrderAmountTypeEnum.PRODUCT, product);

        // 打包费
        Integer packaging = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("packaging"))
                .findFirst().map(OrderAmountVO::getItemAmount).orElse(0);
        settlementTotalFee.put(OrderAmountTypeEnum.PACKING_FEE, packaging);

        // 配送费折扣价 = 配送费（delivery）-VIP配送优惠金额（memberDeliveryDiscount）
        Integer discountDeliveryResult = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("discountDelivery"))
                .findFirst().map(OrderAmountVO::getItemAmount).orElse(0);
        settlementTotalFee.put(OrderAmountTypeEnum.DISCOUNT_DELIVERY_FEE, discountDeliveryResult);

        // 配送折扣
        Integer discountDelivery = 0;
        Optional<OrderAmountVO> discountDelivery1 = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("discountDelivery"))
                .findFirst();
        if (discountDelivery1.isPresent()) {
            discountDelivery = discountDelivery1.get()
                    .getMergeList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("delivery"))
                    .findFirst()
                    .map(OrderAmountVO::getItemAmount).orElse(0);
        }
        settlementTotalFee.put(OrderAmountTypeEnum.DELIVERY_FEE, discountDelivery);

        // VIP配送优惠金额
        Integer memberDeliveryDiscount = 0;
        Optional<OrderAmountVO> discountDelivery2 = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("discountDelivery"))
                .findFirst();
        if (discountDelivery2.isPresent()) {
            memberDeliveryDiscount = discountDelivery2.get().getMergeList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("memberDeliveryDiscount"))
                    .findFirst()
                    .map(OrderAmountVO::getItemAmount).orElse(0);
        }
        settlementTotalFee.put(OrderAmountTypeEnum.MEMBER_DELIVERY_DISCOUNT, memberDeliveryDiscount);

        // 校验配送费折扣价
        assertThat(discountDeliveryResult).isEqualTo(discountDelivery - memberDeliveryDiscount);

        // 新增收费项cn
        Integer deliveryAddFee622 = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("DELIVERY_ADD_FEE622"))
                .findFirst().map(OrderAmountVO::getItemAmount).orElse(0);
        settlementTotalFee.put(OrderAmountTypeEnum.DELIVERY_ADD_FEE, deliveryAddFee622);

        // 红包优惠
        Integer redPacketDiscount = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("redPacketDiscount"))
                .findFirst().map(OrderAmountVO::getItemAmount).orElse(0);
        settlementTotalFee.put(OrderAmountTypeEnum.RED_PACKET, redPacketDiscount);

        // 开通会员费用
        Integer buyMember = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("buyMember"))
                .findFirst().map(OrderAmountVO::getItemAmount).orElse(0);
        settlementTotalFee.put(OrderAmountTypeEnum.BUY_MEMBER, buyMember);

        // 配送费
        Integer delivery = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("delivery"))
                .findFirst().map(OrderAmountVO::getItemAmount).orElse(0);
        settlementTotalFee.put(OrderAmountTypeEnum.DELIVERY_FEE, delivery);
*/
        // 获取结算页所有收费项
        settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().forEach(
                item -> settlementTotalFee.put(item.getItemKey(), item.getItemAmount())
        );
        return settlementTotalFee;
    }


}
