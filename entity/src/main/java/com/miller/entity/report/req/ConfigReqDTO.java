package com.miller.entity.report.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author panjuxiang
 * @since 2024/12/31 20:29
 */
@Data
public class ConfigReqDTO {


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
