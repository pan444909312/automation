package com.miller.entity.platform;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 21:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_UUID)
    private String permissionId;

    private String permissionName;

    private String path;

    private String isMenu;

    private String parentId;

    private String permissionCode;


}
