package com.miller.controller;

import com.miller.common.constants.PermissionConstants;
import com.miller.common.constants.RoleConstants;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.clz.ClassFindService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 免校验，用于测试的接口
 * <p>
 * 在 ShiroConfig 中添加了 /anonymous 开头的请求都直接跳过登录验证，
 * * 但如果添加了@RequiresRoles仍然会走角色的校验
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/8/6 17:45:14
 */
@RequestMapping("/anonymous")
@RestController
public class HelloController {

    @Resource
    private ClassFindService classFindService;

    private int tempInt;


    @PostMapping("/getPackageClass")
    public void getPackageClass(@RequestParam(value = "packagePath") String packagePath) {
        // 获取包下所有类, 通过注解过滤
        List<Class<?>> packageClass = classFindService.getPackageClass(packagePath)
                .stream().filter(clz -> clz.isAnnotationPresent(Scenario.class)).toList();
        System.out.println(packageClass.size());
        packageClass.forEach(System.out::println);
    }


    @ApiOperation(value = "验证服务器能启动")
//    @RequiresRoles(value = {RoleConstants.TEST, RoleConstants.DEV})
//    @RequiresPermissions(value = {PermissionConstants.MANAGER_USER})
    @GetMapping("/hello")
    public String hello() {
        System.out.println("Success " + ++tempInt);
        return "Success <h1>" + tempInt;
    }

    /**
     * 测试Shiro的角色、权限 的配置代码是否生效。
     * 如果配置了多个角色或权限之间是 and 的关系。
     * <p>
     * {@link RequiresRoles @RequiresRoles} 注解用于声明访问此方法用于拥有什么样的角色才可以进行访问
     * {@link RequiresPermissions @RequiresPermissions} 注解用于声明用户需要拥对权限代码才可以访问，
     * 用户拥有的权限在数据库中表permission中的字段permission_code中配置了permission_code 中的值可以自定义，
     * 被查询出来的permission_code会
     * 被ShiroRealm#doGetAuthorizationInfo(PrincipalCollection principalCollection)}
     * 方法中setStringPermissions(permissions)注入到shiro中。
     * 。
     */
    @RequiresRoles(value = {RoleConstants.DEV})
    @GetMapping("/testRoles")
    public String testRoles() {
        // 判断用户是否有对应角色的权限
        String result = String.valueOf(SecurityUtils.getSubject().hasRole(RoleConstants.DEV));
        System.out.println(result);
        return result;
    }

    @RequiresPermissions(value = {PermissionConstants.MANAGER})
    @GetMapping("/testPermission")
    public String testPermission() {
        // 判断用户是否有对应的权限代码
        String result = String.valueOf(SecurityUtils.getSubject().isPermitted(PermissionConstants.MANAGER));
        System.out.println(result);
        return result;
    }


}
