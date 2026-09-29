package com.wcy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

// jwt工具类
public class JwtUtil {

    // 秘钥
    private static final String SECRET_KEY = "cWluZ2tl";
    // 令牌有效期（12小时）
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    /**
     * 生成JWT令牌
     *
     * @param claims 自定义声明信息
     * @return 生成的JWT令牌
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                // 设置 JWT 载荷声明（claims）
                .setClaims(claims)
                // 设置过期时间点：当前时间 + EXPIRATION_TIME
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                // 使用 HS256 算法对 JWT 进行签名（不是加密）
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    /**
     * 解析JWT令牌
     *
     * @param token JWT令牌
     * @return 解析后的Claims对象
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}