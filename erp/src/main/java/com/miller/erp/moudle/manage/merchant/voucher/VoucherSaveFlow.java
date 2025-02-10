package com.miller.erp.moudle.manage.merchant.voucher;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.voucher.api.common.enums.VoucherTypeEnum;
import com.hungrypanda.voucher.api.req.AddVoucherReq;
import com.miller.common.util.ULIDUtils;
import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.erp.moudle.login.response.ERPLoginResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;
import com.panda.erp.server.api.constant.VoucherEnum;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.MultiPartSpecification;


import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.MultiPartConfig.multiPartConfig;
import static org.apache.commons.lang3.StringUtils.getBytes;

public class VoucherSaveFlow {
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_BACKUP + "/admin/voucher/save.htm";
    public static void voucherSave(AutoAddVoucherReq addVoucherReq){
        ERPLoginFlow.erpLoginByCookie();
        RequestUtils.getHeaders().put("Content-Type", "multipart/form-data;charset=UTF-8");
        HttpUtils.sendPostRequestReturnBody(uri,null,RequestUtils.getHeaders(), JSON.parseObject(JSON.toJSONString(addVoucherReq)),null);



    }
    public static void main(String[] args){
        ERPLoginFlow.loginByDefaultUser();
//        ERPLoginFlow.erpLoginByCookie();
        AutoAddVoucherReq addVoucherReq = new AutoAddVoucherReq();
        addVoucherReq.setShopId("1313");
        addVoucherReq.setVoucherType("3");
        addVoucherReq.setVoucherName("TestFromAPI" + ULIDUtils.generateULID().substring(2,10));
        addVoucherReq.setVoucherShopName("TestFromAPI" + ULIDUtils.generateULID().substring(2,10));
        addVoucherReq.setGoodsPrice("20");
        addVoucherReq.setVoucherThresholdType("1");
        addVoucherReq.setVoucherThreshold("");
        addVoucherReq.setVoucherNum("2");
        addVoucherReq.setSalePrice("15");
        addVoucherReq.setSumSalePrice("30.00");
        addVoucherReq.setStockType("1");
        addVoucherReq.setStockNum("");
        addVoucherReq.setIsReservation("1");
        addVoucherReq.setIsOnSale("1");
        addVoucherReq.setSetMealDesc("");
        addVoucherReq.setStartTime("");
        addVoucherReq.setEndTime("");
        addVoucherReq.setEffectiveTimeType("2");
        addVoucherReq.setVaildDays("1999");
        addVoucherReq.setVoucherSort("");
        addVoucherReq.setVoucherUserAllDayPurchase("");
        addVoucherReq.setVoucherUserAllDayPurchaseType("1");
        addVoucherReq.setUseLimitType("0");
        addVoucherReq.setUseLimit("");
        addVoucherReq.setUserType("0");
        addVoucherReq.setBuyLimitNumType("0");
        addVoucherReq.setBuyLimitNum("");
        addVoucherReq.setAppShow("1");
        addVoucherReq.setRuleDesc("");
        addVoucherReq.setPlatformContributionAmount("2");
        addVoucherReq.setMerchantContributionAmount("3");
        addVoucherReq.setCommissionType("0");
        addVoucherReq.setCommissionTax("");
        addVoucherReq.setCommissionValue("");
        addVoucherReq.setFormulaName("");
        addVoucherReq.setTaxAmountType("0");
        addVoucherReq.setTaxRate("");
        addVoucherReq.setIsRecommended("0");
        addVoucherReq.setActivityCityName("杭州市");
        addVoucherReq.setActivitySn("2280GVPH2O");
        addVoucherReq.setFirstImg(new File(""));
        addVoucherReq.setRollerImage(new File(""));
        VoucherSaveFlow.voucherSave(addVoucherReq);
//        MultiPartSpecification firstImg = new MultiPartSpecBuilder(new File(""))
//                .fileName("")
//                .controlName("firstImg")
//                .mimeType("application/octet-stream")
//                .build();
//        MultiPartSpecification rollerImage = new MultiPartSpecBuilder(new File(""))
//                .fileName("")
//                .controlName("rollerImage")
//                .mimeType("application/octet-stream")
//                .build();
//        RequestUtils.getHeaders().put("Content-Type", "multipart/form-data;charset=UTF-8");
//        given().config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"))).headers(RequestUtils.getHeaders())
////        given().config(RestAssuredConfig.config().multiPartConfig(multiPartConfig().defaultCharset("UTF-8"))).headers(RequestUtils.getHeaders())
//                .multiPart(firstImg)
//                .multiPart(rollerImage)
//                .multiPart(rollerImage)
//                .multiPart("shopId","1313").multiPart("voucherType","3").multiPart("voucherName","测试代金券来自API" + ULIDUtils.generateULID().substring(2,7))
//                .multiPart("shopIds","") .multiPart("voucherShopName","测试代金券来自API" + ULIDUtils.generateULID().substring(2,7)).multiPart("goodsPrice","20")
//                .multiPart("voucherThresholdType","1").multiPart("voucherThreshold","").multiPart("voucherNum","2")
//                .multiPart("salePrice","15").multiPart("sumSalePrice","30.00") .multiPart("stockType","1")
//                .multiPart("isOnSale","1").multiPart("setMealDesc","").multiPart("startTime","")
//                .multiPart("endTime","").multiPart("effectiveTimeType","2").multiPart("vaildDays","1999")
//                .multiPart("voucherSort","").multiPart("voucherUserAllDayPurchaseType","1").multiPart("voucherUserAllDayPurchase","")
//                .multiPart("useLimitType","0").multiPart("buyLimitNumType","0").multiPart("useLimit","")
//                .multiPart("userType","0").multiPart("buyLimitNum","").multiPart("appShow","1")
//                .multiPart("ruleDesc","").multiPart("platformContributionAmount","2").multiPart("merchantContributionAmount","3.00")
//                .multiPart("commissionType","0").multiPart("commissionTax","").multiPart("commissionValue","")
//                .multiPart("formulaName","").multiPart("taxAmountType","0").multiPart("taxRate","")
//                .multiPart("isRecommended","0").multiPart("activityCityName","杭州市").multiPart("activitySn","2280GVPH2O")
//                .when()
//                .post(uri)
//                .then()
//                .statusCode(200).log().all();








    }
}
