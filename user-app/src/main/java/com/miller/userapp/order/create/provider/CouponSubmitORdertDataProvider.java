package com.miller.userapp.order.create.provider;

import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.userapp.order.create.request.CouponSubmitOrderRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 消费券结算
 */
public class CouponSubmitORdertDataProvider {

    static Stream<Arguments> couponCheckout(){
        CouponSubmitOrderRequestDTO couponSubmitOrderRequestDTO=new CouponSubmitOrderRequestDTO();
        couponSubmitOrderRequestDTO.setVoucherSn(TestCaseDataForUserConstant.voucherSn);
        couponSubmitOrderRequestDTO.setBuyNum(1);
        return Stream.of(Arguments.of(couponSubmitOrderRequestDTO));
    }
}
