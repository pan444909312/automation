package com.miller.bdm;

import com.miller.bdm.app.privateShop.add.AddPrivateShopTests;
import com.miller.bdm.app.privateShop.callback.SignCallBackTests;
import com.miller.bdm.app.privateShop.formula.GetTemplatesTests;
import com.miller.bdm.app.privateShop.list.PrivateShopTests;
import com.miller.bdm.app.privateShop.sign.SignTests;
import com.miller.bdm.app.publicShop.list.PublicShopTests;
import com.miller.bdm.app.shopCategory.ShopCategoryTests;
import com.miller.bdm.app.shopCity.ShopCityTests;
import com.miller.bdm.app.shopKpRole.ShopKPRoleTests;
import com.miller.bdm.app.shopTag.ShopTagTests;
import com.miller.bdm.login.ERPLoginTests;
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
        ERPLoginTests.class,
        //私海池商家列表
        PrivateShopTests.class,
        //公海池商家列表
        PublicShopTests.class,
        //商家标签列表
        ShopTagTests.class,
        //商家KP角色列表
        ShopKPRoleTests.class,
        //商家城市列表
        ShopCityTests.class,
        //商家类目列表
        ShopCategoryTests.class,
        //
        GetTemplatesTests.class,
        //私海池商家创建
        AddPrivateShopTests.class,
        //发起签约
        SignTests.class,
        //签约回调相关
        SignCallBackTests.class,

})
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {

}
