package com.springboot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@Slf4j
class SpringbootAopLogRedisApplicationTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     *  测试redis
     */
    @Test
    void contextLoads() {
        //向redis中添加数据
        redisTemplate.opsForValue().set("kkk", "vvv");
        //根据键值取出数据
        log.info(redisTemplate.opsForValue().get("kkk"));
    }

    /**
     *  测试日期
     */
    @Test
    void contextLoads2() {
     String time = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd "));
        System.out.println(time);
    }

}
