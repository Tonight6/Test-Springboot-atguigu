package com.springboot.controller;

import com.springboot.annotation.Syslog;
import com.springboot.service.UserService;
import com.springboot.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * ClassName: UserController
 * Package: com.springboot.controller
 *
 * @Description:
 * @Date: 2021/5/23 20:03
 * @author: 浪漫
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("find")
    @Syslog
    public <T> T find(){
//        boolean flag = redisUtil.hasKey("userList");
        boolean flag = false;

        if(flag){
            System.out.println("走缓存");
            T userList = (T) redisUtil.lGet("userList",0,-1);
            return  userList;
        }else {
            System.out.println("走数据库");
            T userList = (T) userService.find();
            redisUtil.lSet("userList", userList);
            return userList;
        }
    }




}
