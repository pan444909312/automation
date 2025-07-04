package com.miller.deliveryapp.module.order.complete;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.module.order.complete.flow.CompleteOrderFlow;
import com.miller.deliveryapp.module.order.complete.request.CompleteOrderRequesDTO;
import com.miller.deliveryapp.module.order.complete.response.CompleteOrderRequesResponse;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.panda.common.enums.delivery.DriverArriveTypeEnum;
import com.panda.delivery.app.server.common.enums.OrderDeliveryOptTypeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@EnvTag.Test
@TestFramework
@DisplayName("骑手app->已完成送餐并拍照送达")
public class DeliveryConsumerTest {

    @MethodSource("modifyOrderStatusToDriverFinishedOrder")
    @ParameterizedTest
    @DisplayName("正常流程_骑手-已完成送餐并拍照送达")
    void shouldModifyOrderStatusToDriverFinishedOrderSuccessfully(CompleteOrderRequesDTO completeOrderRequesDTO) {
        CompleteOrderRequesResponse completeOrderRequesResponse = CompleteOrderFlow.modifyOrderStatus(completeOrderRequesDTO);

        assertThat(completeOrderRequesResponse.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

    /**
     * 修改订单状态为：骑手已送达，并完成拍照
     */

    static Stream<Arguments> modifyOrderStatusToDriverFinishedOrder() {
        CompleteOrderRequesDTO completeOrderRequesDTO = modifyDeliveryStatus(OrderDeliveryOptTypeEnum.DELIVERY_CONSUMER.getValue());
        // 自动化测试使用固定图片，免去上图图片网络操作
        completeOrderRequesDTO.setOrderCompleteImageUrlList(List.of("http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/170174606688616113ac9a0a74ab29cdadf98ad4cf090.jpg"));
        completeOrderRequesDTO.setArriveRemark("留言备注内容-自动化测试创建，图片默认写死资源地址，免去每次上传图片到oss");

        completeOrderRequesDTO.setDriverArriveType(DriverArriveTypeEnum.LEAVE_AT_DOOR.getCode());
        return Stream.of(
                arguments(completeOrderRequesDTO)
        );
    }

    private static CompleteOrderRequesDTO modifyDeliveryStatus(Byte operationType) {
        CompleteOrderRequesDTO completeOrderRequesDTO = new CompleteOrderRequesDTO();
        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get("orderSnByDeliverySql").toString();
        completeOrderRequesDTO.setOrderSnList(List.of(orderSn));
        // 订单状态
        completeOrderRequesDTO.setOperationType(operationType);
        return completeOrderRequesDTO;
    }
}
