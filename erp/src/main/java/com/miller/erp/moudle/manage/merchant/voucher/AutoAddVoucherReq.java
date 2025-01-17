package com.miller.erp.moudle.manage.merchant.voucher;

import lombok.Data;

import java.io.File;

@Data
public class AutoAddVoucherReq {
    private String voucherType;
    private String firstImgUrl;
    private String rollerImageUrl;
    private String userType;
    private String buyLimitNumType;
    private String buyLimitNum;
    private String appShow;
    private String voucherName;
    private String shopId;
    private String voucherShopName;
    private String goodsPrice;
    private String voucherThresholdType;
    private String voucherThreshold;
    private String voucherNum;
    private String voucherSort;
    private String voucherUserAllDayPurchaseType;
    private String voucherUserAllDayPurchase;
    private String sumSalePrice;
    private String salePrice;
    private String stockType;
    private String stockNum;
    private String daysLimit;
    private String isReservation;
    private String useLimitType;
    private String useLimit;
    private String isRecommended;
    private String isOnSale;
    private String setMealDesc;
    private String effectiveTimeType;
    private String startTime;
    private String endTime;
    private String vaildDays;
    private String ruleDesc;
    private String platformContributionAmount;
    private String merchantContributionAmount;
    private String commissionType;
    private String commissionTax;
    private String commissionValue;
    private String formulaName;
    private String taxAmountType;
    private String taxRate;
    private String activitySn;
    private String activityCityName;
    private File firstImg;
    private File rollerImage;

}
