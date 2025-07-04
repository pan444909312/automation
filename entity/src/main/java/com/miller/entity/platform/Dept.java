package com.miller.entity.platform;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 小组实例表
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Dept implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    private String name;


    private String deptId;

}
