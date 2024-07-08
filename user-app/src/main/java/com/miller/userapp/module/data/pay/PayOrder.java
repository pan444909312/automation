package com.miller.userapp.module.data.pay;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 支付订单
 * </p>
 *
 * @author haoyn
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName("pay_order")
public class PayOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，采用雪花算法
     */
    @TableId(type = IdType.INPUT)
    private Long id;

    /**
     * 本系统提供第三方的交易号
     */
    private String tradeNo;

    /**
     * 第三方渠道提供标识一次操作的流水号、支付凭证
     */
    private String channelTransactionNo;

    /**
     * 订单所属应用，应用配置pay_app_config主键ID
     */
    private Integer appId;

    /**
     * 业务应用订单类型： 1开通会员 2普通订单 3余额充值 4支付宝小程序普通订单？
     */
    private Integer orderType;

    /**
     * 业务应用的商品订单主键ID
     */
    private String orderId;

    /**
     * 支付订单状态： 0待支付 1已支付 2支付失败 3 订单关闭
     */
    private Integer tradeStatus;

    /**
     * 默认：0-未退款，1-存在退款
     */
    private Integer refundStatus;

    /**
     * 已退款总额，单位分。
     */
    private Integer amountRefunded;

    /**
     * 支付渠道服务费，单位分(需要吗)
     */
//    private Integer channelFee;

    /**
     * 订单服务费费率，需要支付订单存储吗，保留几位？
     */
//    private BigDecimal feeRate;

    /**
     * 订单过期时间，订单过期后处理，如过期后收到支付宝、微信等支付成功通知，则自动退款等处理。
     */
    private Long timeExpire;

    /**
     * 支付金额，单位分
     */
    private Integer amount;

    /**
     * 标价币种
     */
    private String currency;

    /**
     * 城市
     */
    private String city;

    /**
     * 支付渠道
     */
    private String payChannel;

    /**
     * 支付渠道下的具体支付方式
     */
    private String payExtraChannel;

    /**
     * 统一具体支付方式
     */
    private String paymentPattern;

    /**
     * 支付完成时间
     */
    private Long timePaid;

    /**
     * 错误码
     */
    private String failureCode;

    /**
     * 错误信息
     */
    private String failureMsg;

    /**
     * 收款方：用户ID或应用配置的商户账号
     */
    private String payeeId;

    /**
     * 付款方：用户ID或应用配置的商户账号
     */
    private String payerAccount;

    /**
     * 订单更新版本号，初始1，每次修改+1，用于乐观锁版本控制
     */
    private Integer version;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 交易来源，可能为以下这些值：mis,sdk,posdirect,openapi,others,web,wap,pos,pc,iOS,ios,android
     */
    private String source;
    /**
     * ISO 3位国家代码如：中国：CHN

     */
    private String countryCode;
    /**
     * 交易时支付中心接入渠道的后版本，并不是渠道的支付版本，是支付中心定义的交易版本
     * 默认v1
     */
    private String paymentVersion;
    /**
     * 用于某些特殊渠道对交易号长度有要求时，发起交易的交易号
     */
    private String convertTradeNo;
    /**
     * 网关服务费3，单位分
     */
    private Integer gatewayIncome3;
}
