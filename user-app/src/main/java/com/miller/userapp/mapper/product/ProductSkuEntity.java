package com.miller.userapp.mapper.product;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("product_sku")
@Data
public class ProductSkuEntity {
    private static final long serialVersionUID = -888849767162528147L;
    @TableId
    private Long productSkuId;
    private String productSkuName;
    private Integer productSkuPrice;
    private int isDefault;
    private long productId;
    private String valueName;
    private long createTime;
    private long updateTime;
    private int isDel;
    private String country;
    private int platformPrice;
    private Integer isDefaultSpec;
    private Byte status;
}
