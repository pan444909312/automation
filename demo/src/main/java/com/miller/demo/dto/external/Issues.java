package com.miller.demo.dto.external;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 缺陷表
 * </p>
 *
 * @author Miller
 * @version 1.0
 * @since 2023/10/31 19:58:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Issues implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String issueId;

    private String title;

    private String description;

    private String status;

    private Long createTime;

    private Long updateTime;

    private String creator;

    private String updateUser;

    private String projectId;

    /**
     * 缺陷处理人
     */
    private String handler;


}
