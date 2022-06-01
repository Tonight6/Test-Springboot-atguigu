package com.atguigu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * ClassName: TestDto
 * Package: com.atguigu.dto
 *
 * @Date: 2022-05-30 09:55
 * @author: joey
 * @Description:
 */
@Data
@ApiModel("dto")
public class TestDto {
    @ApiModelProperty("dtoId")
    private String id;

    @ApiModelProperty("dtoName")
    private String name;

}
