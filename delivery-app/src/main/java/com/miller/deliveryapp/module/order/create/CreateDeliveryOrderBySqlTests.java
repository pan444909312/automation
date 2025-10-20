package com.miller.deliveryapp.module.order.create;


import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.db.DBUtils;
import com.miller.service.framework.util.PropertiesUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
@EnvTag.Test
@TestFramework
@DisplayName("通过sql插入创建订单")
public class CreateDeliveryOrderBySqlTests {

    private static final String mySqlUrl = new PropertiesUtils().getProperty(DeleteDeliveryOrderTest.class, "datasource.url.panda_test");
    private static final String userName = new PropertiesUtils().getProperty(DeleteDeliveryOrderTest.class, "datasource.username.panda_test");
    private static final String passWord = new PropertiesUtils().getProperty(DeleteDeliveryOrderTest.class, "datasource.password.panda_test");
    private static DBUtils dbUtils;
    @BeforeAll
    public static void beforeAll() {
        // 初始化，链接数据库
        dbUtils = new DBUtils(mySqlUrl, userName, passWord);
    }

    @DisplayName("通过sql插入创建订单")
    @Test
    public void createOrderBySql() {


        //获取当前时间戳
        long nowTime = System.currentTimeMillis();

        //获取当前时间戳+20分钟
        long futureTime = nowTime + 1200000;

        //获取当前年月日
        LocalDate nowDate = LocalDate.now();

        //获取 未来20分钟后 年月日 时分秒
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatFuture = simpleDateFormat.format(futureTime);


        String orderSn="TESTAUTO"+ nowTime +"";
        String dispatchOrderSn ="DHP"+ nowTime +"";
        Long deliveryOrderId;
        Long orderId;
        Long deliveryId;
        //反向的ordersn
        String orderSnInversion=new StringBuilder(orderSn).reverse().toString();


        //插入hp_delivery_order表数据
        String insertDeliveryOrder ="INSERT INTO `hp_delivery_order`( `dispatch_order_sn`, `order_sn`, `create_time`, `update_time`, `order_remark`, `is_del`, `order_status`, `pay_type`, `pay_time`, `country`, `order_city`, `dispatch_city`, `site_id`, `site_name`, `order_area_id`, `order_area_name`, `user_area_id`, `user_area_name`, `tobacco_alcohol_type`, `tobacco_alcohol_verify_result`, `tobacco_alcohol_comment`, `user_first_order`, `transport_capacity`, `fix_order_date`, `fix_point_time_link_id`, `fix_point_time_id`, `shop_user_distance`, `shop_user_distance_map`, `delivery_shop_user_distance`, `delivery_shop_user_distance_map`, `driver_shop_distance`, `add_source`, `order_business_type`, `delivery_platform`, `delivery_type`, `distance_type`, `order_type`, `special_driver_type`, `delivery_status`, `delivery_detail_status`, `merchant_status`, `merchant_take_order_time`, `merchant_estimated_out_meal`, `merchant_estimated_out_meal_time`, `merchant_real_out_meal_time`, `delivery_need_time_start`, `delivery_need_time_end`, `estimated_delivery_time_start`, `estimated_delivery_time_end`, `delivery_time`, `estimated_arrived_time`, `third_distribution`, `estimated_on_shop_time`, `driver_id`, `driver_name`, `driver_phone`, `dispatch_status`, `dispatch_pool_type`, `driver_visibility_type`, `dispatch_channel`, `receipt_channel`, `rider_order_time`, `on_shop_time`, `take_meal_time`, `arrived_time`, `delivery_timeout_time`, `area_timeout_time`, `algorithm_press_order_time`, `shop_id`, `shop_name`, `shop_type`, `shop_address`, `shop_notes`, `shop_longitude`, `shop_latitude`, `shop_postcode`, `shop_house_num`, `shop_service_phone`, `customer_user_id`, `customer_address_id`, `customer_name`, `customer_phone`, `customer_phone_remark`, `customer_location`, `customer_address_remark`, `customer_house_number`, `customer_postcode`, `customer_longitude`, `customer_latitude`, `customer_language_code`, `tableware_price`, `customer_delivery_price`, `customer_tip_price`, `total_price`, `cod_shop_price`, `cod_user_price`, `dispatch_price`, `cross_shop_pricing_price`, `rider_wage`, `customer_settlement_amount`, `operation_pool_method`, `current_auto_status`, `create_date`, `arrived_date`, `estimated_arrived_date`, `on_time_shop_type`, `on_time_arrived_type`, `driver_code`, `customer_timeout_sensitivity`, `nearby_customer_time`, `shop_grade`, `user_on_time_arrived_type`, `nd_time`, `batch_no`, `deliverable_action`, `deliverable_remark`, `order_arrive_method`, `activity_type`, `additional_business`, `customer_phone_protect`, `customer_phone_mask`) VALUES ( '"+dispatchOrderSn+"', '"+orderSn+"', "+nowTime+", "+nowTime+", '', 0, 0, 0, "+nowTime+", '中国', '杭州市', '杭州市', 0, '', 0, '', 0, '', 0, 0, '', 0, '', 0, 0, 0, '1.23', 3, 1.23, 2, 0.00, 0, 0, 0, 0, 1, 0, 0, 1, 10, 2, "+nowTime+", '"+formatFuture+"', "+futureTime+", 0, 3, 5, "+futureTime+", "+futureTime+", '尽快送达', "+futureTime+", 0, "+futureTime+", 0, '', '', 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 206704044, '杭州-陈春霞7 烟酒 店铺-自动化测试店铺', 0, '中华人民共和国浙江省杭州市中国浙江省杭州市滨江区江陵路1740号310051杭州骏宝行汽车销售服务有限公司', '', '120.2177029642', '30.2035623745', '310051', '', '86444444', 1398709544, 1398664400, '陈春霞1', '+821zbhu01632', '13251016321', '杭州骏宝行汽车销售服务有限公司, 中国浙江省杭州市滨江区江陵路1740号, Hangzhou, Zhejiang  Peoples Republic of China', '', '88888', '88888', '120.212907', '30.209024', 'CN', 0.00, 2.11, 0.22, 20.00, 20.00, 77.72, 0.00, 0.00, 0.00, 77.72, 0, 0, '"+nowDate+"', '', '"+nowDate+"', 0, 0, '', 0, 0, 0, 0, "+futureTime+", '', 2, '', 0, 0, 0, 1, '+821zbhu01632')";
        Integer insertDeliveryOrderResult = dbUtils.getJdbcTemplate().update(insertDeliveryOrder);
        assertThat(insertDeliveryOrderResult, Matchers.greaterThan(0));

        //获取 hp_delivery_order 表 主ID
        String selectDeliveryOrder = "SELECT * FROM hp_delivery_order where order_sn = ?";
        Map<String, Object> deliveryOrderResult = dbUtils.queryOneObjectReturnMap(selectDeliveryOrder, orderSn);
        deliveryOrderId = Long.valueOf(deliveryOrderResult.get("id").toString());


        //插入order表
        String insertOrder ="INSERT INTO `order`( `order_sn`, `shop_id`, `special_order_id`, `user_id`, `address_id`, `first_discount`, `full_sub_id`, `total_price`, `fixed_price`, `delivery_id`, `order_status`, `remark`, `create_time`, `update_time`, `is_del`, `total_buy_count`, `isPending`, `delivery_time`, `tableware_count`, `is_online_pay`, `pay_status`, `rebate_status`, `is_evaluate`, `order_status_desk`, `make_time`, `order_type`, `delivery_type`, `pay_way`, `source_type`, `order_product_name`, `nowPayOrderNo`, `nowPayMessage`, `tradeStatus`, `packingCharges`, `is_print`, `print_resp`, `pay_resp`, `order_resp`, `city`, `country`, `preferential_price`, `out_meal_time`, `ip`, `order_shop_type`, `red_packet_id`, `isBigRedPacket`, `to_shop_distance`, `is_exception`, `now_pay_way`, `is_free_delivery`, `is_member_order`, `tax_rate_price`, `tax_rate`, `estimated_arrival_time`, `mer_tax_price`, `mer_full_sub_id`, `mer_preferential_price`, `mer_total_price`, `mer_fixed_price`, `financial_change_tag`, `member_discount`, `floating_rate`, `final_price`, `account_balance_amount`, `account_balance_flow_amount`, `is_meal_ready`, `unalterable`, `consignee_tel`, `consignee_full_address`, `consignee_name`, `consignee_house_number`, `consignee_zip_code`, `fix_point_time_link_id`, `fix_order_date`, `fix_point_time_id`, `has_discount`, `dining_out_time`, `order_status_new_for_merchant`, `order_status_new`, `cancel_reason`, `estimated_need_time_start`, `estimated_need_time_end`, `delivery_need_time_start`, `delivery_need_time_end`, `address_flag`, `merchant_order_take_time`, `order_arrived_time`, `delivery_begin_time`, `income`, `manual_authorized_fraud_suspicion`, `is_second`, `parent_order_sn`, `first_discount_platform`, `first_discount_merchant`, `full_sub_platform`, `full_sub_merchant`, `delivery_price_discount_merchant`, `delivery_price_discount_platform`, `area_id`, `area_name`, `order_sn_inversion`, `faraway_stop_time`, `floating_amount`, `floating_type`, `platform_fee`, `platform_fee_rate`, `platform_fee_type`, `tableware_price`, `order_mark_type`, `order_new_user_mark_type`, `consignee_longitude`, `consignee_latitude`, `user_take_discount_sum_amount`, `user_take_shop_discount_amount`, `language_code`, `full`, `mer_full`, `small_order_fee`, `small_order_fee_config`, `plastic_amount`, `pay_time`, `push_time`, `first_print_time`, `merchant_out_meal_time`, `distance_source_type`, `order_intercept_time`, `address_remark`, `channel_fee_amount`, `burst_order_mark`, `add_source`, `order_business_type`, `distance_type`, `need_number_masking`, `consignee_tel_mask`) VALUES  ( '"+orderSn+"', 206704044, NULL, 1398709544, 1398664400, 0, 0, 2000, 7772, 4207114, 4, '', "+nowTime+", "+nowTime+", 0, 1, 100, '尽快送达', 1, 0, 1, -1, 0, 2, 15, 0, 1, 16, 1, NULL, '', '"+orderSn+"', 'SUCCESS', 0, NULL, NULL, NULL, NULL, '杭州市', '中国', 0, '"+formatFuture+"', '157.15.28.49', NULL, 0, 0, '1.23', 0, 0, 0, 0, 0, '0', 1140000, 0, 0, 0, 2000, 7772, 0, 0, 1, 0, 7772, 7772, 0, 0, '+821zbhu01632', '杭州骏宝行汽车销售服务有限公司, 中国浙江省杭州市滨江区江陵路1740号, Hangzhou, Zhejiang  Peoples Republic of China', '陈春霞1', '88888', '88888', 0, 0, 0, 0, 15, 15, 15, NULL, "+futureTime+", "+futureTime+", 3, 5, 0, "+nowTime+", 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 51, '滨江区', '"+orderSnInversion+"', '', 0, 0, 0, 0, 0, 0, 0, 0, '120.212907', '30.209024', 0, 0, 'CN', 0, 0, 0, 0, 0, "+nowTime+", "+nowTime+", 0, 0, 3, 0, '', 0, 0, 0, 0, 1, 0, '+821zbhu01632')";
        Integer insertOrderResult = dbUtils.getJdbcTemplate().update(insertOrder);
        assertThat(insertOrderResult, Matchers.greaterThan(0));

        // 获取 order 表 主ID
        String selectOrder = "SELECT * FROM `order` where order_sn = ?";
        Map<String, Object> orderResult = dbUtils.queryOneObjectReturnMap(selectOrder, orderSn);
        orderId = Long.valueOf(orderResult.get("order_id").toString());
        //插入 delivery 表
        String insertDelivery ="INSERT INTO `delivery`( `order_id`, `dispatch_status`, `shop_id`, `user_id`, `delivery_status`, `delivery`, `create_time`, `update_time`, `is_del`, `delivery_name`, `delivery_phone`, `deliveryLocation`, `deliveryLong`, `deliveryLa`, `delivery_status_desk`, `tip_price`, `tip_price_rate`, `rider_wage`, `order_status_new`, `source`, `driver_shop_distance`, `verify_result`, `comment`, `calc_distance_status`, `dispatch_pool_type`, `driver_visibility_type`, `is_timeout`, `timeout_time`, `order_salary_increase_type`, `order_salary_increase`, `increase_price`, `reduction_price`, `cross_shop_pricing_price`, `rider_order_time`, `dispatch_channel`, `shortest_distance`, `delivery_map_resource`, `platform_delivery_price`, `take_meal_time`, `transport_capacity`, `area_timeout_time`, `third_distribution`, `estimate_arrive_shop_time`, `area_id`, `area_name`, `arrived_time`, `receipt_channel`, `delivery_need_time`, `settlement_status`, `algorithm_press_order_time`, `operation_pool_method`, `deliverable_action`, `deliverable_remark`, `take_meal_code`) VALUES ( "+orderId+", 0, 206704044, 0, 1, 211, "+nowTime+", "+nowTime+", 0, '', NULL, NULL, NULL, NULL, 1, 22, '0', 0, NULL, 1, 0.00, 0, '', 0, 2, 3, 0, 0, 0, 0.00, 0.00, 0.00, 0.00, 0, 0, 1.23, 2, 211, 0, '', 0, 0, 0, 0, '', 0, 0, "+futureTime+", 0, 0, 0, 2, '', '')";
        Integer insertDeliveryResult = dbUtils.getJdbcTemplate().update(insertDelivery);
        assertThat(insertDeliveryResult, Matchers.greaterThan(0));

        // 获取 delivery 表 主ID
        String selectDelivery = "SELECT * FROM delivery where order_id = ?";
        Map<String, Object> deliveryResult = dbUtils.queryOneObjectReturnMap(selectDelivery, orderId);
        deliveryId = Long.valueOf(deliveryResult.get("delivery_id").toString());


        //更新order 表delivery_id 字段的值
        String sql = "UPDATE `order` SET `delivery_id` = "+deliveryId+" WHERE (`order_sn` = ?)";
        Integer updateOrderResult = dbUtils.executeInsertOrUpdateOrDelete(sql, orderSn);
        assertThat(updateOrderResult, Matchers.greaterThanOrEqualTo(0));

        //插入 order_detail 表
        String insertOrderDetail ="INSERT INTO `order_detail`( `order_id`, `order_sn`, `product_id`, `product_sku_id`, `product_price`, `create_time`, `update_time`, `is_del`, `product_tag_id`, `product_count`, `original_price`, `sku_price`, `tag_name`, `product_name`, `sku_name`, `tag_price`, `country`, `shop_id`, `order_sub_sn`, `order_sku_name`, `order_product_name`, `platform_price`, `product_tag_id_ext`, `org_product_price`, `unit_product_price`, `total_product_price`, `tax_price`, `product_img`, `product_type`, `stock_type`, `org_platform_price`) VALUES ( 0, '"+orderSn+"', 82280602, 39625360, '2000', "+nowTime+", "+nowTime+", 0, NULL, 1, 2000, NULL, NULL, '非烟酒-苹果(非烟酒-苹果)', '非烟酒-苹果(非烟酒-苹果)', 0, '中国', NULL, NULL, '非烟酒-苹果', '非烟酒-苹果', 0, NULL, 2000, 2000, 2000, 0, 'https://static-img.hungrypanda.co/product-img/17256127333010235e7f9494d474ea179b3e264c6d08d.png?x-oss-process=style/prod_detail', 0, 0, 0)";
        Integer insertOrderDetailResult = dbUtils.getJdbcTemplate().update(insertOrderDetail);
        assertThat(insertOrderDetailResult, Matchers.greaterThan(0));
         //插入 hp_delivery_order_extra_info 表
        String insertDeliveryOrderExtraInfo ="INSERT INTO `hp_delivery_order_extra_info`( `dispatch_order_sn`, `order_sn`, `create_time`, `update_time`, `country`, `order_city`, `dispatch_city`, `order_note`, `force_dispatch`, `food_delivery_code`, `user_need_code`, `driver_need_code`, `tobacco_alcohol_customer_name`, `tobacco_alcohol_customer_birthday`, `order_weight`, `delivery_tag`, `type_together_order`, `type_agent_buy`, `type_group_meal`) VALUES (  '"+dispatchOrderSn+"', '"+orderSn+"', "+nowTime+", "+nowTime+", '中国', '杭州市', '杭州市', '', 0, '61608237', 2, 0, '', '', 0.000, '', 0, 0, 0)";
        Integer insertDeliveryOrderExtraInfoResult = dbUtils.getJdbcTemplate().update(insertDeliveryOrderExtraInfo);
        assertThat(insertDeliveryOrderExtraInfoResult, Matchers.greaterThan(0));
        //插入 hp_order_extra_info 表
        String insertOrderExtraInfo ="INSERT INTO `hp_order_extra_info`( `order_sn`, `user_information`, `delivery_price_add_config_ids`, `package_weight`, `is_del`, `create_time`, `update_time`, `group_share_type`, `delivery_area_id`, `delivery_tag`) VALUES ( '"+orderSn+"', '{\\\"dateOfBirth\\\":\\\"1992-9-11\\\",\\\"firstName\\\":\\\"霞\\\",\\\"lastName\\\":\\\"陈\\\",\\\"middleName\\\":\\\"春\\\"}', '[]', 0.000, 0, "+nowTime+", "+nowTime+", 0, 0, '')";
        Integer insertOrderExtraInfoResult = dbUtils.getJdbcTemplate().update(insertOrderExtraInfo);
        assertThat(insertOrderExtraInfoResult, Matchers.greaterThan(0));

       //插入 order_shop 表

        String insertOrderShop ="INSERT INTO `order_shop`( `order_sn`, `shop_id`, `shop_name`, `shop_logo`, `address`, `shop_telephone`, `merchant_notes`, `longitude`, `latitude`, `shop_type`, `shop_area_id`, `shop_area_name`, `city`, `city_id`, `is_special_driver`, `is_special_drive_faraway`, `is_supermarket`, `add_source`, `gmt_create`, `gmt_update`, `service_phone`) VALUES ( '"+orderSn+"', 206704044, '杭州-陈春霞7 烟酒 店铺-自动化测试店铺', 'https://static.hungrypanda.co/crm/1724394059261e984f6319f6145e4a45ece49fcd5f450.jpg', '中华人民共和国浙江省杭州市中国浙江省杭州市滨江区江陵路1740号310051杭州骏宝行汽车销售服务有限公司', '', '', '120.2177029642', '30.2035623745', 0, 51, '滨江区', '杭州市', 0, 0, 0, 0, 0, "+nowTime+", 0, '86444444')";
        Integer insertOrderShopResult = dbUtils.getJdbcTemplate().update(insertOrderShop);
        assertThat(insertOrderShopResult, Matchers.greaterThan(0));


        //插入 hp_delivery_order_driver_info 表
        String insertDeliveryOrderDriverInfo ="INSERT INTO `hp_delivery_order_driver_info`( `driver_code`, `rider_order_time`, `run_type`, `order_sn`, `third_distribution`, `driver_name`, `driver_phone`, `estimated_delivery_time_start`, `estimated_delivery_time_end`, `estimated_on_shop_time`, `estimated_take_meal_time`, `arrive_remark`, `wait_user_time`, `arrive_type`, `phone_area_code`, `update_time`) VALUES ( '', 0, 0, '"+orderSn+"', 0, '', '', "+nowTime+", "+nowTime+", "+nowTime+", "+nowTime+", '', 0, 0, '', 0)";
        Integer insertDeliveryOrderDriverInfoResult = dbUtils.getJdbcTemplate().update(insertDeliveryOrderDriverInfo);
        assertThat(insertDeliveryOrderDriverInfoResult, Matchers.greaterThan(0));


        //插入 hp_delivery_order_time_change_log 表
        String insertDeliveryOrderTimeChangeLog1 ="INSERT INTO `hp_delivery_order_time_change_log`( `order_sn`, `delivery_order_id`, `driver_id`, `operation`, `original_time`, `original_time_end`, `change_time`, `change_time_end`, `is_del`, `create_time`, `remark`, `change_time_type`) VALUES (  '"+orderSn+"', "+deliveryOrderId+", 206704044, 2, 0, 0, 0, "+futureTime+", 0, "+nowTime+", '', 1)";
        Integer insertDeliveryOrderTimeChangeLog1Result = dbUtils.getJdbcTemplate().update(insertDeliveryOrderTimeChangeLog1);
        assertThat(insertDeliveryOrderTimeChangeLog1Result, Matchers.greaterThan(0));
        //插入 hp_delivery_order_time_change_log 表
        String insertDeliveryOrderTimeChangeLog2 ="INSERT INTO `hp_delivery_order_time_change_log`( `order_sn`, `delivery_order_id`, `driver_id`, `operation`, `original_time`, `original_time_end`, `change_time`, `change_time_end`, `is_del`, `create_time`, `remark`, `change_time_type`) VALUES ( '"+orderSn+"', "+deliveryOrderId+", 206704044, 2, 0, "+futureTime+"+180000, 0, "+futureTime+"+120000, 0, "+nowTime+", '缩短3mile骑手考核时间', 2)";
        Integer insertDeliveryOrderTimeChangeLog2Result = dbUtils.getJdbcTemplate().update(insertDeliveryOrderTimeChangeLog2);
        assertThat(insertDeliveryOrderTimeChangeLog2Result, Matchers.greaterThan(0));
         //插入 hp_delivery_order_time_change_log 表
        String insertDeliveryOrderTimeChangeLog3 ="INSERT INTO `hp_delivery_order_time_change_log`( `order_sn`, `delivery_order_id`, `driver_id`, `operation`, `original_time`, `original_time_end`, `change_time`, `change_time_end`, `is_del`, `create_time`, `remark`, `change_time_type`) VALUES ( '"+orderSn+"', "+deliveryOrderId+", 206704044, 2, 0, 0, 0, "+futureTime+"+180000, 0, "+nowTime+", '', 2)";
        Integer insertDeliveryOrderTimeChangeLog3Result = dbUtils.getJdbcTemplate().update(insertDeliveryOrderTimeChangeLog3);
        assertThat(insertDeliveryOrderTimeChangeLog3Result, Matchers.greaterThan(0));
        //插入 hp_delivery_order_time_change_log 表
        String insertDeliveryOrderTimeChangeLog4 ="INSERT INTO `hp_delivery_order_time_change_log`( `order_sn`, `delivery_order_id`, `driver_id`, `operation`, `original_time`, `original_time_end`, `change_time`, `change_time_end`, `is_del`, `create_time`, `remark`, `change_time_type`) VALUES ( '"+orderSn+"', "+deliveryOrderId+", 206704044, 2, 0, 0, 0, "+futureTime+"+240000, 0, "+nowTime+", '', 0)";
        Integer insertDeliveryOrderTimeChangeLog4Result = dbUtils.getJdbcTemplate().update(insertDeliveryOrderTimeChangeLog4);
        assertThat(insertDeliveryOrderTimeChangeLog4Result, Matchers.greaterThan(0));

        //插入 hp_delivery_time_delay_order_info 表
        String insertDeliveryTimeDelayOrderInfo ="INSERT INTO `hp_delivery_time_delay_order_info`( `city`, `order_sn`, `delivery_order_id`, `dispatch_order_sn`, `total_order_time`, `delivery_order_time`, `shorten_order_time`, `order_distance`) VALUES ( '杭州市', '"+orderSn+"', "+deliveryOrderId+", '"+dispatchOrderSn+"', 7, 2, 1, 1.23)";
        Integer insertDeliveryTimeDelayOrderInfoResult = dbUtils.getJdbcTemplate().update(insertDeliveryTimeDelayOrderInfo);
        assertThat(insertDeliveryTimeDelayOrderInfoResult, Matchers.greaterThan(0));

        //插入 hp_delivery_weather_info 表
        String insertDeliveryWeatherInfo ="INSERT INTO `hp_delivery_weather_info`( `order_id`, `order_sn`, `weather`, `tem`, `wind_speed`, `create_time`, `delivery_order_id`) VALUES ( "+orderId+", '"+orderSn+"', '霭', 21, 14, "+nowTime+", "+deliveryOrderId+")";
        Integer insertDeliveryWeatherInfoResult = dbUtils.getJdbcTemplate().update(insertDeliveryWeatherInfo);
        assertThat( insertDeliveryWeatherInfoResult, Matchers.greaterThan(0));

        //插入 hp_order_performance_monitoring 表
        String insertOrderPerformanceMonitoring ="INSERT INTO `hp_order_performance_monitoring`(`order_id`, `order_sn`, `order_status`, `is_del`, `order_city`, `dispatch_city`, `shop_user_distance`, `order_create_time`, `order_create_date`, `driver_id`, `arrived_date`, `estimated_arrived_date`, `estimated_arrived_time`, `delivery_status`, `shop_id`, `shop_name`, `shop_grade`, `shop_type`, `merchant_take_order_time`, `estimated_delivery_time_end`, `order_business_type`, `delivery_type`, `order_type`, `distance_type`, `customer_language_code`, `user_first_order`, `customer_timeout_sensitivity`, `order_area_id`, `dispatch_pool_type`, `create_time`, `update_time`, `exception_status`, `order_paid_time`, `order_pay_delivery_time`, `shop_out_meal_status`, `shop_out_meal_timeout_status`, `driver_take_meal_timeout`, `dispatch_auto_status`, `dispatch_unassigned_driver_time`, `driver_area_delivery_timeout_status`, `driver_delivery_timeout_status`, `driver_nearby_customer_time`, `driver_out_meal_non_take_meal`, `driver_on_shop_take_meal_error`, `activity_type`, `driver_first_order`, `distance_level`, `paid_distance_type`, `report_near_order_type`, `near_order_type`, `arrived_time`, `merchant_take_date`, `paid_section_distance_type`) VALUES ( "+orderId+", '"+orderSn+"', 0, 0, '杭州市', '杭州市', '1.23', "+nowTime+", '"+nowDate+"', 0, '', '"+nowDate+"', "+futureTime+", 1, 206704044, '杭州-陈春霞7 烟酒 店铺-自动化测试店铺', 0, 0, "+nowTime+", "+futureTime+", 0, 0, 0, 1, 'CN', 0, 0, 51, 2, "+nowTime+", "+nowTime+", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 1, 0, '"+nowDate+"', 0)";
        Integer insertOrderPerformanceMonitoringResult = dbUtils.getJdbcTemplate().update(insertOrderPerformanceMonitoring);
        assertThat( insertOrderPerformanceMonitoringResult, Matchers.greaterThan(0));

        //插入 hp_delivery_order_appointment_record 表
        String insertDeliveryOrderAppointmentRecord1 ="INSERT INTO `hp_delivery_order_appointment_record`( `app_version`, `app_platform`, `longitude`, `latitude`, `driver_id`, `driver_name`, `order_id`, `order_sn`, `extend_sn`, `order_status`, `type`, `operation_code`, `operation_content`, `distance`, `operator_id`, `operator_name`, `create_time`, `extra_code`, `delivery_order_id`, `delivery_status`) VALUES ( '', '', '', '', 0, '', 0, '"+orderSn+"', '', 0, 0, 100, '', 1.23, 1, '系统', "+nowTime+", 0, "+deliveryOrderId+", 0)";
        Integer insertDeliveryOrderAppointmentRecordResult1 = dbUtils.getJdbcTemplate().update(insertDeliveryOrderAppointmentRecord1);
        assertThat( insertDeliveryOrderAppointmentRecordResult1, Matchers.greaterThan(0));

        //插入 hp_delivery_order_appointment_record 表
        String insertDeliveryOrderAppointmentRecord2 ="INSERT INTO `hp_delivery_order_appointment_record`( `app_version`, `app_platform`, `longitude`, `latitude`, `driver_id`, `driver_name`, `order_id`, `order_sn`, `extend_sn`, `order_status`, `type`, `operation_code`, `operation_content`, `distance`, `operator_id`, `operator_name`, `create_time`, `extra_code`, `delivery_order_id`, `delivery_status`) VALUES ( '', '', '', '', 0, '', "+orderId+", '"+orderSn+"', '', 0, 6, 5, '客户下单', 0.00, 0, '', "+nowTime+", 0, "+deliveryOrderId+", 0)";
        Integer insertDeliveryOrderAppointmentRecordResult2 = dbUtils.getJdbcTemplate().update(insertDeliveryOrderAppointmentRecord2);
        assertThat( insertDeliveryOrderAppointmentRecordResult2, Matchers.greaterThan(0));


        //插入 hp_delivery_order_appointment_record 表
        String insertDeliveryOrderAppointmentRecord3 ="INSERT INTO `hp_delivery_order_appointment_record`( `app_version`, `app_platform`, `longitude`, `latitude`, `driver_id`, `driver_name`, `order_id`, `order_sn`, `extend_sn`, `order_status`, `type`, `operation_code`, `operation_content`, `distance`, `operator_id`, `operator_name`, `create_time`, `extra_code`, `delivery_order_id`, `delivery_status`) VALUES ( '', '', '', '', 0, '', "+orderId+", '"+orderSn+"', '', 15, 6, 10, '客户已付款', 0.00, 0, '', "+nowTime+", 0, "+deliveryOrderId+", 0)";
        Integer insertDeliveryOrderAppointmentRecordResult3 = dbUtils.getJdbcTemplate().update(insertDeliveryOrderAppointmentRecord3);
        assertThat( insertDeliveryOrderAppointmentRecordResult3, Matchers.greaterThan(0));

        //插入 hp_delivery_order_appointment_record 表
        String insertDeliveryOrderAppointmentRecord4 ="INSERT INTO `hp_delivery_order_appointment_record`( `app_version`, `app_platform`, `longitude`, `latitude`, `driver_id`, `driver_name`, `order_id`, `order_sn`, `extend_sn`, `order_status`, `type`, `operation_code`, `operation_content`, `distance`, `operator_id`, `operator_name`, `create_time`, `extra_code`, `delivery_order_id`, `delivery_status`) VALUES ( '', '', '', '', 0, '', "+orderId+", '"+orderSn+"', '', 15, 0, 101, '', 1.23, 0, '系统', "+nowTime+", 0, "+deliveryOrderId+", 0)";
        Integer insertDeliveryOrderAppointmentRecordResult4 = dbUtils.getJdbcTemplate().update(insertDeliveryOrderAppointmentRecord4);
        assertThat( insertDeliveryOrderAppointmentRecordResult4, Matchers.greaterThan(0));

        //插入 hp_delivery_order_appointment_record 表
        String insertDeliveryOrderAppointmentRecord5 ="INSERT INTO `hp_delivery_order_appointment_record`( `app_version`, `app_platform`, `longitude`, `latitude`, `driver_id`, `driver_name`, `order_id`, `order_sn`, `extend_sn`, `order_status`, `type`, `operation_code`, `operation_content`, `distance`, `operator_id`, `operator_name`, `create_time`, `extra_code`, `delivery_order_id`, `delivery_status`) VALUES ( '', '', '', '', 0, '', "+orderId+", '"+orderSn+"', '', 15, 3, 399, '', 1.23, 0, '系统', "+nowTime+", 0, "+deliveryOrderId+", 0)";
        Integer insertDeliveryOrderAppointmentRecordResult5 = dbUtils.getJdbcTemplate().update(insertDeliveryOrderAppointmentRecord5);
        assertThat( insertDeliveryOrderAppointmentRecordResult5, Matchers.greaterThan(0));

        //插入 hp_delivery_order_extra_info 表
        String insertDeliveryOrderAppointmentRecord6 ="INSERT INTO `hp_delivery_order_appointment_record`( `app_version`, `app_platform`, `longitude`, `latitude`, `driver_id`, `driver_name`, `order_id`, `order_sn`, `extend_sn`, `order_status`, `type`, `operation_code`, `operation_content`, `distance`, `operator_id`, `operator_name`, `create_time`, `extra_code`, `delivery_order_id`, `delivery_status`) VALUES ( '', '', '', '', 0, '', "+orderId+", '"+orderSn+"', '', 15, 0, 102, '', 1.23, 0, '系统', "+nowTime+", 0, "+deliveryOrderId+", 0)";
        Integer insertDeliveryOrderAppointmentRecordResult6 = dbUtils.getJdbcTemplate().update(insertDeliveryOrderAppointmentRecord6);
        assertThat( insertDeliveryOrderAppointmentRecordResult6, Matchers.greaterThan(0));

        System.out.println("创建成功orderSn："+orderSn);
        CacheUtils.set("orderSnByDeliverySql",orderSn);
        Object orderSnBySql = CacheUtils.get("orderSnByDeliverySql");
        System.out.println("redis orderSn："+orderSnBySql);


    }


}
