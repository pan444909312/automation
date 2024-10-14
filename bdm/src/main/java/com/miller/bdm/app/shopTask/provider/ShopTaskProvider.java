package com.miller.bdm.app.shopTask.provider;

import com.miller.bdm.app.shopTask.request.ShopTaskRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者_登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 18:10:12
 */
public class ShopTaskProvider {
    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> ShopTagList() {
        ShopTaskRequestDTO shopTaskRequestDTO = new ShopTaskRequestDTO();

        shopTaskRequestDTO.setPageNo(1);
        shopTaskRequestDTO.setPageSize(10);


        return Stream.of(
                arguments(shopTaskRequestDTO)
        );
    }
}
