package com.miller.data.center.user;

/**
 * 用户基本信息数据。
 * 测试用例需要用到的数据直接通过初始化脚本完成，而在代码中仅保存最少需要的关键数据ID。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 13:58:45
 */
public class TestCaseDataForUserConstant {

    /**
     * 订单响应对象的 Key, 用于从缓存中读取数据
     */
    public static final String ORDER_ID_OBJECT_KEY = "ORDER_ID_OBJECT_KEY";

    /**
     * 用户余额支付密码
     */
    public static final String payPassword = "123456";

    /**
     *
     * 用户收货地址ID。
     * 地址: China, 江西省九江市浔阳区 Lufeng Road, 九江学院
     * 纬度: 29.720049
     * 经度: 115.998825
     * 邮编: 332000
     */
    public static final Long addressId = 1398663384L;

    /**
     * 会员城市ID。
     * 会员卡类型：月卡。
     * 城市：九江
     * 会费：10元。
     * 自动续费-续费价：10元。
     */
    public static final Long memberCityId = 1111378L;

}

