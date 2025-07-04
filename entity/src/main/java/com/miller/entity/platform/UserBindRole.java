package com.miller.entity.platform;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 用户绑定角色
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 20:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserBindRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户与角色关联表
     */
    @TableId
    private String userRoleId;

    private String userId;

    private String roleId;

    private Long createTime;

    private Long updateTime;


}
