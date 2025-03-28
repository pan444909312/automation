package com.miller.userapp.module.data.order.handler;

import com.hungrypanda.app.server.common.enums.LanguageEnum;
import com.hungrypanda.app.server.dto.shop.ShopDTO;
import com.miller.userapp.module.data.order.entity.CalculateOrderBasicData;
import com.miller.userapp.module.data.order.entity.OrderCountInfoEx;

import java.util.Objects;

public class MemberPlatformFeeDiscountHandler extends AbstractDiscountHandler{
    public void getShopFirstDiscount(CalculateOrderBasicData ov, OrderCountInfoEx countInfo){
        String language = ov.getHeader().get("language").toString();
        //会员目前只支持中文版
        if(!language.equalsIgnoreCase(LanguageEnum.CN.getKey())) return;
        ShopDTO shop = ov.getShop();
        Integer platformFee = countInfo.getPlatformFee();
        if (Objects.isNull(platformFee) || platformFee == 0) {
            //平台服务为0,不处理
            return;
        }
        if(shop.checkIsMeal()) return;

    }

}
