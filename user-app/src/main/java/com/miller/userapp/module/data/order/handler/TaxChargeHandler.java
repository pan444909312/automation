package com.miller.userapp.module.data.order.handler;

import com.hungrypanda.app.server.dto.order.OrderChargeDetailDTO;
import com.hungrypanda.app.server.dto.shop.ShopDTO;
import com.miller.userapp.module.data.order.entity.CalculateOrderBasicData;
import com.miller.userapp.module.data.order.entity.OrderCountInfoEx;
import com.miller.userapp.module.data.order.tax.*;
import com.panda.user.order.server.api.enums.OrderChargeTypeEnum;

import java.util.Objects;

public class TaxChargeHandler extends AbstractChargeHandler {
    /**
     * 消费税加拿大规则:canadaRule，最新消费税规则:newTaxRule，默认消费税规则:defaultRule
     */
    private String taxRuleType = "defaultRule";
    public TaxChargeHandler(String taxRuleType){
        this.taxRuleType = taxRuleType;
    }
    /**
     * 不支持第三方消费税
     * @param calculateOrderBasicData
     * @param countInfo
     */
    public void getTaxCharge(CalculateOrderBasicData calculateOrderBasicData, OrderCountInfoEx countInfo){
        ShopDTO shop = calculateOrderBasicData.getShop();
        TaxService taxService = getTaxService(taxRuleType);
        taxService.computeTaxPrice(calculateOrderBasicData,countInfo);
        Integer taxPrice = countInfo.getTaxPrice();
        if (Objects.isNull(taxPrice) || taxPrice <= 0) {
            //不存在消费税时，直接结束
            return;
        }
//        OrderChargeDetailDTO orderChargeDetail = buildOrderChargeDetailDTO(OrderChargeTypeEnum.TAX, taxPrice, shop);

    }
    private TaxService getTaxService(String taxRuleType){
        return switch (taxRuleType) {
            case "canadaRule" -> new CanadaRuleTaxServiceImpl();
            case "canadaRuleNew" -> new CanadaRuleNewTaxServiceImpl();
            case "newTaxRule" -> new NewTaxRuleTaxServiceImpl();
            case "includeDeliveryFeeRule" -> new IncludeDeliveryRuleTaxServiceImpl();
            case "excludeVoucherMerRule" -> new ExcludeVoucherMerRuleTaxServiceImpl();
            default -> new DefaultRuleTaxServiceImpl();
        };
    }
}
