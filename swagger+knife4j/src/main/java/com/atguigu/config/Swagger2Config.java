package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * ClassName: Swagger2Config
 * Package: com.atguigu.config
 *
 * @Date: 2022-05-30 09:50
 * @author: joey
 * @Description:
 */
@Configuration
@EnableOpenApi
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                /**
                 * 重点说明:
                 * 其余都是可以默认,但是controller扫描的路径一定要该队,是该项目的controller包路径
                 */
                .apis(RequestHandlerSelectors.basePackage("com.atguigu"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                /**
                 * 指定项目的名称和主题
                 */
                .title("swagger_knife4j_demo演示")
                /**
                 * 描述项目的用途
                 */
                .description("swaager测试description")
                /**
                 * name:使用者的姓名
                 * url:使用者的相关技术文章
                 * email:使用者的邮箱地址
                 */
                .contact(new Contact("兰思聪", "wwww.语雀.com", "noodleOvO@163.com"))
                .version("1.0")
                .build();
    }
}

