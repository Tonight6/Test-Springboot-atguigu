package com.atguigu.annotation;

import com.atguigu.Enum.LimitType;

import java.lang.annotation.*;

/**
 * ClassName: RateLimiter
 * Package: com.atguigu.annotation
 *
 * @Date: 2022-05-26 10:43
 * @author: joey
 * @Description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
// ElementType.TYPE: 用于描述类、接口(包括注解类型) 或enum声明
public @interface RateLimiter {
    /**
     * 限流key
     */
    String key() default "rate_limit:";

    /**
     * 限流时间,单位秒
     */
    int time() default 60;

    /**
     * 限流次数
     */
    int count() default 3;

    /**
     * 限流类型
     */
    LimitType limitType() default LimitType.DEFAULT;



}
