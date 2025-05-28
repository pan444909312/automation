package com.miller.testcaseuserapp.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 操作 pandapay_test 库
 *
 * @author Miller Shan
 * @version 1.0.0
 * @since 2025/05/27 23:03:44
 */
@Slf4j
public class PayTestDBHelpful extends PandaTestDBHelpful {
    /**
     * 数据库配置
     */
    private static String mySqlUrl = "jdbc:mysql://hp-polar-test-business-master-pub.mysql.polardb.rds.aliyuncs.com:3306/pandapay_test";

}
