package com.miller.security;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * JWT(JSON Web Token)代码开发
 *
 * @author Miller Shan
 * @since 2024/12/26 20:58:37
 */
public class JWTUtils {
    /**
     * 创建 JWT
     *
     * @param username  用户名
     * @param issuer    String
     * @param subject   String
     * @param ttlMillis Long
     * @return String
     */
    public static String createJWT(String username, String issuer, String subject, long ttlMillis) {
        // Header: 使用的签名算法版本
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // secret 是存放在服务端的秘密字符串，会参与加密算法进行加密，如果泄漏出去客户端就可以自己生产JWT
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("1sf12sds21ie1inecs078j");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // Payload: 存放需要传递的数据
        JwtBuilder builder = Jwts.builder().setId(username)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
        // Signature: 对 Header、Payload 进行签名，防止数据篡改
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        // 把 Header、Payload、Signature 三个部分拼成一个字符串，每个部分之间用"点"（.）分隔转成字符串返回给客户端
        return builder.compact();
    }

    /**
     * 解密加密之后的token
     */
    public static Claims parseJWT(String jwt) throws ExpiredJwtException {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary("1sf12sds21ie1inecs078j"))
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

    /**
     * 根据token获取用户的登录名
     * @param token
     * @return
     */
    public static String getUsernameByToken(String token) {
        JWTToken jwtToken = new JWTToken(token);
        // 解析token获取用户名和密码
        String jwt = String.valueOf(jwtToken.getPrincipal());
        Claims claims = JWTUtils.parseJWT(jwt);
        // 从token中获取到用户名
        String username = claims.getId();
        return username;
    }

    /**
     * 验证token是否过期
     *
     * @param expiration Date
     * @return boolean
     */
    public static boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    public static void main(String[] args) {
        //加密
        String jwt = createJWT("Miller", "13dsdda", "afrefsa", 1000 * 60 * 60 * 24 * 7);
        System.out.println(jwt);

        //解密
        Claims claims = parseJWT(jwt);
        System.out.println(claims.getId());
    }
}