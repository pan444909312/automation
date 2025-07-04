package com.miller.entity.tools.req;

import lombok.Data;

import java.util.List;

/**
 *
 * @Author: panjuxiang
 * @Since: 2025/6/18
 */
@Data
public class AutoCreateCouponReqDTO {
    private Integer couponScope;
    private Integer couponType;
    private List<Long> shopList;
    private List<Long> productList;
}
