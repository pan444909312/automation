package com.miller.deliveryapp.order.status.provider;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.deliveryapp.order.status.request.ModifyOrderStatusRequestDTO;
import com.miller.service.framework.cache.CacheUtils;
import com.panda.common.enums.delivery.DriverArriveTypeEnum;
import com.panda.delivery.app.server.common.enums.OrderDeliveryOperationTypeEnum;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者_修改订单状态
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 15:10:12
 */
@SuppressWarnings(value = "unused")
public class ModifyOrderStatusDataProvider {

    /**
     * 修改订单状态为：骑手已到店
     */
    static Stream<Arguments> modifyOrderStatusToDriverArrivedTheRestaurant() {
        return Stream.of(
                arguments(modifyDeliveryStatus(OrderDeliveryOperationTypeEnum.ON_SHOP.getValue()))
        );
    }

    /**
     * 修改订单状态为：骑手已到店但是商家未出餐
     */
    static Stream<Arguments> modifyOrderStatusToDriverArrivedTheRestaurantButWaitingOrder() {
        return Stream.of(
                arguments(modifyDeliveryStatus(OrderDeliveryOperationTypeEnum.NON_OUT_MEAL.getValue()))
        );
    }

    /**
     * 修改订单状态为：骑手已到店正在取餐
     */
    static Stream<Arguments> modifyOrderStatusToDriverArrivedTheRestaurantAndTakingOrder() {
        return Stream.of(
                arguments(modifyDeliveryStatus(OrderDeliveryOperationTypeEnum.TAKE_MEAL.getValue()))
        );
    }

    /**
     * 修改订单状态为：骑手已送达，并完成拍照
     */
    static Stream<Arguments> modifyOrderStatusToDriverFinishedOrder() {
        ModifyOrderStatusRequestDTO modifyOrderStatusRequestDTO = modifyDeliveryStatus(OrderDeliveryOperationTypeEnum.DELIVERY_CONSUMER.getValue());
        // 自动化测试使用固定图片，免去上图图片网络操作
        modifyOrderStatusRequestDTO.setOrderCompleteImageUrlList(List.of("http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/170174606688616113ac9a0a74ab29cdadf98ad4cf090.jpg"));
        modifyOrderStatusRequestDTO.setArriveRemark("留言备注内容-自动化测试创建，图片默认写死资源地址，免去每次上传图片到oss");

        modifyOrderStatusRequestDTO.setDriverArriveType(DriverArriveTypeEnum.LEAVE_AT_DOOR.getCode());
        return Stream.of(
                arguments(modifyOrderStatusRequestDTO)
        );
    }


    /**
     * 修改订单状态
     */
    private static ModifyOrderStatusRequestDTO modifyDeliveryStatus(Byte operationType) {
        ModifyOrderStatusRequestDTO modifyOrderStatusRequestDTO = new ModifyOrderStatusRequestDTO();
        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        modifyOrderStatusRequestDTO.setOrderSnList(List.of(orderSn));
        // 订单状态
        modifyOrderStatusRequestDTO.setOperationType(operationType);
        return modifyOrderStatusRequestDTO;
    }
}
