package com.miller.security;

import lombok.Data;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * 使用JwtToken做登录认证, 代码参考 {@link org.apache.shiro.authc.UsernamePasswordToken}，稍微改一下兼容 JWT Token
 *
 * @author Miller Shan
 * @since 2024/12/26 20:58:37
 */
@Data
@Component
public class JWTToken implements HostAuthenticationToken, RememberMeAuthenticationToken {
    //返回给前端的token
    private String token;
    private char[] password;
    private boolean rememberMe;
    private String host;


    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    public JWTToken() {
        this.rememberMe = false;
    }

    public JWTToken(String token, char[] password) {
        this(token, (char[]) password, false, (String) null);
    }

    public JWTToken(String token, String password) {
        this(token, (char[]) (password != null ? password.toCharArray() : null), false, (String) null);
    }

    public JWTToken(String token, char[] password, String host) {
        this(token, password, false, host);
    }

    public JWTToken(String token, String password, String host) {
        this(token, password != null ? password.toCharArray() : null, false, host);
    }

    public JWTToken(String token, char[] password, boolean rememberMe) {
        this(token, (char[]) password, rememberMe, (String) null);
    }

    public JWTToken(String token, String password, boolean rememberMe) {
        this(token, (char[]) (password != null ? password.toCharArray() : null), rememberMe, (String) null);
    }

    public JWTToken(String token, char[] password, boolean rememberMe, String host) {
        this.rememberMe = false;
        this.token = token;
        this.password = password;
        this.rememberMe = rememberMe;
        this.host = host;
    }

    public JWTToken(String username, String password, boolean rememberMe, String host) {
        this(username, password != null ? password.toCharArray() : null, rememberMe, host);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName());
        sb.append(" - ");
        sb.append(JWTUtils.parseJWT(this.token).getId());
        sb.append(", rememberMe=").append(this.rememberMe);
        if (this.host != null) {
            sb.append(" (").append(this.host).append(")");
        }
        return sb.toString();
    }
}
