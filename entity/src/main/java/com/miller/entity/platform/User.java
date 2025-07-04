package com.miller.entity.platform;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户实体类，与数据库user表对应
 * <p>
 * 使用 MyBatis-plus 中的注解 {@link TableName @TableName}指定表名
 * </p>
 *
 * @author Miller Shan
 * @since 2024-11-16
 */
@Tag(name = "用户", description = "用户实体类")
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     * 需要使用 {@link TableId @TableId}指定字段为数据库中的主键，
     * 因为数据库中使用的是user_id字段名，否则使用 selectById 查询会返回null。
     * 可以通过将日志级别调整为warn查看输出。
     * WARN Can not find table primary key in Class: "User".
     * {@code type = IdType.ASSIGN_UUID} 这个属性是用于当userId为空时，使用MyBatis-plus的自动生成主键策略。
     */
    @Schema(description = "用户ID", type = "string", example = "user_id")
    @TableId(type = IdType.ASSIGN_UUID)
    private String userId;

    /**
     * 使用 {@link TableField @TableField} 注解指定Java代码中属性名对应的数据库表中的列名
     */
    @TableField("name")
    @Schema(description = "姓名")
    private String name;

    /**
     * 用户账号(email)，系统中唯一
     */
    @Schema(description = "用户邮箱，作为登陆账号")
    private String email;

    /**
     * 密码，默认初始化SQL写死为123456
     */
    @Schema(description = "用户账号密码", type = "string", example = "123456", minLength = 6, maxLength = 30)
    private String password;

    /**
     * 用户状态，默认0
     */
    @Schema(description = "用户状态")
    private String status;

    /**
     * 用户手机号
     */
    @Schema(description = "用户手机号")
    private String mobile;


    /**
     * 创建用户的时间,单位:11位时间戳。例如:<pre>System.currentTimeMillis()</pre>
     */
    @Schema(description = "用户创建时间")
    private Long createTime;

    /**
     * 更新用户信息的时间,单位:11位时间戳。例如:<blockquote><pre>System.currentTimeMillis()</pre></blockquote>
     */
    @Schema(description = "用户更新时间")
    private Long updateTime;

    /**
     * 默认MyBatis-plus会自动映射所有的实体字段到数据库字段上，
     * 但是如果有些字段不想映射到数据库中的字段，那么可以通过添加
     * 注解 {@link TableField @TableField(exist = false)}注解 来表明这个字段不需要映射到数据库。
     */
    @TableField(exist = false)
    @Schema(description = "备注信息")
    private String remark;

}
