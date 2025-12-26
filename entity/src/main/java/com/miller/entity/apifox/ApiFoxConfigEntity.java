package com.miller.entity.apifox;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Apifox cli config 配置表
 */
@TableName("apifox_config")
@Data
@Accessors(chain = true)
public class ApiFoxConfigEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String groupCode;

    private String configValue;

    private String configDesc;

    private Integer isEnable;

}
