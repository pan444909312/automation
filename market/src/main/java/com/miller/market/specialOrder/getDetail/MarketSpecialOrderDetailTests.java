package com.miller.market.specialOrder.getDetail;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.mapper.specialOrder.SpecialOrderMapper;
import com.miller.market.specialOrder.getDetail.flow.MarketSpecialOrderDetailFlow;
import com.miller.market.specialOrder.getDetail.request.MarketSpecialOrderDetailRequestDTO;
import com.miller.market.specialOrder.getDetail.response.MarketSpecialOrderDetailResponseDTO;
import com.miller.market.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.market.common.enums.SpecialOrderTypeEnum;
import com.panda.market.dal.entity.SpecialOrder;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 特殊单订单详情
 */
@EnvTag.Test
@TestFramework
@DisplayName("特殊单订单详情")
public class MarketSpecialOrderDetailTests {

    @MethodSource("staticOrderDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_已登录_获取特殊单订单详情")
    void getOrderDetailSuccessfully(MarketSpecialOrderDetailRequestDTO requestDTO) {
        MarketSpecialOrderDetailResponseDTO responseDTO = MarketSpecialOrderDetailFlow.specialOderDetail(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticOrderDataProvider() {
        MarketSpecialOrderDetailRequestDTO requestDTO = new MarketSpecialOrderDetailRequestDTO();
        requestDTO.setSpecialOrderId(BusinessConstant.specialOrderId);

        return Stream.of(Arguments.of(requestDTO));
    }

}
