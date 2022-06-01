package com.atguigu.controller;

import com.atguigu.dto.TestDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: TestControoler
 * Package: com.atguigu.controller
 *
 * @Date: 2022-05-30 09:53
 * @author: joey
 * @Description:
 */
@Api(tags = "测试控制器1")
@RestController
@Slf4j
public class TestController {

    @GetMapping("/hello")
    @Operation(summary = "测试一下", description = "测试一下接口")
    public String hello() {
        return "测试一下！！=================";
    }


    @GetMapping("/dto")
    @Operation(summary = "测试dto", description = "测试dto接口")
    public String dto(TestDto testDto) {
      log.info("进入testDto方法=-===");
        return testDto.toString();
    }



}
