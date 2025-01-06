package com.miller.entity.platform;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 项目表
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 20:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    private String projectId;

    private String name;

    private String description;

    private Long createTime;

    private Long updateTime;
}
