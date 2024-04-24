package com.miller.deliveryapp.order.history.list.provider;

import com.miller.deliveryapp.order.history.list.request.HistoryOrderListRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 骑手app历史订单列表
 *
 * @author chenchunxia
 * @version 1.0
 * @since 2024/4/15 20:45:30
 */
@SuppressWarnings(value = "unused")
public class HistoryOrderListDataProvider {

    static Stream<Arguments> historyOrderListDataProvider() {
        HistoryOrderListRequestDTO historyOrderListRequestDTO = new HistoryOrderListRequestDTO();
        historyOrderListRequestDTO.setType(1);
        //获取今天日期，格式yyyy-MM-dd
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(today);
        historyOrderListRequestDTO.setStartDate(formattedDate);
        historyOrderListRequestDTO.setEndDate(formattedDate);
        // 使用默认值，1页10条
        return Stream.of(
                arguments(historyOrderListRequestDTO)
        );
    }
}
