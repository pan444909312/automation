package com.miller.demo.user.response;

import com.miller.demo.dto.external.User;
import lombok.Data;

import java.util.List;

/**
 * 用户_查询用户列表_响应实体类
 * <p>
 * 可借助 IDEA 插件"GsonFormatPlus"自动将json字符串生成java类。<br>
 * 命名规则: 接口名+ResponseDTO， 比如: /user/getUserList 命名为{@link UserGetUserListResponseDTO}
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 13:15:00
 */
@Data
public class UserGetUserListResponseDTO {
    private Integer code;
    private String message;
    /**
     * 这里引用的 User 对象是通过项目依赖加载进来的，这样可以保持 DTO 对象与后端一直，保证了数据的一致性，后端修改自动更新。
     */
    private List<User> data;
}
