package com.miller.security;
import com.miller.entity.platform.User;
import com.miller.service.platform.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义密码验证器
 *
 * @author Miller Shan
 * @since 2024/12/26 20:58:37
 */
@Component
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {
    // 自定义盐值
//    private static final String salt = "Miller";
    @Autowired
    private UserService userService;

    /**
     * Cryptography, 数据加密。将密码加密之后再存入数据库中。
     *
     * @param token
     * @param info  Authentication身份认证
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        JWTToken jwtToken = (JWTToken) token;
        // 前端登陆成功之后，每次修改不会都发送账号密码，而是通过JwtToken来与交互，所以这里判断一下如果有JwtToken则先通过
        if (jwtToken.getPassword() == null) {
            return true;
        }
        // 获取前端传过来的密码
        String inputPassword = new String(jwtToken.getPassword());
        //获取 new SimpleAuthenticationInfo(username, user.getPassword(), getName()); 传过来的 username
        String username = String.valueOf(info.getPrincipals());
        // 获取 new SimpleAuthenticationInfo(username, user.getPassword(), getName()); 传过来的密码
        String dbPassword = String.valueOf(info.getCredentials());
        // 获取用户
        User user = userService.getUserByEmail(username);
        // 这里是验证密码的关键代码，使用 email+password 进行MD5加密之后，作为密码与数据库中的密码进行对比校验。
        // 判断自定义 验证器(邮箱)+用户输入的密码与数据库的密码是否一致，这里可以自定义salt值,我直接就用用户的邮箱作为salt了。
        return this.equals(MD5Utils.md5(user.getEmail() + inputPassword), dbPassword);
        // 使用自定义的salt值作为加密器
//        return this.equals(StringUtils.md5(salt + inputPassword, dbPassword);
    }
}
