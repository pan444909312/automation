package com.miller.entity.platform.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRespDTO {


    /**
     * 用户ID
     */
    private String userId;

    /**
     * 使用 {@link TableField @TableField} 注解指定Java代码中属性名对应的数据库表中的列名
     */
    @TableField("name")
    @Schema(name = "用户昵称")
    private String name;

    /**
     * 用户账号(email)，系统中唯一
     */
    @Schema(name = "用户邮箱，作为登陆账号")
    private String email;



    /**
     * 用户手机号
     */
    @Schema(name = "用户手机号")
    private String mobile;

    /**
     * 用户角色
     */
    private List<String> roles;


}
