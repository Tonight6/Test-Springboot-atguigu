package com.springboot.util;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * ClassName: JWTToken
 * Package: com.springboot
 *
 * @Description:
 * @Date: 2021/5/18 8:43
 * @author: 浪漫
 */
// 对token进行扩展
public class JWTToken implements AuthenticationToken {
    private String token;

    public JWTToken(String token) {
        super();
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
