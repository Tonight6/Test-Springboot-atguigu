package com.springboot.shiro;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ClassName: shrioConfig
 * Package: com.springboot.config
 *
 * @Description:
 * @Date: 2021/3/15 19:06
 * @author: 浪漫
 */
@Configuration
public class shrioConfig {


    //配置一个Shiro的过滤器bean，这个bean将配置Shiro相关的一个规则的拦截
    //例如什么样的请求可以访问什么样的请求不可以访问等等
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        //创建Shiro的拦截的拦截器 ，用于拦截我们的用户请求
        ShiroFilterFactoryBean shiroFilter=new ShiroFilterFactoryBean();
        //设置Shiro的安全管理，设置管理的同时也会指定某个Realm 用来完成我们权限分配
        shiroFilter.setSecurityManager(securityManager);
        //用于设置一个登录的请求地址，这个地址可以是一个html或jsp的访问路径，也可以是一个控制器的路径
        //作用是用于通知Shiro我们可以使用这里路径转向到登录页面，但Shiro判断到我们当前的用户没有登录时就会自动转换到这个路径
        //要求用户完成成功
        shiroFilter.setLoginUrl("/");
        //登录成功后转向页面，由于用户的登录后期需要交给Shiro完成，因此就需要通知Shiro登录成功之后返回到那个位置
        shiroFilter.setSuccessUrl("/success");
        //用于指定没有权限的页面，当用户访问某个功能是如果Shiro判断这个用户没有对应的操作权限，那么Shiro就会将请求
        //转向到这个位置，用于提示用户没有操作权限
        shiroFilter.setUnauthorizedUrl("/index");
        //定义一个Map集合，这个Map集合中存放的数据全部都是规则，用于设置通知Shiro什么样的请求可以访问什么样的请求不可以访问
        //Map<String,String> map=new LinkedHashMap<String,String>();
        //  /login 表示某个请求的名字    anon 表示可以使用游客什么进行登录（这个请求不需要登录）
        //map.put("/login","anon");

        //roles[admin] 表示 以/admin/**开头的请求需要拥有admin角色才可以访问否   则返回没有权限的页面
        //perms[admin:add] 表示 /admin/test的请求需要拥有 admin:add权限才可访问
        //注意：admin:add仅仅是一个普通的字符串用于标记某个权限功能
        //  authc 表示这个请求需要进行认证（登录），只有认证（登录）通过才能访问
        //map.put("/admin/test","authc,perms[admin:add]");
        //map.put("/admin/**","authc,roles[admin]");
        //map.put("/user/**","authc,roles[user]");


        //我们可以在这里配置所有的权限规则这列数据真正是需要从数据库中读取出来
        //或者在控制器中添加Shiro的注解

        //shiroFilter.setFilterChainDefinitionMap(map);
        return shiroFilter;

    }

    /**
     * 开启Shiro的注解例如（  @RequiresRoles @RequiresUser @RequiresPermissions）
     * 需要借助SpringAOP来扫描这些注解
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }


    /**
     * 开启AOP的注解支持
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor=new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

/*    注意：启动注解的权限控制以后需要删除在Shiro配置类中的权限拦截的配置规则
map.put("/admin/test","authc,perms[admin:add]");
map.put("/admin/**","authc,roles[admin]");
map.put("/user/**","authc,roles[user]");*/


    // 配置SecurityManager
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("myRealm") Realm myRealm,
                                                     @Qualifier("rememberMeManager") RememberMeManager rememberMeManager ){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //设置一个Realm，这个Realm是最终用于完成我们的认证号和授权操作的具体对象
        securityManager.setRealm(myRealm);

        //设置RememberMeManager 实现记住我功能
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    //配置一个自定义的Realm的bean，最终将使用这个bean返回的对象来完成我们的认证和授权
    @Bean("myRealm")
    public Realm myRealm(){
        MyRealm realm=new MyRealm();
        return realm;
    }


    // 开启thymeleaf的shiro标签
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


    //记住我
    @Bean(name = "rememberMeManager")
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //这个地方有点坑，不是所有的base64编码都可以用，长度过大过小都不行，没搞明白，官网给出的要么0x开头十六进制，要么base64
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }
    //cookie管理
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(1 * 60 * 60);
        return cookie;
    }






}
