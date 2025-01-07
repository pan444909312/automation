package com.miller.security;

import com.miller.entity.platform.User;
import com.miller.service.platform.PermissionService;
import com.miller.service.platform.UserBindRoleService;
import com.miller.service.platform.UserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 通过Realm查询用户
 *
 * @author Miller Shan
 * @since 2024/12/26 20:58:37
 */
@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private UserBindRoleService userBindRoleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * Authentication, 登陆身份认证。 验证用户是否合法用户。
     *
     * @param authenticationToken AuthenticationToken
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //方式一：使用shiro的认证
//        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
//        String username = usernamePasswordToken.getUsername();
//        User user = userService.getUserByEmail(username);
//        if (null == user) {
//            String message = "The user does not exist:" + username;
//            log.warn(message);
//            throw new UnknownAccountException(message);
//        }
//        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(), getName());
//        return simpleAuthenticationInfo;
//
        // 方式二：使用JWTToken进行认证
        JWTToken jwtToken = (JWTToken) authenticationToken;

        // 解析token获取用户名和密码
        String jwt = String.valueOf(jwtToken.getPrincipal());
        Claims claims = JWTUtils.parseJWT(jwt);
        // 从token中获取到用户名
        String username = claims.getId();
        // 根据用户名查询出用户的密码交给shiro的身份认证进行处理
        User user = userService.getUserByEmail(username);

        if (null == user) return null;
        // 讲用户名和数据库查询出来的密码交给密码验证器进行验证账号密码的正确先
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(), getName());
        return simpleAuthenticationInfo;
    }

    /**
     * Authorization, 授权。 权限验证，验证已认证的用户是否拥有响应的权限。
     *
     * @param principalCollection PrincipalCollection
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取第一个参数，principalCollection 取的值是从 new SimpleAuthenticationInfo(username, user.getPassword(), getName());
        String username = String.valueOf(principalCollection.iterator().next());
        // 获取用户绑定的所有角色
        Set<String> roles = userBindRoleService.getRolesByUserName(username);
        // 获取用户绑定的角色和角色拥有的权限
        Set<String> permissions = permissionService.getAllPermissionByUsername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 将用户的角色注入到Shiro,这样可以通过@RequiresRoles注解控制访问的方法
        simpleAuthorizationInfo.setRoles(roles);
        // 用户的权限注入到Shiro,这样可以通过@RequiresPermissions注解控制访问的方法
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 使用自定义的 JWT Token需要重写这个方法
     *
     * @param token AuthenticationToken
     * @return boolean
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        // 判断token是否是自己自定义的token这样就可以在 doGetAuthenticationInfo 中使用自定义的JwtToken了
        return token instanceof JWTToken;
    }

}
