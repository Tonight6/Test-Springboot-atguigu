package com.springboot.controller;

import com.springboot.annotation.Syslog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: TestController
 * Package: com.springboot.controller
 *
 * @Description:
 * @Date: 2021/5/23 16:22
 * @author: 浪漫
 */
@RestController
@RequestMapping("/log")
public class TestController {

    @RequestMapping("test")
    @Syslog("测试1")
    public String test(@RequestParam("name") String name){
        System.out.println(name);
        return name;
    }


    @RequestMapping("test2")
    @Syslog("异常测试")
    public String test2222(@RequestParam("name") String name){
        int i = 10 / 0;
        System.out.println(name + "===================");
        return name;
    }
}
