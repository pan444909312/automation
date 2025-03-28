package com.miller.userapp.module.data.order.tax.config;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RuleNewTaxConfigEntity {
    private List<Long> caTaxNotFoodShopIdList;
    private Map<String, CaTaxRateConfig> caTaxRateMap;
    private CaTaxRateEffectiveTimeConfig caTaxRateEffectiveTimeConfig;
}
