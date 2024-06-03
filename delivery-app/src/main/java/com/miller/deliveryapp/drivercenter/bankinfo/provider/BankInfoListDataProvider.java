package com.miller.deliveryapp.drivercenter.bankinfo.provider;

import com.miller.deliveryapp.drivercenter.bankinfo.request.BankInfoListRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 个人银行卡信息数据提供者
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 21:10:12
 */
@SuppressWarnings(value = "unused")
public class BankInfoListDataProvider {
    /**
     * 骑手银行卡信息用例数据提供者
     */
    static Stream<Arguments> bankInfoListDataProvider() {
        BankInfoListRequestDTO bankInfoListRequestDTO = new BankInfoListRequestDTO();
        return Stream.of(
                arguments(bankInfoListRequestDTO)
        );
    }
}
