package com.miller.userapp.mapper.virtual;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/*
没有hp_delivery_discount_shop表对应的实体类，自行添加
 */
@Data
@TableName("hp_delivery_discount_shop")
public class DeliveryDiscountShopEntity {
    private Long recId;
    private String discountSn;
    private Long shopId;
    private int importType;
    private Long  tagId;
    private  int isDel;
    private Long gmtCreated;
    private Long gmtUpdate;
    private Long managerId;
    private Long startTime;
    private Long endTime;
    private String systemCode;
}
