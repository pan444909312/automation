package com.miller.userapp.module.person.address;

import com.miller.userapp.module.person.address.create.AddressAddTests;
import com.miller.userapp.module.person.address.create.AddressDeleteTests;
import com.miller.userapp.module.person.address.create.AddressEditTests;
import com.miller.userapp.module.person.address.list.AddressListTests;
import com.miller.userapp.module.person.address.list.AddressSearchConfirmTests;
import com.miller.userapp.module.person.address.list.AddressSearchTests;
import com.miller.userapp.module.person.address.list.LocaitionChangeTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * @author panjuxiang
 * @since 2024/6/7 10:06
 */

@SelectClasses({
        AddressListTests.class,
        AddressSearchTests.class,
        AddressSearchConfirmTests.class,
        LocaitionChangeTests.class,
        AddressAddTests.class,
        AddressEditTests.class,
        AddressDeleteTests.class

})
@SuiteDisplayName("调试多个地址测试用例")
@Suite
public class AddressSuiteTestCases {
}
