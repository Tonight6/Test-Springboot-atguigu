package com.atguigu.service;

import com.atguigu.entity.JwtUser;
import com.atguigu.entity.Role;
import com.atguigu.mapper.RoleMapper;
import com.atguigu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: UserDetatilsServiceImpl
 * Package: com.atguigu.service
 *
 * @Date: 2021/9/15 8:49
 * @author: 浪漫
 * @Description:
 */
public class UserDetatilsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1. 根据username 获取SysUser
        JwtUser user  = userMapper.selectSysUser(username);
        System.out.println("loadUserByUsername user:"+user);
        if( user != null){
            //2. 根据userid的，获取role
            List<Role> roleList = roleMapper.selectRoleByUser(user.getId());
            System.out.println("roleList:"+ roleList);

            List<GrantedAuthority> authorities = new ArrayList<>();
            String roleName = "";
            for (Role role : roleList) {
                roleName  = role.getName();
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+roleName);
                authorities.add(authority);
            }
            user.setAuthorities(authorities);
            return user;
        }
        return user;
    }
}
