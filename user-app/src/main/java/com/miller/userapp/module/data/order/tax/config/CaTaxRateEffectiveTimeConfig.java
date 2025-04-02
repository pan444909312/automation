package com.miller.userapp.module.data.order.tax.config;

import lombok.Data;

@Data
public class CaTaxRateEffectiveTimeConfig {

    /**
     * 有效日期：开始日期
     */
    private String startDate;

    /**
     * 有效日期：结束日期
     */
    private String endDate;

}
