package com.springbootredis.entiy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: User
 * Package: com.springbootredis.entiy
 *
 * @Description:
 * @Date: 2021/3/30 10:11
 * @author: 浪漫
 */


@Data
@AllArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String key;
    private String value;
}
