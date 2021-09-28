package com.atguigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName: SecurityJwtMain
 * Package: com.atguigu
 *
 * @Date: 2021/8/14 16:40
 * @author: 浪漫
 * @Description:
 */
@SpringBootApplication
@MapperScan(value = "com.atguigu.mapper")
public class SecurityJwtMain {
    public static void main(String[] args) {
          SpringApplication.run(SecurityJwtMain.class, args);
     }
}
