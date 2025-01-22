package com.miller.entity.platform;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 小组和用户关联关系表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserBindDept implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    private String userDeptId;

    private String deptId;

    private String userId;

    private Long createTime;

    private Long updateTime;

}
