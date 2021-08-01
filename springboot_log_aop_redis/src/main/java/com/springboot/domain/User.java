package com.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: User
 * Package: com.springboot.domain
 *
 * @Description:
 * @Date: 2021/5/23 15:29
 * @author: 浪漫
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String userName;
    private String password;
}
