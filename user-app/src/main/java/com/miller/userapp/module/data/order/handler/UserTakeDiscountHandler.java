package com.miller.userapp.module.data.order.handler;

import com.hungrypanda.app.server.common.enums.DeliveryTypeEnum;
import com.hungrypanda.app.server.common.utils.ConvertUtils;
import com.hungrypanda.app.server.common.utils.PriceUtil;
import com.hungrypanda.app.server.dto.shop.ShopDTO;
import com.hungrypanda.common.enums.discount.DiscountTypeEnum;
import com.miller.userapp.module.data.order.entity.CalculateOrderBasicData;
import com.miller.userapp.module.data.order.entity.OrderCountInfoEx;
import org.apache.commons.lang3.StringUtils;

public class UserTakeDiscountHandler extends AbstractDiscountHandler{
    public void getShopFirstDiscount(CalculateOrderBasicData ov, OrderCountInfoEx countInfo){
        ShopDTO shop = ov.getShop();
        String deliveryType = ov.getDeliveryType();
        // 如果店铺支持自取并且折扣不为空且当前是自取订单
        if (StringUtils.isNotEmpty(deliveryType) && Integer.parseInt(deliveryType) == DeliveryTypeEnum.USER_COLLECT.getCode() &&
                shop.getIsUserPack() == 1 && shop.getDiscountOn() != null && shop.getDiscountOn() == 1 &&
                shop.getDiscountRate() != null &&
                shop.getDiscountRate() != 0) {

            int discountNum = countInfo.getTakeSelfNeedAmount() - ConvertUtils.transferDataForInt(countInfo.getTakeSelfNeedAmount() * (shop.getDiscountRate() / 10));
            discountNum = Math.max(discountNum, 0);
            if (shop.getDiscountLimit() != null && shop.getDiscountLimit() != 0 && shop.getDiscountLimit() < discountNum) {
                discountNum = shop.getDiscountLimit();
            }
            ov.setTakeDiscountPriceStr(PriceUtil.getFixedPriceStr(discountNum, shop.getCountry()));
            ov.setTakeDiscountPriceInt(discountNum);
            if (shop.getShopBearPercent() != null) {
                ov.setUserTakeShopDiscountAmount(discountNum * shop.getShopBearPercent() / 100);
            }
            if(discountNum > 0){
                countInfo.setTakeDiscountPrice(discountNum);
//                buildOrderDiscountEntity(DiscountTypeEnum.TAKE_SELF_DISCOUNT, discountNum);
            }
        }

    }
}
