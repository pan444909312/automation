package com.miller.erp;

import com.miller.erp.login.LoginTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 当需要执行多个测试类时可以通过此程序进行调试
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 18:57:00
 */
@SelectClasses({
        // ERP-登录
        LoginTests.class,

})
//@SelectPackages("com.miller.erp")
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {
}
