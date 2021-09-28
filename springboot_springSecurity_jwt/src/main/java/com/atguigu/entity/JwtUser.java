package com.atguigu.entity;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * ClassName: JwtUser
 * Package: com.atguigu.util
 *
 * @Date: 2021/8/14 16:44
 * @author: 浪漫
 * @Description:
 */
public class JwtUser implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private String realname;

    private boolean isExpired;
    private boolean isLocked;
    private boolean isCredentials;
    private boolean isEnabled;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser() {
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isCredentials() {
        return isCredentials;
    }

    public void setCredentials(boolean credentials) {
        isCredentials = credentials;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
// 写一个能直接使用user创建jwtUser的构造器
//    public JwtUser(User user) {
//        id = user.getId();
//        username = user.getUsername();
//        password = user.getPassword();
//        authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
//    }

    public JwtUser(Integer id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
