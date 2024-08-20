package com.miller.erp.moudle.manage.merchant.product.request;


import lombok.Data;

/**
 * 请求对象_这个是html的请求对象，所以数据需要自己构造
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/08/12 13:49:23
 */
@Data
public class CopyOtherShopProductRequestDTO {
    private Long orgShopId;
    private Long targetProductId;
    private String param;
    private Integer type;
    private Integer isSupermarket;
    private Long shopIds;
    private Integer chooseCopyType;
    private Long  targetMenuId;
    private String select;
    private Long shopId;
    private String copyProduct;
    private Long productId;
    private String menuId;

}
