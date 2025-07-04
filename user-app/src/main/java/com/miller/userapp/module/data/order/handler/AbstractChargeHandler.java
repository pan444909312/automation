package com.miller.userapp.module.data.order.handler;

import com.hungrypanda.app.server.dto.order.OrderChargeDetailDTO;
import com.hungrypanda.app.server.dto.shop.ShopDTO;
import com.panda.user.order.server.api.enums.OrderChargeTypeEnum;

import java.util.Objects;

public abstract class AbstractChargeHandler {
    /**
     * 构建收费项
     *
     * @param chargeTypeEnum
     * @param chargePrice
     * @param shop
     * @return
     */
    protected OrderChargeDetailDTO buildOrderChargeDetailDTO(OrderChargeTypeEnum chargeTypeEnum, Integer chargePrice, ShopDTO shop) {
        OrderChargeDetailDTO orderChargeDetail = new OrderChargeDetailDTO()
                .setOrderChargeType(chargeTypeEnum.getCode())
                .setChargeName(chargeTypeEnum.getName())
                .setChargePrice(chargePrice)
                .setChargeId(0L)
                .setIsMergeShow(0);

        if (Objects.nonNull(shop)) {
            orderChargeDetail.setShopId(shop.getShopId());
        }
        return orderChargeDetail;
    }
}
