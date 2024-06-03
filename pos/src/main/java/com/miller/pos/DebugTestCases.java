package com.miller.pos;


import com.miller.pos.login.PosLoginTests;
import com.miller.pos.product.addproduct.AddProductTests;
import com.miller.pos.product.addproductdemo.AddProductDemoTests;
import com.miller.pos.product.deleteproduct.DelProductTests;
import com.miller.pos.product.editproduct.EditProductTests;
import com.miller.pos.product.queryproduct.QueryProductTests;
import com.miller.pos.shop.queryshop.QueryShopTests;
import com.miller.pos.shop.queryshoplist.QueryShopListTests;
import com.miller.pos.shop.statusshop.StatusShopTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 当需要执行多个测试类时可以通过此程序进行调试
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/06 18:57:00
 */
@SelectClasses({
        // pos登录获取token
        PosLoginTests.class,
//        QueryShopTests.class,
//        QueryShopListTests.class,
//        StatusShopTests.class
        AddProductDemoTests.class,
//        EditProductTests.class,
//        QueryProductTests.class,
//        DelProductTests.class
})
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {
}
