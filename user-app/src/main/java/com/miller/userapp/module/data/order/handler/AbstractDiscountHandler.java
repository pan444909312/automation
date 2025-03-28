package com.miller.userapp.module.data.order.handler;


import com.hungrypanda.app.server.entity.order.OrderDiscountEntity;
import com.hungrypanda.common.enums.discount.DiscountTypeEnum;


public abstract class AbstractDiscountHandler {

    /**
     * 构建优惠项
     *
     * @param discountTypeEnum
     * @return
     */
    protected OrderDiscountEntity buildOrderDiscountEntity(DiscountTypeEnum discountTypeEnum, Integer discountAmount) {
        OrderDiscountEntity orderDiscountEntity = new OrderDiscountEntity();
        orderDiscountEntity.setDiscountType(discountTypeEnum.getCode());
        orderDiscountEntity.setDiscountName(discountTypeEnum.getName());
        orderDiscountEntity.setOrgDiscountAmount(discountAmount);
        orderDiscountEntity.setDiscountAmount(discountAmount);
        return orderDiscountEntity;
    }


}
