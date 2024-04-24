package com.miller.deliveryapp.order.history.detail.provider;

import com.miller.deliveryapp.order.history.detail.request.HistoryOrderDetailRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 骑手app历史订单详情页
 *
 * @author chenchunxia
 * @version 1.0
 * @since 2024/4/15 20:45:30
 */
@SuppressWarnings(value = "unused")
public class HistoryOrderDetailDataProvider {

    static Stream<Arguments> historyOrderDetailDataProvider() {
        HistoryOrderDetailRequestDTO historyOrderDetailRequestDTO = new HistoryOrderDetailRequestDTO();
        historyOrderDetailRequestDTO.setOrderSn("2225802332519797691588");


        // 使用默认值，1页10条
        return Stream.of(
                arguments(historyOrderDetailRequestDTO)
        );
    }
}
