package com.springboot.config;


import com.springboot.Interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * ClassName: myMvcConfig
 * Package: com.springboot.bishe.config
 *
 * @Description:
 * @Date: 2021/4/15 14:21
 * @author: 浪漫
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //super.addInterceptors(registry);
        //        //静态资源；  *.css , *.js
        //SpringBoot 1.x版本已经做好了静态资源映射,不必担心拦截静态资源
        //SpringBoot 2.x及以上版本会拦截静态资源请求，需要自己排除静态资源请求
        //拦截所有请
    //配置拦截器求，不包括（"/index.html","/","/user/login"）登录界面请求和静态资源请求（"/asserts/**","/webjars/**"）
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/","index.html").excludePathPatterns("/login");
    }

}
