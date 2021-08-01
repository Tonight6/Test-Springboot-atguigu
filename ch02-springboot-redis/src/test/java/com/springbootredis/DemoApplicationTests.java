package com.springbootredis;

import com.springbootredis.entiy.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class DemoApplicationTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void set(){

        redisTemplate.opsForValue().set("lsca","cccc");
        System.out.println(redisTemplate.opsForValue().get("lsca"));
    }



    @Test
    public void one(){
        log.info("------开始RedisTemplate操作组件实战----");
        //定义字符串内容及存入缓存的key
        final String content="RedisTemplate实战字符串信息";
        final String key="redis:template:one:string";
        //Redis通用的操作组件
        ValueOperations valueOperations=redisTemplate.opsForValue();
        //将字符串信息写入缓存中
        log.info("写入缓存中的内容：{} ", content);
        valueOperations.set(key, content);
        //从缓存中读取内容
        Object result=valueOperations.get(key);
        log.info("读取出来的内容：{} ", result);
    }

//    @Test
//    public void two() throws Exception{
//        log.info("------开始RedisTemplate操作组件实战----");
//        //构造对象信息
//        User user=new User(1, "debug", "阿修罗");
//        //Redis通用的操作组件
//        ValueOperations valueOperations=redisTemplate.opsForValue();
//        //将序列化后的信息写入缓存中
//        final String key="redis:template:two:object";
//        final String content=objectMapper.writeValueAsString(user);
//        valueOperations.set(key, content);
//        log.info("写入缓存对象的信息：{} ", user);
//        //从缓存中读取内容
//        Object result=valueOperations.get(key);
//        if (result  !=null){
//            User resultUser=objectMapper.readValue(result.toString(), User.class);
//            log.info("读取缓存内容并反序列化后的结果：{} ", resultUser);
//        }


}
