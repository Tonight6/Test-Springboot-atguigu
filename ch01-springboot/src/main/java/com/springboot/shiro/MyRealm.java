package com.springboot.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * ClassName: MyRealm
 * Package: com.springboot.domain
 *
 * @Description:
 * @Date: 2021/3/15 19:13
 * @author: 浪漫
 */



//自定义Realm永远完成具体的认证和授权操作
// Realm的父类抽象类
//  AuthenticatingRealm 只负责认证（登录）的Realm父类
public class MyRealm extends AuthorizingRealm {


    /**
     * 执行授权逻辑
     * 只要访问加上授权的资源都会调用改方法
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从Shiro中获取用户名
        Object username=principalCollection.getPrimaryPrincipal();
        //创建一个SimpleAuthorizationInfo类的对象，利用这个对象需要设置当前用户的权限信息
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        //创建角色信息的集合
        Set<String> roles=new HashSet<String>();
        //这里应该根据账号到数据库中获取用户的所对应的所有角色信息并初始化到roles集合中
        if("admin".equals(username)){
            roles.add("admin");
            roles.add("user");
        }else if ("zhangsan".equals(username)){
            roles.add("user");
        }
        Set<String>psermission=new HashSet<String>();
        if("admin".equals(username)){
            psermission.add("admin:add");
        }
        if("zhangsan".equals(username)){
            psermission.add("user:add");
        }
        //设置角色信息
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(psermission);
        return simpleAuthorizationInfo;
    }



    /**
     * 执行认证逻辑
     * 只要使用subject.login(token) 就会调用该方法
     * */

    /**
     * Shiro的认证方法我们需要在这个方法中来获取用户的信息（从数据库中）
     * @param authenticationToken   用户登录时的Token（令牌），这个对象中将存放着我们用户在浏览器中存放的账号和密码
     * @return 返回一个AuthenticationInfo 对象，这个返回以后Shiro会调用这个对象中的一些方法来完成对密码的验证 密码是由Shiro
     *         进行验证是否合法
     * @throws AuthenticationException   如果认证失败Shiro就会抛出AuthenticationException 我们也可以手动自己抛出这个AuthenticationException
     * 以及它的任意子异常类不通的异常类型可以认证过程中的不通错误情况我们需要根据异常类型来为用户返回特定的响应数据
     * AuthenticationException 异常的子类  可以我们自己抛出
     *      AccountException 账号异常  可以我们自己抛出
     *      UnknownAccountException 账号不存在的异常  可以我们自己抛出
     *      LockedAccountException  账号异常锁定异常  可以我们自己抛出
     *      IncorrectCredentialsException  密码错误异常 这个异常会在Shiro进行密码验证是抛出
     */

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //将AuthenticationToken强转成UsernamePasswordToken 这样获取账号和密码更加的方便
        UsernamePasswordToken token= (UsernamePasswordToken)authenticationToken;
        //获取用户在浏览器中输入的账号
        String username=token.getUsername();
        //认证账号,正常情况我们需要这里从数据库中获取账号的信息，以及其他关键数据，例如账号是否被冻结等等
        String dbusername=username;
        if(!"admin".equals(dbusername)){//判断用户账号是否正确
            System.out.println("UnknownAccountException....账号错误");
        }
        if("zhangsan".equals(username)){
            System.out.println("LockedAccountException...账号被锁定");
        }
        //定义一个密码这个密码应该来自数据库中
        String dbpassword="123456";

/*        密码加密码
参数 1 为加密算法 我们选择MD5加密
参数 2 为被加密的数据的数据
参数 3 为加密时的盐值 ，用于改变加密后数据结果
      通常这个盐值需要选择一个表中唯一的数据例如表中的账号
参数 4 为需要对数据使用指定的算法加密多少次
        Object obj=new SimpleHash("MD5",dbpassword," ",1);
认证密码是否正确 使用加密后的密码登录
        return new SimpleAuthenticationInfo(dbusername,obj.toString(),getName());*/


        //认证密码是否正确
        return new SimpleAuthenticationInfo(dbusername,dbpassword,getName());

    }
}
