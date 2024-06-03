package com.miller.deliveryapp.drivercenter.agreement.provider;

import com.miller.deliveryapp.drivercenter.agreement.request.AgreementListRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 骑手同意的协议列表获取
 *
 * @author penglul
 * @version 1.0
 * @since 2024/04/25 15:10:12
 */
@SuppressWarnings(value = "unused")
public class AgreementListDataProvider {
    /**
     * 骑手同意的协议列表获取
     */
    static Stream<Arguments> agreementListDataProvider() {
        AgreementListRequestDTO agreementListRequestDTO = new AgreementListRequestDTO();
        agreementListRequestDTO.setCountry("中国");
        return Stream.of(
                arguments(agreementListRequestDTO)
        );
    }
}
