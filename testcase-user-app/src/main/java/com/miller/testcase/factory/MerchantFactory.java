package com.miller.testcase.factory;

import com.miller.testcase.utils.TestCaseHelpful;

/**
 * 创建商家的数据工厂类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/5/27 23:04:49
 */
public class MerchantFactory {
    /**
     * true: 编辑商家；false:创建商家。如果为false则使用指定的 ShopId 对商家进行编辑操作。
     */
    private static final boolean isEditMerchant = false;
    private final long shopIdForDebug = 970301088;

    public static void main(String[] args) {
        String erpToken = TestCaseHelpful.erpLogin();
        System.out.println(erpToken);

    }
}
