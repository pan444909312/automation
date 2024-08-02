package com.miller.userapp.module.data.pay;

import com.hungrypanda.app.server.entity.account.UserAccountEntity;
import com.hungrypanda.app.server.entity.member.MemberOrderEntity;
import com.hungrypanda.app.server.entity.order.OrderEntity;
import com.hungrypanda.app.server.entity.order.OrderFastDeliveryBusinessOrderEntity;
import com.hungrypanda.app.server.entity.order.OrderPaymentCombinedEntity;
import com.hungrypanda.app.server.entity.order.OrderPaymentCombinedRelationEntity;
import com.hungrypanda.app.server.entity.user.UserAccountFlowEntity;
import com.hungrypanda.app.server.entity.voucher.VoucherOrderInfoEntity;
import com.hungrypanda.payserver.entity.PayOrder;
import com.miller.userapp.module.data.pay.db.*;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PaySucDataCheckServer {
    private SqlSession sqlSession = DBUtils.getDBOfPandaTest();
    private SqlSession sqlPaySession = DBUtils.getDBOfPandaPayTest();
    private OrderSql orderSql = new OrderSql(sqlSession);
    private PayOrderSql payOrderSql = new PayOrderSql(sqlPaySession);
    private UserAccountFlowSql userAccountFlowSql = new UserAccountFlowSql(sqlSession);
    private UserAccountSql userAccountSql = new UserAccountSql(sqlSession);
    private OrderPaymentCombinedSql orderPaymentCombinedSql = new OrderPaymentCombinedSql(sqlSession);
    private MemberOrderSql memberOrderSql= new MemberOrderSql(sqlSession);
    private OrderFastDeliveryBusinessOrderSql orderFastDeliveryBusinessOrderSql = new OrderFastDeliveryBusinessOrderSql(sqlSession);
    private VoucherOrderInfoSql voucherOrderInfoSql = new VoucherOrderInfoSql(sqlSession);
    //分为合单和普通单
    //pay_order表确认
    //order表确认
    //如果补充支付再用户余额表是否为0
    //全额退款确认支付金额与退款金额一致

    /**
     *
     * @param orderSn  订单号
     * @param isCombinationOrder 是否组合订单
     * @param isBalanceSupply 是否余额补充支付
     */
    public List<String> checkPayResult(String orderSn ,boolean isGateWayPay,boolean isCombinationOrder, boolean isBalanceSupply){
        PayOrder payOrder = payOrderSql.getPayOrder(orderSn);
        List<String> msgList = new ArrayList<>();
        if(!isCombinationOrder && isGateWayPay){
            OrderEntity orderEntity = orderSql.getOrderEntity(orderSn);
            if(!payOrder.getAmount().equals(orderEntity.getFinalPrice())){
                msgList.add("订单金额与支付金额不一致");
            }
            if(!payOrder.getTradeNo().equals(orderEntity.getNowPayOrderNo())){
                msgList.add("订单交易号与支付交易号不一致");
            }
            if(!orderEntity.getPayStatus().equals(1)){
                msgList.add("订单支付状态不为1");
            }
            if(isBalanceSupply){
                UserAccountEntity userAccountEntity = userAccountSql.getUserAccountEntity(orderEntity.getUserId());
//                UserAccountFlowEntity userAccountFlowEntity = userAccountFlowSql.getUserAccountFlowEntity(orderEntity.getOrderSn()) //单独没有订单号存储
//                        .stream().filter(a->a.getType() == 1).findAny().get();
                UserAccountFlowEntity userAccountFlowEntity = userAccountFlowSql.getUserAccountFlowEntity(orderEntity.getUserId());
                if(userAccountEntity.getBalance() != 0){
                    msgList.add("网关支付补充支付后，用户余额不为0");
                }
                if(!orderEntity.getAccountBalanceFlowAmount().equals(userAccountFlowEntity.getAmount())){
                    msgList.add("网关支付补充支付后，用户扣减余额与订单余额不一致");
                }
                if(orderEntity.getAccountBalanceFlowAmount() == 0){
                    msgList.add("网关支付补充支付订单存储的余额为0");
                }
            }else {
                if(orderEntity.getAccountBalanceFlowAmount()  != 0 || orderEntity.getAccountBalanceAmount() != 0  ){
                    msgList.add("网关支付非补充支付时，订单余额数值不为0");
                }
            }
        } else if(!isCombinationOrder && !isGateWayPay){
            OrderEntity orderEntity = orderSql.getOrderEntity(orderSn);
            if(!orderEntity.getPayStatus().equals(1)){
                msgList.add("订单支付状态不为1");
            }
            if(orderEntity.getAccountBalanceFlowAmount()  == 0 || orderEntity.getAccountBalanceAmount() == 0  ){
                msgList.add("余额支付时，订单余额数值为0");
            }
            if(orderEntity.getFinalPrice()  != 0 ){
                msgList.add("余额支付时，订单网关金额不为0");
            }
            if(orderEntity.getPayWay()  != 16 ){
                msgList.add("余额支付时，订单支付方式不为16");
            }
//            UserAccountFlowEntity userAccountFlowEntity = userAccountFlowSql.getUserAccountFlowEntity(orderEntity.getOrderSn())
//                    .stream().filter(a->a.getType() == 1).findAny().get();
//            if(!orderEntity.getAccountBalanceFlowAmount().equals(userAccountFlowEntity.getAmount())){
//                msgList.add("网关支付补充支付后，用户扣减余额与订单余额不一致");
//            }
        } else if(isCombinationOrder && isGateWayPay){
            OrderPaymentCombinedEntity orderPaymentCombinedEntity = orderPaymentCombinedSql.getOrderPaymentCombinedEntity(orderSn);
            int totalFixPrice = orderPaymentCombinedEntity.getFixedPrice();
            int totalBalanceAccount = orderPaymentCombinedEntity.getAccountBalanceFlowAmount();
            if(!payOrder.getAmount().equals(orderPaymentCombinedEntity.getFinalPrice())){
                msgList.add("订单金额与支付金额不一致");
            }
            if(!payOrder.getTradeNo().equals(orderPaymentCombinedEntity.getNowPayOrderNo())){
                msgList.add("订单交易号与支付交易号不一致");
            }
            if(!orderPaymentCombinedEntity.getPayStatus().equals(1)){
                msgList.add("订单支付状态不为1");
            }
            List<OrderPaymentCombinedRelationEntity> orderPaymentCombinedRelations = orderPaymentCombinedSql.getOrderPaymentCombinedRelationEntity(orderSn);
            String orderSnSub="";
            String memberOrderSnSub="";
            String voucherOrderSnSub="";
            String fastDeliveryOrderSn="";
            OrderEntity orderEntity = null;
            MemberOrderEntity memberOrderEntity = null;
            VoucherOrderInfoEntity voucherOrderInfoEntity = null;
            OrderFastDeliveryBusinessOrderEntity orderFastDeliveryBusinessOrderEntity = null;
            for(OrderPaymentCombinedRelationEntity orderPaymentCombinedRelation : orderPaymentCombinedRelations){
                switch (orderPaymentCombinedRelation.getOrderSubType()){
                    case 0:
                        orderSnSub = orderPaymentCombinedRelation.getOrderSubSn();
                        orderEntity = orderSql.getOrderEntity(orderSnSub);
                        if(!orderPaymentCombinedRelation.getSubFixedPrice().equals(orderEntity.getFixedPrice())){
                            msgList.add("合单普通外卖子订单金额不一致");
                        }
                        if(isBalanceSupply){ //这里的比较要非日韩支付
                            UserAccountFlowEntity userAccountFlowEntity = userAccountFlowSql.getUserAccountFlowEntity(orderEntity.getUserId(),null); //服务端没存
                            int orderFixPrice = orderPaymentCombinedRelation.getSubFixedPrice();
                            int actualSubBalanceAmount = new BigDecimal(orderFixPrice).divide(new BigDecimal(totalFixPrice),6, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal(totalBalanceAccount)).setScale(2,RoundingMode.HALF_UP).intValue();
                            if(Math.abs(userAccountFlowEntity.getAmount() - actualSubBalanceAmount) > 1){ //相等或者相差无几0.01
                                msgList.add("合单外卖子订单余额金额扣减不一致");
                            }
                            if(!userAccountFlowEntity.getAmount().equals(orderEntity.getAccountBalanceFlowAmount())){
                                msgList.add("合单外卖子订单余额金额不一致");
                            }
                        }
                        break;
                    case 1:
                        voucherOrderSnSub = orderPaymentCombinedRelation.getOrderSubSn();
                        voucherOrderInfoEntity = voucherOrderInfoSql.getVoucherOrderInfoEntity(voucherOrderSnSub);
                        if(!orderPaymentCombinedRelation.getSubFixedPrice().equals(voucherOrderInfoEntity.getOrderAmount())){
                            msgList.add("合单代金券子订单金额不一致");
                        }
                        if(isBalanceSupply){//这里的比较要非日韩支付
                            UserAccountFlowEntity userAccountFlowEntity = userAccountFlowSql.getUserAccountFlowEntity(voucherOrderSnSub)
                                    .stream().filter(a->a.getType() == 1).findAny().get();
                            int orderFixPrice = orderPaymentCombinedRelation.getSubFixedPrice();
                            int actualSubBalanceAmount = new BigDecimal(orderFixPrice).divide(new BigDecimal(totalFixPrice),6, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal(totalBalanceAccount)).setScale(2,RoundingMode.HALF_UP).intValue();
                            if(Math.abs(userAccountFlowEntity.getAmount() - actualSubBalanceAmount) > 1){ //相等或者相差无几0.01
                                msgList.add("合单代金券子订单余额金额扣减不一致");
                            }
                            if(!userAccountFlowEntity.getAmount().equals(voucherOrderInfoEntity.getBalanceFlowAmount())){
                                msgList.add("合单外卖子订单余额金额不一致");
                            }
                        }
                        break;
                    case 2:
                        memberOrderSnSub = orderPaymentCombinedRelation.getOrderSubSn();
                        memberOrderEntity = memberOrderSql.getMemberOrderEntity(memberOrderSnSub);
                        if(!orderPaymentCombinedRelation.getSubFixedPrice().equals(memberOrderEntity.getMemberOrderPrice())){
                            msgList.add("合单会员子订单金额不一致");
                        }
                        if(isBalanceSupply){//这里的比较要非日韩支付
                            UserAccountFlowEntity userAccountFlowEntity = userAccountFlowSql.getUserAccountFlowEntity(memberOrderSnSub)
                                    .stream().filter(a->a.getType() == 1).findAny().get();
                            int orderFixPrice = orderPaymentCombinedRelation.getSubFixedPrice();
                            int actualSubBalanceAmount = new BigDecimal(orderFixPrice).divide(new BigDecimal(totalFixPrice),6, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal(totalBalanceAccount)).setScale(2,RoundingMode.HALF_UP).intValue();
                            if(Math.abs(userAccountFlowEntity.getAmount() - actualSubBalanceAmount) > 1){ //相等或者相差无几0.01
                                msgList.add("合单会员子订单余额金额扣减不一致");
                            }
                            if(!userAccountFlowEntity.getAmount().equals(memberOrderEntity.getAccountBalanceFlowAmount())){
                                msgList.add("合单外卖子订单余额金额不一致");
                            }
                        }
                        break;
                    case 3:
                        fastDeliveryOrderSn = orderPaymentCombinedRelation.getOrderSubSn();
                        orderFastDeliveryBusinessOrderEntity = orderFastDeliveryBusinessOrderSql.getOrderFastDeliveryBusinessOrderEntity(fastDeliveryOrderSn);
                        if(!orderPaymentCombinedRelation.getSubFixedPrice().equals(orderFastDeliveryBusinessOrderEntity.getFixedPrice())){
                            msgList.add("合单优速达子订单金额不一致");
                        }
                        if(isBalanceSupply){//这里的比较要非日韩支付
                            UserAccountFlowEntity userAccountFlowEntity = userAccountFlowSql.getUserAccountFlowEntity(fastDeliveryOrderSn)
                                    .stream().filter(a->a.getType() == 1).findAny().get();
                            int orderFixPrice = orderPaymentCombinedRelation.getSubFixedPrice();
                            int actualSubBalanceAmount = new BigDecimal(orderFixPrice).divide(new BigDecimal(totalFixPrice),6, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal(totalBalanceAccount)).setScale(2,RoundingMode.HALF_UP).intValue();
                            if(Math.abs(userAccountFlowEntity.getAmount() - actualSubBalanceAmount) > 1){ //相等或者相差无几0.01
                                msgList.add("合单优速达子订单余额金额不一致");
                            }
                        }
                    default:
                        break;
                }

            }
            UserAccountEntity userAccountEntity = userAccountSql.getUserAccountEntity(orderEntity.getUserId());
            if(isBalanceSupply){
                if(orderPaymentCombinedEntity.getAccountBalanceFlowAmount()  == 0 || orderPaymentCombinedEntity.getAccountBalanceAmount() == 0  ){
                    msgList.add("网关支付补充支付时，合单订单余额数值为0");
                }
                if(userAccountEntity.getBalance() != 0){
                    msgList.add("网关支付补充支付合单时，用户剩余余额不为0");
                }

            }else {
                if(orderPaymentCombinedEntity.getAccountBalanceFlowAmount()  != 0 || orderPaymentCombinedEntity.getAccountBalanceAmount() != 0  ){
                    msgList.add("网关支付非补充支付时，合单订单余额数值不为0");
                }
            }
        }else if(isCombinationOrder && !isGateWayPay){
            OrderPaymentCombinedEntity orderPaymentCombinedEntity = orderPaymentCombinedSql.getOrderPaymentCombinedEntity(orderSn);
            int totalFixPrice = orderPaymentCombinedEntity.getFixedPrice();
            int totalBalanceAccount = orderPaymentCombinedEntity.getAccountBalanceFlowAmount();
            if(!orderPaymentCombinedEntity.getPayStatus().equals(1)){
                msgList.add("订单支付状态不为1");
            }
            List<OrderPaymentCombinedRelationEntity> orderPaymentCombinedRelations = orderPaymentCombinedSql.getOrderPaymentCombinedRelationEntity(orderSn);
            String orderSnSub="";
            String memberOrderSnSub="";
            String voucherOrderSnSub="";
            String fastDeliveryOrderSn="";
            OrderEntity orderEntity = null;
            MemberOrderEntity memberOrderEntity = null;
            VoucherOrderInfoEntity voucherOrderInfoEntity = null;
            OrderFastDeliveryBusinessOrderEntity orderFastDeliveryBusinessOrderEntity = null;
            for(OrderPaymentCombinedRelationEntity orderPaymentCombinedRelation : orderPaymentCombinedRelations){
                int orderFixPrice = orderPaymentCombinedRelation.getSubFixedPrice();
                int actualSubBalanceAmount = new BigDecimal(orderFixPrice).divide(new BigDecimal(totalFixPrice),6, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(totalBalanceAccount)).setScale(2,RoundingMode.HALF_UP).intValue();
                switch (orderPaymentCombinedRelation.getOrderSubType()){
                    case 0:
                        orderSnSub = orderPaymentCombinedRelation.getOrderSubSn();
                        orderEntity = orderSql.getOrderEntity(orderSnSub);
                        if(!orderPaymentCombinedRelation.getSubFixedPrice().equals(orderEntity.getFixedPrice())){
                            msgList.add("合单普通外卖子订单金额不一致");
                        }
                         //这里的比较要非日韩支付
                        UserAccountFlowEntity userAccountFlowEntity0 = userAccountFlowSql.getUserAccountFlowEntity(orderEntity.getUserId(),null);



                        if(Math.abs(userAccountFlowEntity0.getAmount() - actualSubBalanceAmount) > 1){ //相等或者相差无几0.01
                                msgList.add("合单外卖子订单余额金额扣减不一致");
                        }
                        if(!userAccountFlowEntity0.getAmount().equals(orderEntity.getAccountBalanceFlowAmount())){
                                msgList.add("合单外卖子订单余额金额不一致");
                        }

                        break;
                    case 1:
                        voucherOrderSnSub = orderPaymentCombinedRelation.getOrderSubSn();
                        voucherOrderInfoEntity = voucherOrderInfoSql.getVoucherOrderInfoEntity(voucherOrderSnSub);
                        if(!orderPaymentCombinedRelation.getSubFixedPrice().equals(voucherOrderInfoEntity.getOrderAmount())){
                            msgList.add("合单代金券子订单金额不一致");
                        }
                        //这里的比较要非日韩支付
                        UserAccountFlowEntity userAccountFlowEntity1 = userAccountFlowSql.getUserAccountFlowEntity(voucherOrderSnSub)
                                    .stream().filter(a->a.getType() == 1).findAny().get();

                        if(Math.abs(userAccountFlowEntity1.getAmount() - actualSubBalanceAmount) > 1){ //相等或者相差无几0.01
                                msgList.add("合单代金券子订单余额金额扣减不一致");
                        }
                        if(!userAccountFlowEntity1.getAmount().equals(voucherOrderInfoEntity.getBalanceFlowAmount())){
                                msgList.add("合单外卖子订单余额金额不一致");
                        }

                        break;
                    case 2:
                        memberOrderSnSub = orderPaymentCombinedRelation.getOrderSubSn();
                        memberOrderEntity = memberOrderSql.getMemberOrderEntity(memberOrderSnSub);
                        if(!orderPaymentCombinedRelation.getSubFixedPrice().equals(memberOrderEntity.getMemberOrderPrice())){
                            msgList.add("合单会员子订单金额不一致");
                        }
                        //这里的比较要非日韩支付
                        UserAccountFlowEntity userAccountFlowEntity2 = userAccountFlowSql.getUserAccountFlowEntity(memberOrderSnSub)
                                    .stream().filter(a->a.getType() == 1).findAny().get();
                        if(Math.abs(userAccountFlowEntity2.getAmount() - actualSubBalanceAmount) > 1){ //相等或者相差无几0.01
                                msgList.add("合单会员子订单余额金额扣减不一致");
                        }
                        if(!userAccountFlowEntity2.getAmount().equals(memberOrderEntity.getAccountBalanceFlowAmount())){
                                msgList.add("合单外卖子订单余额金额不一致");
                        }

                        break;
                    case 3:
                        fastDeliveryOrderSn = orderPaymentCombinedRelation.getOrderSubSn();
                        orderFastDeliveryBusinessOrderEntity = orderFastDeliveryBusinessOrderSql.getOrderFastDeliveryBusinessOrderEntity(fastDeliveryOrderSn);
                        if(!orderPaymentCombinedRelation.getSubFixedPrice().equals(orderFastDeliveryBusinessOrderEntity.getFixedPrice())){
                            msgList.add("合单优速达子订单金额不一致");
                        }
                        //这里的比较要非日韩支付
                        UserAccountFlowEntity userAccountFlowEntity3 = userAccountFlowSql.getUserAccountFlowEntity(fastDeliveryOrderSn)
                                    .stream().filter(a->a.getType() == 1).findAny().get();
                        if(Math.abs(userAccountFlowEntity3.getAmount() - actualSubBalanceAmount) > 1){ //相等或者相差无几0.01
                                msgList.add("合单优速达子订单余额金额不一致");
                        }

                    default:
                        break;
                }

            }
            if(orderPaymentCombinedEntity.getAccountBalanceFlowAmount()  == 0 || orderPaymentCombinedEntity.getAccountBalanceAmount() == 0  ){
                msgList.add("余额支付时，合单订单余额数值为0");
            }
            if(orderPaymentCombinedEntity.getFinalPrice()  == 0  ){
                msgList.add("余额支付时，合单网关金额数值为0");
            }

        }
        return msgList;
    }







}
