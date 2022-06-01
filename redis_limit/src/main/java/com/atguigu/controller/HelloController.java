package com.atguigu.controller;

import com.atguigu.Enum.LimitType;
import com.atguigu.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * ClassName: HelloController
 * Package: com.atguigu.controller
 *
 * @Date: 2022-05-26 11:00
 * @author: joey
 * @Description:
 */
@RestController
public class HelloController {
    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/hello")
    @RateLimiter(time = 5,count = 3,limitType = LimitType.IP)
    public String hello() {
        return "hello>>>"+new Date();
    }

    @GetMapping("/ince")
    @RateLimiter(time = 5,count = 3,limitType = LimitType.IP)
    public String ince() throws NoSuchMethodException {
        // 不存在时，默认自动创建，初始值为1
        RateLimiter rateLimiter = this.getClass().getMethod("ince", null).getAnnotation(RateLimiter.class);
        // TODO 设置过期时间
        Long ince_test = redisTemplate.opsForValue().increment("ince_Test");



        return "ince_test==" + ince_test;
    }


}
