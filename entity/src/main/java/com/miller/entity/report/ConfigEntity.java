package com.miller.entity.report;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

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
public class ConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID 自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "配置的key值")
    @TableField("config_key")
    private String configKey;

    @Schema(description = "配置的value值")
    @TableField("config_value")
    private String configValue;

    @Schema(description = "配置描述")
    @TableField("config_desc")
    private String configDesc;

    @Schema(description = "最后修改人员名称")
    @TableField("last_modify_user")
    private String lastModifyUser;

    @Schema(description = "创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Long createTime;

    @Schema(description = "更新时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    @Schema(description = "删除标记（0:可用 1:不可用）")
    @TableField("is_deleted")
    @TableLogic
    private Byte isDeleted;
}
