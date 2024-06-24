package com.miller.data.center.merchant;

/**
 * 商家相关信息的测试数据。
 * 测试用例需要用到的数据直接通过初始化脚本完成，而在代码中仅保存最少需要的关键数据ID。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 13:58:45
 */
public class TestCaseDataForMerchantConstant {

    // 第一组数据：普通商家。
    /**
     * 自动化测试专用店铺。
     * "shopId": 59750820,
     * "shopName": "东东测试商家·订单测试·自动化测试·勿动",
     */
    public static Long shopId = 59750820L;

    /**
     * 自动化测试专用商品。
     * "productId": 81669204,
     * "productName": "乔治",
     * "productPrice": 10000,
     * "orgProductPrice": 10000,
     * "promoteRate": 10.0,
     */
    public static Long productId = 81669204L;
    public static Long skuId = 0L;
    /**
     * 自动化测试专用外卖代金券
     * 券ID: 8201ZEFNW2
     * 券类型: 外卖代金券（线上）
     * 关联店铺: 东东测试商家·订单测试·自动化测试·勿动，"shopId": 59750820,
     * 券内商家名称: 满10减5
     * 单个面值: 10
     * 门槛: 无门槛
     * 数量: 1
     * 单个售价: 5
     * 总售价: 5
     * 生效时间: 999
     * 每日单用户限购: 无限
     * 单个分摊（金额）: 平台承担 0 商家承担 0
     * 平台抽成: 无
     * 券消费税: 无
     * 运营活动: 东东测试代金券
     */
    public static String voucherSn = "8201ZEFNW2";

    // 第二组数据：美食城商家。
    /**
     * 自动化测试专用店铺。
     * "shopId": 638845643,
     * "shopName": "东东测试美食城·订单测试·自动化测试·勿动",
     */
    public static Long shopIdOfFoodCity = 638845643L;

    /**
     * 美食城，东东测试·档口1，商品1信息
     * "productId": "81742258",
     * "productName": "艾莎(艾莎)",
     * "productCount": 1,
     * "productSkuId": "37431304",
     * "tagName": "艾莎",
     * "productTagIdExt": null,
     * "totalProductPrice": 10000,
     * "totalOriginalPrice": 10000,
     * "minProductPrice": 10000,
     */
    public static Long productIdOfFoodCity1 = 81742258L;
    public static Long skuIdOfFoodCity1 = 0L;
    /**
     * 美食城，东东测试·档口2，商品2信息
     * "productId": "81744208",
     * "productName": "敞篷车(敞篷车)",
     * "productCount": 1,
     * "productSkuId": "37433438",
     * "tagName": "敞篷车",
     * "productTagIdExt": null,
     * "totalProductPrice": 10000,
     * "totalOriginalPrice": 10000,
     * "minProductPrice": 10000,
     */
    public static Long productIdOfFoodCity2 = 81744208L;
    public static Long skuIdOfFoodCity2 = 0L;


}
