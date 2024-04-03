package com.miller.bdm.app.shop_kp_role;

import com.miller.bdm.app.shop_kp_role.flow.ShopKPRoleFlow;
import com.miller.bdm.app.shop_kp_role.request.ShopKPRoleRequestDTO;
import com.miller.bdm.app.shop_kp_role.response.ShopKPRoleResponseDTO;
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
 * bdm-商家KP角色-列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@EnvTag.Test
@TestFramework
@DisplayName("bdm-商家KP角色-列表")
public class ShopKPRoleTests {
    @MethodSource("com.miller.bdm.app.shop_kp_role.provider.ShopKPRoleDataProvider#ShopKPRoleList")
    @ParameterizedTest
    @DisplayName("bdm-商家KP角色-列表")
    void ShopKPRoleList() {
        ShopKPRoleResponseDTO ShopKPRoleResponseDTO = ShopKPRoleFlow.getPageList();
        assertThat(ShopKPRoleResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        assertThat(ShopKPRoleResponseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
        CacheUtils.set(BusinessConstantOfERP.SHOP_KP_ROLE_KEY, ShopKPRoleResponseDTO);
    }


}
