package com.miller.controller.tools;

import com.alibaba.fastjson.JSON;
import com.miller.controller.tools.conversion.StringConversionDto;
import com.miller.controller.tools.product.service.StringConversionService;
import com.miller.entity.constant.CouponScopeEnum;
import com.miller.entity.tools.req.AutoCreateCouponReqDTO;
import com.miller.entity.tools.req.AutoCreateMerchantReqDTO;
import com.miller.entity.tools.req.SendRedPacketReqDTO;
import com.miller.entity.util.Response;
import com.miller.testcase.factory.MerchantFactory;
import com.miller.userapp.module.data.promotion.redpacket.CouponGenerate;
import com.miller.userapp.module.data.promotion.redpacket.SendRedPacket;
import com.miller.userapp.module.data.user.CreateUserEntity;
import com.miller.userapp.module.data.user.CreateUserServer;
import com.miller.userapp.util.DBUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: panjuxiang
 * @Since: 2025/6/17
 */

@RestController
@RequestMapping("/automation/tools")
@Tag(name = "工具相关接口")
public class ToolsController {

    @Autowired
    private StringConversionService stringConversionService;

    @Operation(description = "发布格式化")
    @PostMapping("/toPublishingFormat")
    public Response<String> toPublishingFormat(@RequestBody StringConversionDto body) {
        final String resStr = stringConversionService.toPublishingFormat(body);
        return Response.success(resStr);
    }

    @Operation(description = "一键创建用户")
    @PostMapping("/autoCreateUser")
    public Response<String> autoCreateUser(
            @RequestBody String createUserDTO,
            @RequestParam(value = "isCustom", defaultValue = "false") Boolean isCustom) {

        CreateUserEntity createUserData = JSON.parseObject(createUserDTO, CreateUserEntity.class);
        CreateUserServer createUserServer = new CreateUserServer(DBUtils.getDBOfPandaTest(), createUserData);
        String str = createUserServer.autoCreateUser(!isCustom, createUserData.getPhone());
        try {
            Long.parseLong(str);
            return Response.success("UserId:" + str);
            // str是有效数字
        } catch (NumberFormatException e) {
            // str不是数字
            return Response.fail(str);
        }

    }

    @Operation(description = "一键创建商家")
    @PostMapping("/autoCreateMerchant")
    public Response<String> autoCreateMerchant(@RequestBody AutoCreateMerchantReqDTO autoCreateMerchantReqDTO) {
        String result;
//        try {
            result = MerchantFactory.quickCreateMerchant(autoCreateMerchantReqDTO.getCityName(), autoCreateMerchantReqDTO.getShopName());
//        }catch (Exception e){
//            return Response.fail(e.getMessage());
//        }

        return Response.success(result);
    }

    @Operation(description = "获取一键创建商家支持的城市列表")
    @GetMapping("/getSupportCityList")
    public Response<List<String>> getSupportCityList() {

        String supportedCities = MerchantFactory.City.getSupportedCities();
        List<String> list = Arrays.asList(supportedCities.split("、"));
        return Response.success(list);
    }

    @Operation(description = "验证码查询工具")
    @PostMapping("/codeQuery")
    public Response<String> codeQuery() {

        return Response.success("");
    }

    @Operation(description = "一键创建优惠券")
    @PostMapping("/autoCreateCoupon")
    public Response<String> autoCreateCoupon(@RequestBody AutoCreateCouponReqDTO autoCreateCouponReqDTO) {
        CouponGenerate couponGenerate = new CouponGenerate();

        // 商家券
        if (autoCreateCouponReqDTO.getCouponScope().equals(CouponScopeEnum.SHOP_COUPON.getCode())) {
            if (autoCreateCouponReqDTO.getShopList() == null || autoCreateCouponReqDTO.getShopList().isEmpty()) {
                // 设置商家默认值
                ArrayList<Long> shopList = new ArrayList<>();
                shopList.add(536235947L);
                autoCreateCouponReqDTO.setShopList(shopList);
            }
            return Response.success(couponGenerate.addShopCouponTemplate(autoCreateCouponReqDTO.getCouponType(), autoCreateCouponReqDTO.getShopList()));
        }

        // 商品券
        if (autoCreateCouponReqDTO.getCouponScope().equals(CouponScopeEnum.PRODUCT_COUPON.getCode())) {
            if (autoCreateCouponReqDTO.getProductList() == null || autoCreateCouponReqDTO.getProductList().isEmpty()) {
                // 设置商品默认值
                ArrayList<Long> productList = new ArrayList<>();
                productList.add(82083860L);
                productList.add(82083858L);
                productList.add(82083862L);
                autoCreateCouponReqDTO.setProductList(productList);
            }
            return Response.success(couponGenerate.addProductCouponTemplate(autoCreateCouponReqDTO.getProductList()));
        }

        // 平台券
        if (autoCreateCouponReqDTO.getCouponScope().equals(CouponScopeEnum.PLATFORM_COUPON.getCode())) {
            return Response.success(couponGenerate.addPlatformCouponTemplate(autoCreateCouponReqDTO.getCouponType()));
        }

        // 神券
        if (autoCreateCouponReqDTO.getCouponScope().equals(CouponScopeEnum.SUPER_COUPON.getCode())) {
            return Response.fail("暂不支持神券类型");
        }

        return Response.fail("参数错误");
    }

    @Operation(description = "一键发红包")
    @PostMapping("/sendRedPacket")
    public Response<String> sendRedPacket(@RequestBody SendRedPacketReqDTO sendRedPacketReqDTO) {

        SendRedPacket sendRedPacket = new SendRedPacket();
        sendRedPacket.sendRedPacketToUserByUserId(sendRedPacketReqDTO.getUserId(), sendRedPacketReqDTO.getRedPacketId());

        return Response.success("添加成功");
    }

    @Operation(description = "批量创建商品")
    @PostMapping("/autoCreateGoods")
    public Response<String> autoCreateGoods() {

        return Response.success("");
    }

}
