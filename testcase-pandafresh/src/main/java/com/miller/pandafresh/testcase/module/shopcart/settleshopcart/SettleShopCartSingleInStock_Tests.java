package com.miller.pandafresh.testcase.module.shopcart.settleshopcart;

import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartPreSaleGoods;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.JSONUtils;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import java.util.List;
import java.util.Map;

/**
 * settleShopCart
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/07/28 13:51:03
 */

@SelectClasses({
        AddShopCartOrdinaryGoods.class,
        SettleShopCartSingleInStock.class
})
@DisplayName("购物车-去结算-结算单个现货商品")
@Suite
public class SettleShopCartSingleInStock_Tests {


} 