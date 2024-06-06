package com.miller.bdm.app.shopKpRole.provider;

import com.miller.bdm.app.shopKpRole.request.ShopKPRoleRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * bdm-商家KP角色-列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@SuppressWarnings(value = "unused")
public class ShopKPRoleDataProvider {
    static Stream<Arguments> ShopKPRoleList() {
        ShopKPRoleRequestDTO ShopKPRoleRequestDTO = new ShopKPRoleRequestDTO();

        return Stream.of(
                arguments(ShopKPRoleRequestDTO)
        );
    }

}
