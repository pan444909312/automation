package com.miller.userapp.module.data.pay;

import java.util.List;

public class PaySucDataCheck {
    private static String orderSn = "HD423449071022976269"; //需要核对的订单号，合单的话是合单号
    private static boolean isGateWay = true;
    private static boolean isBalanceSupply = true;
    public static void main(String[] args){
        PaySucDataCheckServer paySucDataCheckServer = new PaySucDataCheckServer();
        boolean isCombinedOrder = false;
        if(orderSn.startsWith("HD")){
            isCombinedOrder = true;
        }
        List<String> result  = paySucDataCheckServer.checkPayResult(orderSn,isGateWay,isCombinedOrder,isBalanceSupply);
        result.stream().forEach(System.out::println);

    }
}
