package com.miller.bdm.app.privateShop.callback.provider;

import com.miller.bdm.app.privateShop.callback.request.SignCallBackRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * bdm-签约回调
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@SuppressWarnings(value = "unused")
public class SignCallBackDataProvider {

    /**
     *签署人签署文档
     * @return
     */
    static Stream<Arguments> SignCallBackSign() {
        SignCallBackRequestDTO signCallBackRequestDTO = new SignCallBackRequestDTO();

        return Stream.of(
                arguments(signCallBackRequestDTO)
        );
    }



}
