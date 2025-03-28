package com.miller.userapp.module.data.order.handler;

import com.hungrypanda.app.server.common.consants.CommonConstants;
import com.hungrypanda.app.server.common.enums.CountryInfoEnum;
import com.hungrypanda.app.server.common.enums.DeliveryTypeEnum;
import com.hungrypanda.app.server.common.enums.EntryInfoEnum;
import com.hungrypanda.app.server.common.enums.order.OrderActivityTypeEnum;
import com.hungrypanda.app.server.common.utils.PriceUtil;
import com.hungrypanda.app.server.common.utils.RequestUtils;
import com.hungrypanda.app.server.common.utils.TranslateUtils;
import com.hungrypanda.app.server.dto.shop.ShopDTO;
import com.hungrypanda.app.server.entity.shop.ShopExtraInfoEntity;
import com.hungrypanda.common.enums.system.SysAppConfigEnum;
import com.miller.userapp.module.data.basic.db.ShopExtraInfoSql;
import com.miller.userapp.module.data.basic.service.SysAppConfigService;
import com.miller.userapp.module.data.order.entity.CalculateOrderBasicData;
import com.miller.userapp.module.data.order.entity.OrderCountInfoEx;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SmallOrderFeeChargeHandler extends AbstractChargeHandler{
    private SysAppConfigService sysAppConfigService;
    private ShopExtraInfoSql shopExtraInfoSql;
    private boolean openSmallOrderFeeExcludeDiscount = false;
    public SmallOrderFeeChargeHandler(SqlSession sqlSession,boolean openSmallOrderFeeExcludeDiscount){
        sysAppConfigService = new SysAppConfigService(sqlSession);
        shopExtraInfoSql = new ShopExtraInfoSql(sqlSession);
        this.openSmallOrderFeeExcludeDiscount = openSmallOrderFeeExcludeDiscount;
    }
    public void getSmallOrderFeeChargePrice(CalculateOrderBasicData calculateOrderBasicData, OrderCountInfoEx countInfo){
        //初始化金额
        int smallOrderFee = 0;
        ShopDTO shopDTO = calculateOrderBasicData.getShop();
        if(Objects.isNull(shopDTO)) return ;
        if(!OrderActivityTypeEnum.needCheck(calculateOrderBasicData.getActivityCode())){
            //判断不计算小额订单费城市
            String value = sysAppConfigService.getSysAppConfigByKey(SysAppConfigEnum.PRODUCT_INVITE_NO_SMALL_FEE_CITY.name());
            if (StringUtils.isNotBlank(value)){
                //全部不计算
                if (CommonConstants.SYS_CONFIG_CITY_ALL.equals(value)){
                    return ;
                }
                List<String> list = Arrays.asList(value.split(",|，"));
                if (list.contains(shopDTO.getAddCity())){
                    return ;
                }
            }
        }
        ShopExtraInfoEntity shopExtraInfo = shopExtraInfoSql.getShopExtraInfo(shopDTO.getShopId());
        if(Objects.isNull(shopExtraInfo)) return ;
        // 小额订单非自取门槛
        Integer threshold = shopExtraInfo.getMinOrderDeliveryThreshold();
        // 小额订单非自取上限
        Integer limit = shopExtraInfo.getMinOrderDeliveryUplimit();
        String deliveryType = calculateOrderBasicData.getDeliveryType();
        //自提订单处理
        if (StringUtils.isNotEmpty(deliveryType) && Integer.parseInt(deliveryType) == DeliveryTypeEnum.USER_COLLECT.getCode()) {
            threshold = shopExtraInfo.getMinOrderSelfThreshold();
            limit = shopExtraInfo.getMinOrderSelfUplimit();
        }

        //无配置限制
        if (Objects.isNull(threshold) || threshold <= 0 || Objects.isNull(limit) || limit <= 0) {
            return ;
        }
        int condition;
        //小额订单费 = 小额订单门槛 - （商品售价-满减金额-红包优惠（不含运费红包）-首单优惠-门店首单-自取折扣-代金券优惠）
        if (Boolean.TRUE.equals(this.openSmallOrderFeeExcludeDiscount)) {
            //目前仅加拿大此项配置为true
            condition = countInfo.getTotalPrice() - countInfo.getSubDiscount() - countInfo.getRedPacketPrice() - countInfo.getFirstDiscount()
                    - countInfo.getShopFirstDiscount() - countInfo.getTakeDiscountPrice() - countInfo.getVoucherDiscount();
        } else {
            // 其他国家计算金额  小额订单费=小额订单门槛 -（商品售价+打包费+平台打包费+餐具费）
            condition = countInfo.getTotalPrice() + countInfo.getPackingCharges() + countInfo.getPlatformPacking() + countInfo.getTablewarePrice();
        }

        if (condition >= threshold) {
            return;
        }
        smallOrderFee = Math.min(threshold - condition, limit);
        calculateOrderBasicData.setSmallOrderFee(smallOrderFee);
        calculateOrderBasicData.setSmallOrderFeeConfig(threshold);
        countInfo.setSmallOrderFee(smallOrderFee);
        String countryCode = calculateOrderBasicData.getHeader().get("countryCode").toString();
        calculateOrderBasicData.setSmallOrderFeeInfo(buildSmallOrderFeeInfo(threshold, countryCode));

//        OrderChargeDetailDTO orderChargeDetail = buildOrderChargeDetailDTO(OrderChargeTypeEnum.SMALL_ORDER_FEE, smallOrderFee, shopDTO);
    }
    public String buildSmallOrderFeeInfo(Integer threshold,String countryCode) {
        if (threshold == null || threshold <= 0) {
            return "";
        }
        CountryInfoEnum countryInfoEnum = CountryInfoEnum.getEnumByCountryCode(countryCode);
        String price = countryInfoEnum != null ? countryInfoEnum.getSymbol() : "" + PriceUtil.stripTailZerosPrice(BigDecimal.valueOf(threshold));
        return TranslateUtils.trans(EntryInfoEnum.SMALL_ORDER_INFO).replace("#num#", price);
    }
}
