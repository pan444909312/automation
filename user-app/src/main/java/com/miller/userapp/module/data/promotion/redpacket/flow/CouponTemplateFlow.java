package com.miller.userapp.module.data.promotion.redpacket.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.module.data.promotion.redpacket.request.CouponTemplateAddProductRequestDTO;
import com.miller.userapp.module.data.promotion.redpacket.request.CouponTemplateAddRequestDTO;
import com.miller.userapp.module.data.promotion.redpacket.request.CouponTemplateAddShopRequestDTO;
import com.miller.userapp.module.data.promotion.redpacket.response.CouponTemplateAddProductResponseDTO;
import com.miller.userapp.module.data.promotion.redpacket.response.CouponTemplateAddResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.userapp.module.data.promotion.redpacket.response.CouponTemplateAddShopResponseDTO;

/**
 * @author panjuxiang
 * @since 2024/8/8 16:34
 */
public class CouponTemplateFlow {

    private String couponAddURL = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/coupon/template/add";
    private String couponAddShopURL = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/coupon/template/add/shop";
    private String couponAddProductURL = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/coupon/template/product/add";


    /**
     * 添加优惠券
     * @param couponTemplateAddRequestDTO
     * @return
     */
    public CouponTemplateAddResponseDTO addCoupon(CouponTemplateAddRequestDTO couponTemplateAddRequestDTO) {
        return HttpUtils.sendPostRequestReturnJavaObject(
                couponAddURL, null, RequestUtils.getHeaders(), couponTemplateAddRequestDTO, null, CouponTemplateAddResponseDTO.class);
    }

    /**
     * 添加优惠券
     * @param couponTemplateAddRequestDTO
     * @return
     */
    public CouponTemplateAddResponseDTO addCoupon(String couponTemplateAddRequestDTO) {
        return HttpUtils.sendPostRequestReturnJavaObject(
                couponAddURL, null, RequestUtils.getHeaders(), couponTemplateAddRequestDTO, null, CouponTemplateAddResponseDTO.class);
    }


    /**
     * 优惠券绑定商家
     * @param couponTemplateAddShopRequestDTO
     * @return
     */
    public CouponTemplateAddShopResponseDTO addCouponShop(CouponTemplateAddShopRequestDTO couponTemplateAddShopRequestDTO) {
        return HttpUtils.sendPostRequestReturnJavaObject(
                couponAddShopURL, null, RequestUtils.getHeaders(), couponTemplateAddShopRequestDTO, null, CouponTemplateAddShopResponseDTO.class);
    }

    /**
     * 优惠券绑定商品
     * @param couponTemplateAddProductRequestDTO
     * @return
     */
    public CouponTemplateAddProductResponseDTO addCouponProduct(CouponTemplateAddProductRequestDTO couponTemplateAddProductRequestDTO) {
        return HttpUtils.sendPostRequestReturnJavaObject(
                couponAddProductURL, null, RequestUtils.getHeaders(), couponTemplateAddProductRequestDTO, null, CouponTemplateAddProductResponseDTO.class);
    }

}
