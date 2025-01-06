package com.miller.controller.platform;

import com.miller.common.constants.PermissionConstants;
import com.miller.entity.platform.User;
import com.miller.entity.util.ResponseEnum;
import com.miller.exception.AutomationException;
import com.miller.security.JWTToken;
import com.miller.security.JWTUtils;
import com.miller.service.platform.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理接口
 * <p>
 * 使用{@link RestController @RestController}标识此接口为RESTFul风格，通过调用{@link UserController}相关接口访问用户信息。
 * </p>
 *
 * @author Miller Shan
 * @since 2024/12/24 20:13
 */
@Tag(name = "用户", description = "User Controller")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录接口
     * <p>
     * 使用POST请求登录接口，在请求Body里传入账号(email)，密码(123456)进行登录。
     * </p>
     *
     * @param user {@link User}对象
     * @return {@code HashMap}， 包含user,token字段
     * @see #login(User)
     */
    @Operation(
            summary = "登录接口",
            description = "参数为JSON",
            parameters = {
                    @Parameter(name = "user", description = "实体类User", required = true, schema = @Schema(implementation = User.class))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "收到请求",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "201", description = "处理成功"),
                    @ApiResponse(responseCode = "401", description = "账号不存在"),
                    @ApiResponse(responseCode = "402", description = "账号已禁用"),
                    @ApiResponse(responseCode = "403", description = "密码错误"),
                    @ApiResponse(responseCode = "410", description = "请求的资源不存在"),
                    @ApiResponse(responseCode = "500", description = "账号密码不能为空")
            }
    )
    @PostMapping(value = "/login")
    public Map<String, Object> login(@RequestBody User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new AutomationException(ResponseEnum.ACCOUNT_OR_PASSWORD_EMPTY);
        }
        Subject subject = SecurityUtils.getSubject();
        // 手动创建一个 JWT Token
        String token = JWTUtils.createJWT(user.getEmail(), "back", "user", 1000 * 60 * 60 * 24);
        // 使用用户输入的密码作为password
        JWTToken jwtToken = new JWTToken(token, user.getPassword());
        try {
            // 开始登陆
            subject.login(jwtToken);
        } catch (UnknownAccountException e) {
            throw new AutomationException(ResponseEnum.UNKNOWN_ACCOUNT);
        } catch (IncorrectCredentialsException e) {
            throw new AutomationException(ResponseEnum.PASSWORD_ERROR);
        }
        User backUser = userService.getUserByEmail(user.getEmail());
        // 将用户信息和加密之后的token返回给前端
        Map<String, Object> map = new HashMap<>();
        map.put("user", backUser);
        map.put("token", token);
        return map;
    }

    /**
     * 退出登录接口
     *
     * @return {@code String} "成功退出"
     */
    @Operation(summary = ("退出登录"))
    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "成功退出";
    }

    /**
     * 获取所有菜单栏接口
     *
     * @param username {@code String}
     * @return {@literal List<Map<String, Object>}
     */
    @Operation(
            summary = "获取菜单栏",
            parameters = {
                    @Parameter(name = "username", description = "用户名", required = true, in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = List.class))
                            )})
            }
    )
    @RequiresPermissions(PermissionConstants.ISSUES)
    @GetMapping(value = "/getMenuList/{username}")
    public List<Map<String, Object>> getMenuList(@PathVariable("username") String username) {
        List<Map<String, Object>> list = userService.getMenuList(username);
        if (list == null) {
            throw new AutomationException(ResponseEnum.FAILURE);
        } else {
            return list;
        }
    }

    /**
     * 获取用户列表接口
     *
     * @return {@literal List<User>}
     */
    @Operation(summary = "获取用户列表",
                responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = List.class)))})},
            description = "获取用户列表"
    )
    @RequiresPermissions(PermissionConstants.ISSUES)
    @GetMapping(value = "/getUserList")
    public List<User> getUserList() {
        List<User> list = userService.getUserList();
        if (list == null) {
            throw new AutomationException(ResponseEnum.FAILURE);
        } else {
            return list;
        }
    }
}
