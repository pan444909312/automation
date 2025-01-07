package com.miller.entity.platform;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 20:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    private String roleId;

    private String name;

    /**
     * 角色表
     */
    private String description;

    private Long createTime;

    private Long updateTime;


}
