package com.miller.entity.platform;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色绑定的权限
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 21:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleBindPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roleId;

    private String permissionId;


}
