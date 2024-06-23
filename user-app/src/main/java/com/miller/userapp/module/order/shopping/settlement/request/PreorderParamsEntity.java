package com.miller.userapp.module.order.shopping.settlement.request;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class PreorderParamsEntity {

    private Integer preorderOpenType;

    private Integer preorderClosedSupport;

    private Integer preorderTimeMode;

    private Integer preorderDays;

    private Integer preorderCutOff;

    private Integer preorderInterval;

    private Integer preorderPushShop;
}
