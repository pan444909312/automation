package com.miller.userapp.module.data.promotion.redpacket;

import com.miller.entity.constant.CouponTypeEnum;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.launcher.TestCaseRunnerLauncher;
import com.miller.service.framework.util.ResourceUtils;
import com.miller.userapp.module.data.promotion.redpacket.flow.CouponTemplateFlow;
import com.miller.userapp.module.data.promotion.redpacket.request.CouponTemplateAddProductRequestDTO;
import com.miller.userapp.module.data.promotion.redpacket.request.CouponTemplateAddRequestDTO;
import com.miller.userapp.module.data.promotion.redpacket.request.CouponTemplateAddShopRequestDTO;
import com.miller.userapp.module.data.promotion.redpacket.response.CouponTemplateAddResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author panjuxiang
 * @since 2024/8/8 16:48
 */
@Slf4j
@Scenario(scenarioID = "01K06P9H5MRQF374VNHGYFCQ8H", scenarioName = "红包工厂_一键自动创建模板优惠券",
        author = "panjuxiang@hungrypandagroup.com",
        developmentTime = 4 * 60, maintenanceTime = 0, manualTestTime = 5)
public class CouponGenerate {

    private CouponTemplateFlow couponTemplateFlow;
    private final String fileURL = "coupon" + File.separator;
    //默认店铺id
    private final String defaultShopId = "536235947";
    //默认商品id
    private final String defaultProductIds = "82083860,82083858,82083862";
    //默认券类型 满减
    private final Integer defaultCouponType = 1;

    private void tearDown() {
        new TestCaseRunnerLauncher().runTestMethod(CouponGenerate.class, "reportedData");
        // 搜索索引更新
    }

    @Test
    @DisplayName("一键自动创建商家")
    void reportedData() {
        // 什么都不需要做，仅仅是作为数据上报，复用现在测试框架功能
    }


    /**
     * 根据传入的优惠券入参文件 url，添加优惠券
     * 优惠券有效期默认为今天到后30天
     *
     * @param couponDataResource 优惠券入参文件 url
     * @return 添加结果，成功则返回优惠券template_sn
     */
    private String addCoupon(String couponDataResource) {

        ERPLoginFlow.loginByDefaultUser();
        couponTemplateFlow = new CouponTemplateFlow();
        // 处理优惠券有效期，默认设置开始时间就今天，结束时间往后推30天
        String couponData = new ResourceUtils().readTestCaseDataFromResourcesPath(
                        CouponTemplateAddRequestDTO.class, fileURL + couponDataResource).
                replace("$startTime$", new DateTime(new Date()).toString("yyyy-MM-dd")).
                replace("$endTime$", new DateTime(new Date()).plusDays(30).toString("yyyy-MM-dd"));

        try {
            CouponTemplateAddResponseDTO couponTemplateAddResponseDTO = couponTemplateFlow.addCoupon(couponData);
            return couponTemplateAddResponseDTO.getData();
        } catch (Exception e) {
            return "优惠券添加失败";
        }finally {
            tearDown();
        }
    }

    /**
     * 根据券类型，添加对应的模版平台券
     *
     * @param couponType 券类型，1：满减；2：折扣；3：减运费
     * @return 添加结果，成功则返回优惠券template_sn
     */
    public String addPlatformCouponTemplate(Integer couponType) {
        // 满减券
        if (couponType == CouponTypeEnum.FULL_SUB.getCode()) {
            return addCoupon("CouponPlatformFullSub.json");
        }
        // 折扣券
        if (couponType == CouponTypeEnum.DISCOUNT.getCode()) {
            return addCoupon("CouponPlatformDiscount.json");
        }
        // 减运费券
        if (couponType == CouponTypeEnum.DELIVERY_FEE.getCode()) {
            return addCoupon("CouponPlatformShippingSub.json");
        }
        return "券类型不存在";
    }

    /**
     * 不传couponType 默认创建模版的满减平台券
     *
     * @return 添加结果，成功则返回优惠券template_sn
     */
    public String addPlatformCouponTemplate() {
        return addPlatformCouponTemplate(defaultCouponType);
    }

    /**
     * 根据券类型和商家列表，创建商家券并指定可用商家
     *
     * @param couponType 券类型，1：满减；2：折扣；3：减运费
     * @param shopList   指定的商家列表
     * @return 添加结果，成功则返回优惠券template_sn
     */
    public String addShopCouponTemplate(Integer couponType, List<Long> shopList) {
        CouponTemplateAddShopRequestDTO requestDTO = new CouponTemplateAddShopRequestDTO();
        String templateSn = "";
        Integer errorNum = 0;
        // 满减券
        if (couponType == CouponTypeEnum.FULL_SUB.getCode()) {
            templateSn = addCoupon("CouponShopFullSub.json");
            requestDTO.setTemplateSn(templateSn);
            requestDTO.setShopIds(shopList);
            errorNum = couponTemplateFlow.addCouponShop(requestDTO).getData().getErrorShopNum();
        }
        // 折扣券
        else if (couponType == CouponTypeEnum.DISCOUNT.getCode()) {
            templateSn = addCoupon("CouponShopDiscount.json");
            requestDTO.setTemplateSn(templateSn);
            requestDTO.setShopIds(shopList);
            errorNum = couponTemplateFlow.addCouponShop(requestDTO).getData().getErrorShopNum();
        }
        // 减运费券
        else if (couponType == CouponTypeEnum.DELIVERY_FEE.getCode()) {
            templateSn = addCoupon("CouponShopShippingSub.json");
            requestDTO.setTemplateSn(templateSn);
            requestDTO.setShopIds(shopList);
            errorNum = couponTemplateFlow.addCouponShop(requestDTO).getData().getErrorShopNum();
        } else return "券类型不存在";

        return errorNum <= 0 ? templateSn : templateSn + "，导入商家，失败商家数：" + errorNum;

    }

    /**
     * 根据券类型和商家列表，创建商家券并指定可用商家，多个商家用","分隔
     *
     * @param couponType  券类型，1：满减；2：折扣；3：减运费
     * @param shopListStr 指定的商家列表
     * @return 添加结果，成功则返回优惠券template_sn
     */
    public String addShopCouponTemplate(Integer couponType, String shopListStr) {
        List<Long> shopList = Arrays.stream(shopListStr.split(",")).map(Long::valueOf).toList();
        return addShopCouponTemplate(couponType, shopList);
    }

    /**
     * 不传couponType 默认创建模版的满减商家券，多个商家用","分隔
     *
     * @param shopList 指定的商家列表
     * @return 添加结果，成功则返回优惠券template_sn
     */
    public String addShopCouponTemplate(String shopList) {
        return addShopCouponTemplate(defaultCouponType, shopList);
    }

    /**
     * 不传商家列表，指定默认设置的商家id 536235947
     *
     * @param couponType 券类型，1：满减；2：折扣；3：减运费
     * @return 添加结果，成功则返回优惠券template_sn
     */
    public String addShopCouponTemplate(Integer couponType) {
        return addShopCouponTemplate(couponType, defaultShopId);
    }

    /**
     * 不传couponType和商家列表，
     * 默认创建模版的满减商家券
     * 指定默认设置的商家id 536235947
     *
     * @return 添加结果，成功则返回优惠券template_sn
     */
    public String addShopCouponTemplate() {
        return addShopCouponTemplate(defaultCouponType, defaultShopId);
    }

    /**
     * 创建商品券，并按传入的商品id导入商品
     *
     * @param productList 商品id列表
     * @return 添加结果，成功则返回优惠券template_sn
     */
    public String addProductCouponTemplate(List<Long> productList) {
        Integer errorNum = 0;
        CouponTemplateAddProductRequestDTO requestDTO = new CouponTemplateAddProductRequestDTO();
        String templateSn = addCoupon("CouponProduct.json");
        requestDTO.setTemplateSn(templateSn);
        requestDTO.setProductIds(productList);
        errorNum = couponTemplateFlow.addCouponProduct(requestDTO).getData().getErrorShopNum();
        return errorNum <= 0 ? templateSn : templateSn + "，导入商品失败，商品不存在或被删除，失败数：" + errorNum;

    }

    /**
     * 创建商品券，并按传入的商品id导入商品，多个商品用","分隔
     *
     * @param productListStr 商品id列表
     * @return 添加结果，成功则返回优惠券template_sn
     */
    public String addProductCouponTemplate(String productListStr) {
        List<Long> productList = Arrays.stream(productListStr.split(",")).map(Long::valueOf).toList();
        return addProductCouponTemplate(productList);
    }

    /**
     * 创建商品券，不传指定商品则传入写死的默认测试商品id
     *
     * @return 添加结果，成功则返回优惠券template_sn
     */
    public String addProductCouponTemplate() {
        return addProductCouponTemplate(defaultProductIds);
    }

    public void addCouponTest() {
        //创建平台满减优惠券
//        System.out.println(addPlatformCouponTemplate(1));

        //创建平台折扣优惠券
//        System.out.println(addPlatformCouponTemplate(2));

        //创建平台减运费优惠券
//        System.out.println(addPlatformCouponTemplate(3));

        //创建店铺满减优惠券
//        System.out.println(addShopCouponTemplate(1));

        //创建店铺折扣优惠券
//        System.out.println(addShopCouponTemplate(2));

        //创建店铺减运费优惠券
//        System.out.println(addShopCouponTemplate(3));

        //指定商家创建店铺满减优惠券
//        System.out.println(addShopCouponTemplate(1, "278985730,116049906"));

        //创建商品券
//        System.out.println(addProductCouponTemplate());

        //指定商品创建商品券
        System.out.println(addProductCouponTemplate("82083860"));
    }

    /**
     * 一键测试添加平台满减券、平台折扣券、平台减运费券、商家满减券、商家折扣券、商家减运费券、商品券
     */
    public void addAllCouponTest() {
        // couponType 券类型，1：满减；2：折扣；3：减运费
        addPlatformCouponTemplate(1);
        addPlatformCouponTemplate(2);
        addPlatformCouponTemplate(3);
        // shopList传商家id，多个商家用","分隔
        addShopCouponTemplate(1, "536235947");
        addShopCouponTemplate(2, "536235947");
        addShopCouponTemplate(3, "536235947");
        // productList传商品id，多个商品用","分隔
        addProductCouponTemplate("82083860");
    }

}
