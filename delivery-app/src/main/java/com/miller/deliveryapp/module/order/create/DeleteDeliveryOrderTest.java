package com.miller.deliveryapp.module.order.create;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.db.DBUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.miller.service.framework.asserts.AssertUtils.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("逻辑删除sql插入的订单")
public class DeleteDeliveryOrderTest {
    private static final String mySqlUrl = "jdbc:mysql://rm-3ns24734o9z8747d0jo.mysql.rds.aliyuncs.com/panda_test";
    private static final String userName = "panda_test";
    private static final String passWord = "Pan$te19*";
    private static DBUtils dbUtils;
    @BeforeAll
    public static void beforeAll() {
        // 初始化，链接数据库
        dbUtils = new DBUtils(mySqlUrl, userName, passWord);
    }

    @DisplayName("逻辑删除通过sql插入的订单")
    @Test
    public void deleteOrderBySql() {

        String orderSn = CacheUtils.get("orderSnByDeliverySql").toString();
        //更新 hp_delivery_order 表is_del=1 逻辑删除订单
        String updateDeliveryOrder = "UPDATE `hp_delivery_order` SET `is_del` = 1 WHERE (`order_sn` = ?)";
        Integer updateDeliveryOrderResult = dbUtils.executeInsertOrUpdateOrDelete(updateDeliveryOrder, orderSn);
        assertThat(updateDeliveryOrderResult, Matchers.greaterThanOrEqualTo(0));

        //更新 order 表is_del=1 逻辑删除订单
        String updateOrder = "UPDATE `order` SET `is_del` = 1 WHERE (`order_sn` = ?)";
        Integer updateOrderResult = dbUtils.executeInsertOrUpdateOrDelete(updateOrder, orderSn);
        assertThat(updateOrderResult, Matchers.greaterThanOrEqualTo(0));
    }

}
