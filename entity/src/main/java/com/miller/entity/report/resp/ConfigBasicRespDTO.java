package com.miller.entity.report.resp;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 系统配置表
 * </p>
 *
 * @author panjuxiang
 * @since 2024-12-31
 */
@Getter
@Setter
@TableName("config")
@Schema(name = "ConfigEntity", description = "系统配置表")
public class ConfigBasicRespDTO implements Serializable {


    @Schema(description = "配置的key值")
    @TableField("config_key")
    private String configKey;

    @Schema(description = "配置的value值")
    @TableField("config_value")
    private String configValue;

    @Schema(description = "配置描述")
    @TableField("config_desc")
    private String configDesc;

}
