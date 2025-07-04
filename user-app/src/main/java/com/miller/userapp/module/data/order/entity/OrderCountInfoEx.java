package com.miller.userapp.module.data.order.entity;

import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.service.order.ZeroOrderHelper;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Data
public class OrderCountInfoEx {
    /**
     * 商品售价汇总(包含加料费用)
     */
    private Integer totalPrice;

    /**
     * 商品原价汇总(包含加料费用)
     */
    private Integer orgTotalPrice;

    /**
     * 根据平台抽成后商品价格计算的，商家端显示的商品总价
     */
    private Integer merTotalPrice;

    /**
     * 订单费用价格
     */
    private Integer fixedPrice;

    /**
     * 根据平台抽成后商品价格计算的，商家端显示的不含小费的最终价格
     */
    private Integer merFixedPrice;

    /**
     * 包装费用
     */
    private Integer packingCharges;

    /**
     * 塑料袋打包费，也是打包费
     */
    private Integer plasticAmount;

    /**
     * 平台打包费
     */
    private Integer platformPacking;

    /**
     * 扣除运费立减优惠后配送费
     * orgDeliveryPrice - deliveryPriceDiscount
     */
    private Integer sendMoney;

    /**
     * 配送费原价
     */
    private Integer orgDeliveryPrice;

    /**
     * 配送费优惠
     */
    private Integer deliveryPriceDiscount;

    /**
     * 消费税
     */
    private Integer taxPrice;

    /**
     * 商家端消费税
     */
    private Integer merTaxPrice;

    /**
     * 满减
     */
    private Integer subDiscount;

    /**
     * 商米端满减
     */
    private Integer merSubDiscount;

    /**
     * 平台费用
     */
    private Integer platformFee;

    /**
     * 首单优惠
     */
    private Integer firstDiscount;

    /**
     * 餐具费用
     */
    private Integer tablewarePrice;

    /**
     * 红包满减
     */
    private Integer redPacketPrice;

    /**
     * 商品券红包优惠
     */
    private Integer productRedPacketPrice;

    /**
     * 自提折扣优惠
     */
    private Integer takeDiscountPrice;

    /**
     * 会员平台服务费优惠金额
     */
    private Integer memberPlatformFeeDiscount;

    /**
     * 会员运费优惠金额
     */
    private Integer memberDeliveryDiscount;

    /**
     * 运费红包
     */
    private Integer deliveryRedPacket;

    /**
     * 店铺首单优惠
     */
    private Integer shopFirstDiscount;

    /**
     * 小额订单费用
     */
    private Integer smallOrderFee;
    /**
     * 商家端店铺首单优惠
     */
    private Integer merShopFirstDiscount;

    /**
     * 代金券优惠
     */
    private Integer voucherDiscount;

    /**
     * 自主收费配置(多项)
     */
    private List<Integer> chargeAmounts;

    /**
     * 商品加价总金额
     */
    private Integer orgTotalPlatformPrice;

    /**
     * 配料总加金额
     */
    private Integer orgTotalTagPlatformPrice;

    /**
     * 合单-会员优惠价
     */
    private Integer memberDiscountPrice;
    /**
     * 合单-会员原价
     */
    private Integer orgMemberPrice;
    /**
     * 合单-代金券售价
     */
    private Integer voucherPrice;

    /**
     * 单品折扣-独享优惠
     */
    private Integer exclusiveDiscount;

    /**
     * 优速达订单费用
     */
    private Integer fastDeliveryAmount;
    /**
     * 起送价基于原价
     */
    private Integer startSendMoneyBaseOnOrg;
    /**
     * 配送费独立加价
     */
    private Integer deliverAddPriceAloneChargeAmount;

    public OrderCountInfoEx() {
        this.totalPrice = 0;
        this.orgTotalPrice = 0;
        this.fixedPrice = 0;
        this.merFixedPrice = 0;
        this.packingCharges = 0;
        this.plasticAmount = 0;
        this.platformPacking = 0;
        this.sendMoney = 0;
        this.orgDeliveryPrice = 0;
        this.taxPrice = 0;
        this.merTaxPrice = 0;
        this.subDiscount = 0;
        this.merSubDiscount = 0;
        this.platformFee = 0;
        this.memberPlatformFeeDiscount = 0;
        this.firstDiscount = 0;
        this.tablewarePrice = 0;
        this.redPacketPrice = 0;
        this.takeDiscountPrice = 0;
        this.merTotalPrice = 0;
        this.memberDeliveryDiscount = 0;
        this.deliveryRedPacket = 0;
        this.smallOrderFee = 0;
        this.shopFirstDiscount = 0;
        this.merShopFirstDiscount = 0;
        this.voucherDiscount = 0;
        this.deliveryPriceDiscount = 0;
        this.orgTotalPlatformPrice = 0;
        this.orgTotalTagPlatformPrice = 0;
        this.memberDiscountPrice = 0;
        this.orgMemberPrice = 0;
        this.voucherPrice = 0;
        this.exclusiveDiscount = 0;
        this.chargeAmounts = new ArrayList<>();
        this.fastDeliveryAmount = 0;
        this.deliverAddPriceAloneChargeAmount = 0;
    }

    private Integer getChargeAmountSum() {
        if (CollectionUtils.isEmpty(chargeAmounts)) {
            return 0;
        }
        return chargeAmounts.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
    }

    public void addChargeAmounts(Integer chargeAmount) {
        if (Objects.isNull(chargeAmount)) {
            return;
        }
        chargeAmounts.add(chargeAmount);
    }

    public void addTotalPrice(Integer amount) {
        if (amount == null) {
            return;
        }
        this.totalPrice += amount;
    }

    public void addExclusiveDiscount(Integer discount) {
        if (discount == null) {
            return;
        }
        this.exclusiveDiscount += discount;
    }

    public void addOrgTotalPrice(Integer amount) {
        if (amount == null) {
            return;
        }
        this.orgTotalPrice += amount;
    }

    public void addMerTotalPrice(Integer amount) {
        if (amount == null) {
            return;
        }
        this.merTotalPrice += amount;
    }

    public void addPackingCharges(Integer amount) {
        if (amount == null) {
            return;
        }
        this.packingCharges += amount;
    }

    public void addSendMoneyDiscount(Integer amount) {
        if (amount == null) {
            return;
        }
        this.memberDeliveryDiscount += amount;
    }


    public Integer getFixedPrice() {
        int amount = totalPrice
                + packingCharges
                + platformPacking
                - subDiscount
                + taxPrice
                + platformFee
                - memberPlatformFeeDiscount
                - firstDiscount
                + sendMoney
                + tablewarePrice
                - redPacketPrice
                - memberDeliveryDiscount
                - shopFirstDiscount
                - takeDiscountPrice
                - deliveryRedPacket
                + smallOrderFee
                - voucherDiscount
                + getChargeAmountSum()
                + deliverAddPriceAloneChargeAmount;
        if (ZeroOrderHelper.canBeZeroOrder()) {
            return Math.max(amount, 0);
        } else {
            return Math.max(amount, 1);
        }
    }

    /**
     * 红包优惠基数，按照单品折扣>满减>代金券>平台首单优惠>门店新客立减>红包>自取折扣
     * @return
     */
    public Integer getBaseRedPacketPrice() {
        int amount = totalPrice
                + packingCharges
                + platformPacking
                + taxPrice
                + platformFee
                - memberPlatformFeeDiscount
                + sendMoney
                + tablewarePrice
                + smallOrderFee
                + getChargeAmountSum()
                + deliverAddPriceAloneChargeAmount
                - subDiscount
                - voucherDiscount
                - firstDiscount
                - shopFirstDiscount
                - memberDeliveryDiscount
                ;
        if (ZeroOrderHelper.canBeZeroOrder()) {
            return Math.max(amount, 0);
        } else {
            return Math.max(amount, 1);
        }
    }

    public Integer getMerFixedPrice() {
        int amount = merTotalPrice
                + packingCharges
                + platformPacking
                - merSubDiscount
                + merTaxPrice
                + platformFee
                - memberPlatformFeeDiscount
                - firstDiscount
                + sendMoney
                + tablewarePrice
                - memberDeliveryDiscount
                - redPacketPrice
                - deliveryRedPacket
                - merShopFirstDiscount
                - voucherDiscount
                +getChargeAmountSum()
                + deliverAddPriceAloneChargeAmount;
        if (ZeroOrderHelper.canBeZeroOrder()) {
            return Math.max(amount, 0);
        } else {
            return Math.max(amount, 1);

        }
    }


    public Integer getDeliveryPrice() {

        int amount = this.sendMoney - this.memberDeliveryDiscount - this.deliveryRedPacket;
        return Math.max(amount, 0);
    }

    /**
     * 加价之后，并且参与优惠之后，与配送费相关的金额
     * @return
     */
    public Integer getDeliveryPriceAddDeliveryChargeAmount() {

        int amount = getDeliveryPrice() + this.deliverAddPriceAloneChargeAmount;
        return Math.max(amount, 0);
    }

    /**
     * 获取商品折扣（减少的部分）
     *
     * @return
     */
    public Integer getProductDiscount() {
        Integer productDiscount = orgTotalPrice - totalPrice;
        return productDiscount;
    }

    /**
     * 获取总优惠金额（减少的部分）
     *
     * @return
     */
    public Integer getTotalDiscount() {
        Integer totalDiscount = getProductDiscount()
                + subDiscount
                + takeDiscountPrice
                + firstDiscount
                + redPacketPrice
                + memberPlatformFeeDiscount
                + memberDeliveryDiscount
                + deliveryPriceDiscount
                + deliveryRedPacket
                + shopFirstDiscount
                + voucherDiscount;
        return totalDiscount;
    }

    /**
     * 获取起送价
     * @return
     */
    public Integer getSendStartAmount() {
        if (StatusEnum.YES.matchType(startSendMoneyBaseOnOrg)){
            return this.orgTotalPrice;
        }
        return this.totalPrice - this.subDiscount;
    }

    /**
     * 获取未优惠前的总金额
     *
     * @return
     */
    public Integer getNotDisToaPrice() {
        Integer notDiscountTotalPrice = orgTotalPrice
                + packingCharges
                + platformPacking
                + taxPrice
                + platformFee
                + orgDeliveryPrice
                + tablewarePrice
                + smallOrderFee
                + getChargeAmountSum()
                + deliverAddPriceAloneChargeAmount;
        return notDiscountTotalPrice;
    }

    /**
     * 获取实际的店铺首单优惠
     * @return
     */
    public Integer getActualShopFirstDiscount(){
        int amount = totalPrice
                + packingCharges
                + platformPacking
                - subDiscount
                + taxPrice
                + platformFee
                - memberPlatformFeeDiscount
                + sendMoney
                + tablewarePrice
                + smallOrderFee
                + getChargeAmountSum()
                + deliverAddPriceAloneChargeAmount;
        if (ZeroOrderHelper.canBeZeroOrder()) {
            amount = Math.max(amount, 0);
        } else {
            amount = Math.max(amount, 1);
        }
        return Math.min(amount, shopFirstDiscount);
    }

    /**
     * 获取实际的首单优惠
     * @return
     */
    public Integer getActualFirstDiscount(){
        int amount = totalPrice
                + packingCharges
                + platformPacking
                - subDiscount
                + taxPrice
                + platformFee
                - memberPlatformFeeDiscount
                + sendMoney
                + tablewarePrice
                + smallOrderFee
                + getChargeAmountSum()
                + deliverAddPriceAloneChargeAmount;
        if (ZeroOrderHelper.canBeZeroOrder()) {
            amount = Math.max(amount, 0);
        } else {
            amount = Math.max(amount, 1);
        }
        return Math.min(amount, firstDiscount);
    }

    /**
     * 获取自取折扣优惠计算的总金额
     *
     * 自取基数（预期）=商品售价汇总(包含加料费用)+包装费用+餐具费用+配送费-满减-首单优惠-红包满减-会员运费优惠金额-店铺首单优惠-运费红包-代金券优惠
     *
     * @return
     */
    public Integer getTakeSelfNeedAmount() {
        int amount = totalPrice
                + packingCharges
                + tablewarePrice
                + platformPacking
                + sendMoney
                - subDiscount
                - firstDiscount
                - redPacketPrice
                - memberDeliveryDiscount
                - shopFirstDiscount
                - deliveryRedPacket
                - voucherDiscount;

        if (ZeroOrderHelper.canBeZeroOrder()) {
            return Math.max(amount, 0);
        } else {
            return Math.max(amount, 1);
        }
    }

    /**
     * 获取折扣红包优惠基数
     * @return
     */
    public Integer getRedPacketDiscountBase() {
        int redPacketDiscountBase = totalPrice
                + packingCharges
                + platformPacking
                + tablewarePrice;
        return redPacketDiscountBase;
    }

    /**
     * 获取运费优惠基数
     * @return
     */
    public Integer getDeliveryDiscountBase() {
        int deliveryDiscountBase = totalPrice
                + packingCharges
                + platformPacking
                + tablewarePrice;
        return deliveryDiscountBase;
    }

    /**
     * 加价总金额
     *
     * @param amount 量
     */
    public void addOrgTotalPlatformPrice(Integer amount) {
        if (amount == null) {
            return;
        }
        this.orgTotalPlatformPrice += amount;
    }

    /**
     * 配料的总加价金额
     *
     * @param amount 量
     */
    public void addOrgTotalTagPlatformPrice(Integer amount) {
        if (amount == null) {
            return;
        }
        this.orgTotalTagPlatformPrice += amount;
    }
}
