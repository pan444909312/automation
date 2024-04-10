package com.miller.userapp.order.shopping.settlement.provider;

import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.userapp.order.shopping.settlement.request.CouponCheckOutRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 消费券结算
 */
public class CouponCheckoutDataProvider {

    static Stream<Arguments> couponCheckout(){
        CouponCheckOutRequestDTO couponCheckOutRequestDTO=new CouponCheckOutRequestDTO();
        couponCheckOutRequestDTO.setVoucherSn(TestCaseDataForUserConstant.voucherSn);
        couponCheckOutRequestDTO.setBuyNum(1);
        return Stream.of(Arguments.of(couponCheckOutRequestDTO));
    }
}
