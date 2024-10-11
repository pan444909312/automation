package com.miller.bdm.app.shopVisit.provider;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import com.miller.bdm.app.shopVisit.request.ShopVisitRequestDTO;
/**
 * 数据提供者_登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 18:10:12
 */
public class ShopVisitProvider {
    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> ShopTagList() {
        ShopVisitRequestDTO shopVisitRequestDTO = new ShopVisitRequestDTO();
        shopVisitRequestDTO.setShopId(352297264L);
        shopVisitRequestDTO.setPageNo(1);
        shopVisitRequestDTO.setPageSize(10);
        shopVisitRequestDTO.setPartnership(1);
        shopVisitRequestDTO.setCityAuth(false);


        return Stream.of(
                arguments(shopVisitRequestDTO)
        );
    }
}
