package com.miller.demo.dto.external;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户实体类，与数据库user表对应
 * <p>
 * 假设这个是开发代码中的请求对象
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/31 20:58:25
 */
@ApiModel(value = "用户", description = "用户实体类")
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
     * WARN Can not find table primary key in Class: "com.xxx.User".
     * {@code type = IdType.ASSIGN_UUID} 这个属性是用于当userId为空时，使用MyBatis-plus的自动生成主键策略。
     */
    @ApiModelProperty(value = "用户ID")
    @TableId(type = IdType.ASSIGN_UUID)
    private String userId;

    /**
     * 使用 {@link TableField @TableField} 注解指定Java代码中属性名对应的数据库表中的列名
     */
    @TableField("name")
    @ApiModelProperty(value = "用户昵称")
    private String name;

    /**
     * 用户账号(email)，系统中唯一
     */
    @ApiModelProperty(value = "用户邮箱，作为登陆账号")
    private String email;

    /**
     * 密码，默认初始化SQL写死为123456
     */
    @ApiModelProperty(value = "用户账号密码")
    private String password;

    /**
     * 用户状态，默认0
     */
    @ApiModelProperty(value = "用户状态")
    private String status;

    /**
     * 创建用户的时间,单位:11位时间戳。例如:<pre>System.currentTimeMillis()</pre>
     */
    @ApiModelProperty(value = "用户创建时间")
    private Long createTime;

    /**
     * 更新用户信息的时间,单位:11位时间戳。例如:<blockquote><pre>System.currentTimeMillis()</pre></blockquote>
     */
    @ApiModelProperty(value = "用户更新时间")
    private Long updateTime;

    /**
     * 默认MyBatis-plus会自动映射所有的实体字段到数据库字段上，
     * 但是如果有些字段不想映射到数据库中的字段，那么可以通过添加
     * 注解 {@link TableField @TableField(exist = false)}注解 来表明这个字段不需要映射到数据库。
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "备注信息")
    private String remark;
}
