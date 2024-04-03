package com.miller.bdm.constants;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 业务配置常量
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 17:27:34
 */
public class BusinessConstantOfERP {
    /**
     * 接口请求的域名
     */
    public static final String DOMAIN_TEST_GATEWAY = "https://test-gateway.hungrypanda.cn";

    /**
     * 私海池对应的ID
     */
    public static final String PRIVATE_SHOP_ID_KEY = "PRIVATE_SHOP_ID_KEY";

    /**
     * 緩存
     * 品類數據
     */
    public static final String SHOP_CATEGORY_KEY = "SHOP_CATEGORY_KEY";

    /**
     * 緩存
     * 商家標籤數據
     */
    public static final String SHOP_TAG_KEY = "SHOP_TAG_KEY";


    /**
     * 緩存
     * 商家城市數據
     */
    public static final String SHOP_CITY_KEY = "SHOP_CITY_KEY";


    /**
     * 緩存
     * 商家KP角色
     */
    public static final String SHOP_KP_ROLE_KEY = "SHOP_KP_ROLE_KEY";


    /**
     * 私海池&公海池查询或私海池新增对应的城市
     */
    public static final String TEST_CITY = "杭州市";

    /**
     * ERP 账号
     */
    public static final String USERNAME = "lipan";
    /**
     * ERP 账号密码，MD5 加密
     */
    public static final String PASSWORD = "3e6c7d141e32189c917761138b026b74";

    /**
     * token: ERP 认证令牌。直接使用token作为默认值，保持名称和值一致，后续名称修改只需要改一个地方。
     */
    public static String TOKEN = "token";

    /**
     * ERP 后台老的项目接口请求域名。
     * 前后端不分离的项目，代码工程在 platform
     */
    public static final String DOMAIN_TEST_BACKUP = "https://platform-test-backup.hungrypanda.cn";


    /**
     * 私海池新增需要的数据
     */
    public static final String Email = "lipan1009@163.om";
    public static final String Iphone = "15010079202";
    public static final String KPName = "自动化";
    public static final String PrivateShopName = "测试自动化私海池名称";
    public static final String Code = "86";
    public static final String Lang = "1";
    public static final double Latitude = 30.209841;
    public static final double Longitude = 120.214462;
}
