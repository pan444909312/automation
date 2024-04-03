package com.miller.bdm.app.shop_city;

import com.miller.bdm.app.shop_city.flow.ShopCityFlow;
import com.miller.bdm.app.shop_city.response.ShopCityResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * bdm-商家城市列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@EnvTag.Test
@TestFramework
@DisplayName("bdm-商家城市列表")
public class ShopCityTests {
    @MethodSource("com.miller.bdm.app.shop_city.provider.ShopCityDataProvider#ShopCityList")
    @ParameterizedTest
    @DisplayName("bdm-商家城市列表")
    void ShopCategoryList() {
        ShopCityResponseDTO ShopTagResponseDTO = ShopCityFlow.getPageList();
        assertThat(ShopTagResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        assertThat(ShopTagResponseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
        CacheUtils.set(BusinessConstantOfERP.SHOP_CITY_KEY, ShopTagResponseDTO);
    }


}
