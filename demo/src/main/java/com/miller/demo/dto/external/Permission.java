package com.miller.demo.dto.external;

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
 * @version 1.0
 * @since 2023/10/31 20:58:25
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
