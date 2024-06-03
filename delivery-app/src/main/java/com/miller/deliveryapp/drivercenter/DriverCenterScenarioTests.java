package com.miller.deliveryapp.drivercenter;

import com.miller.deliveryapp.drivercenter.agreement.AgreementListTests;
import com.miller.deliveryapp.drivercenter.bankinfo.BankInfoListTests;
import com.miller.deliveryapp.drivercenter.info.DriverInfoTests;
import com.miller.deliveryapp.login.DeliveryLoginByDefaultAccountTests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 场景：骑手登录app，获取骑手个人信息，银行卡列表信息，骑手同意的协议列表信息
 *
 * @author penglulu
 * @version 1.0
 * @since 2024/04/25 17:30:01
 */
@SelectClasses({
        // 用户登录
        DeliveryLoginByDefaultAccountTests.class,
        // 获取骑手个人信息
        DriverInfoTests.class,
        // 获取骑手银行卡列表信息
        BankInfoListTests.class,
        // 获取骑手同意的协议列表
        AgreementListTests.class,

})
@Suite
@SuiteDisplayName("骑手登录app，获取骑手个人信息，银行卡列表信息，骑手同意的协议列表信息")
//@TestCase(testCaseID = "xxxxxxxxx", name = "骑手登录app，获取历史订单列表，并进入一个订单的详情页")
public class DriverCenterScenarioTests {
}
