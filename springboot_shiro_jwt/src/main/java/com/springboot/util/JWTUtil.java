package com.springboot.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * ClassName: JWTUtil
 * Package: com.springboot.util
 *
 * @Description:
 * @Date: 2021/5/18 8:43
 * @author: 浪漫
 */
@Configuration
@ConfigurationProperties(prefix = "markerhub.jwt")
@Data
public class JWTUtil {

    private String secret;
    private long expire;
    private String header;


    /**
     * 生成 token
     */
    public String createToken(String username) {
        Date date = new Date(System.currentTimeMillis() + expire * 1000 * 60);
        return Jwts.builder()
                .claim("username", username)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 解析 token
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     */
    public String getUsername(String token) {
        Claims claims = parseToken(token);
        return claims.get("username", String.class);
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public boolean isTokenExpired(String token) {
        Claims claims = parseToken(token);
        Date expiration = claims.getExpiration();
        return expiration.after(new Date());
    }


}
