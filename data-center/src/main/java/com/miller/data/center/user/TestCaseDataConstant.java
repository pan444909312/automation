package com.miller.data.center.user;

/**
 * 多个测试用例之间的共享数据.
 * 测试用例需要用到的数据直接通过初始化脚本完成，而在代码中仅保存最少需要的关键数据ID。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 13:58:45
 */
public class TestCaseDataConstant {

    /**
     * 订单响应对象的 Key， 用于从缓存中读取数据
     */
    public static final String ORDER_ID_OBJECT_KEY = "ORDER_ID_OBJECT_KEY";

    /**
     * 自动化测试专用商品ID
     */
    public static Long productId = 81669204L;

    /**
     * 自动化测试专用店铺ID
     */
    public static Long shopId = 59750820L;
}
