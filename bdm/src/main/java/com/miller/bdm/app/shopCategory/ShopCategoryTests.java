package com.miller.bdm.app.shopCategory;

import com.miller.bdm.app.shopCategory.flow.ShopCategoryFlow;
import com.miller.bdm.app.shopCategory.request.ShopCategoryRequestDTO;
import com.miller.bdm.app.shopCategory.response.ShopCategoryResponseDTO;
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
 * bdm-商家类目列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@EnvTag.Test
@TestFramework
@DisplayName("bdm-商家類目列表")
public class ShopCategoryTests {
    @MethodSource("com.miller.bdm.app.shopCategory.provider.ShopCategoryDataProvider#ShopCategoryList")
    @ParameterizedTest
    @DisplayName("bdm-商家类目列表")
    void ShopCategoryList(ShopCategoryRequestDTO ShopCategoryRequestDTO) {
        ShopCategoryResponseDTO privateShopResponseDTO = ShopCategoryFlow.getPageList(ShopCategoryRequestDTO);
        assertThat(privateShopResponseDTO.getCode()).isEqualTo(Long.valueOf(ResponseConstantOfERP.resultCode));
        assertThat(privateShopResponseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
        CacheUtils.set(BusinessConstantOfERP.SHOP_CATEGORY_KEY, privateShopResponseDTO);
    }


}
