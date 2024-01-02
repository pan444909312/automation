package com.miller.data.center.user;

/**
 * 多个测试用例之间的共享数据
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

    public static Long shopId = 59750820L;
}
