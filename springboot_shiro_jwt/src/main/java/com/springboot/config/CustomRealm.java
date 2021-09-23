package com.springboot.config;

import com.springboot.util.JWTToken;
import com.springboot.Service.UserService;
import com.springboot.util.JWTUtil;
import com.springboot.util.RedisUtil;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


/**
 * ClassName: CustomRealm
 * Package: com.springboot.config
 *
 * @Description:
 * @Date: 2021/5/18 8:41
 * @author: 浪漫
 */
@Component
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 认证，用户登录
     */
    @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        //String username = authenticationToken.getPrincipal().toString();
        //String password =  userService.getPassword(username);

        // 校验token有效性
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = jwtUtil.getUsername(token);
        if (username == null || !jwtUtil.isTokenExpired(token)) {
            throw new AuthenticationException("token认证失败！");
        }
        //TODO 验证token是否过期

        // 存入redis 时间为60s
        //redisUtil.expire(token, 60);

        //SimpleAuthenticationInfo 验证密码    authenticationToken.getPrincipal().toString()
        //第一个参数是userName或者user对象。返回给subject.login(token);方法的参数
        //第二个参数从数据库中获取的password
        //第三个参数是realm，即当前realm的名称
        return new SimpleAuthenticationInfo(token,token, "CustomRealm");


        //        JwtToken jwt = (JwtToken) token;
//        log.info("jwt----------------->{}", jwt);
//        String userId = jwtUtils.getClaimByToken((String) jwt.getPrincipal()).getSubject();
//        User user = userService.getById(Long.parseLong(userId));
//        if(user == null) {
//            throw new UnknownAccountException("账户不存在！");
//        }
//        if(user.getStatus() == -1) {
//            throw new LockedAccountException("账户已被锁定！");
//        }
//        AccountProfile profile = new AccountProfile();
//        BeanUtil.copyProperties(user, profile);
//        log.info("profile----------------->{}", profile.toString());
//        return new SimpleAuthenticationInfo(profile, jwt.getCredentials(), getName());
    }



    /**
     * 授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("————权限认证————");

        String username = jwtUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        String role = userService.getRole(username);
        //每个角色拥有默认的权限
        String rolePermission = userService.getRolePermission(username);
        //每个用户可以设置新的权限
        String permission = userService.getPermission(username);
        Set<String> roleSet = new HashSet<>();
        Set<String> permissionSet = new HashSet<>();
        //需要将 role, permission 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
        roleSet.add(role);
        permissionSet.add(rolePermission);
        permissionSet.add(permission);
        //设置该用户拥有的角色和权限
        info.setRoles(roleSet);
        info.setStringPermissions(permissionSet);
        return info;

    }



}
