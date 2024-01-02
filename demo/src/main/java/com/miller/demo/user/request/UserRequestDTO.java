package com.miller.demo.user.request;

import com.miller.demo.dto.external.User;
import lombok.Data;

/**
 * 请求对象_用户
 * 这里的请求参数对象也可以用后端的，但是可能会出现请求参数不对应的情况，比如有些请求携带了一些加密数据放在请求体中，
 * 所以需要单独自定义，也可以通过继承后端的实体类进行扩展，比如：extends User 这样只需要在pom中引入需要的实体jar包即可保证研发、测试字段一致性，
 * 这样研发修改了字段名称，测试用例无需修改，自动会同步。
 * <p>
 * 命名规则: 首先考虑与后端对应，其次与数据库表字段对应，但不会完全对应。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 12:55:00
 */
@Data
public class UserRequestDTO extends User {
    /**
     * username
     */
    private String email;

    /**
     * 密码，默认初始化SQL写死为123456
     */
    private String password;
}
