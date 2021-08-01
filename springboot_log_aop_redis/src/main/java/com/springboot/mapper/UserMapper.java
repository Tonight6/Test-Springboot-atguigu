package com.springboot.mapper;

import com.springboot.domain.User;

import java.util.List;

/**
 * ClassName: UserMapper
 * Package: com.springboot.mapper
 *
 * @Description:
 * @Date: 2021/5/23 19:57
 * @author: 浪漫
 */

public interface UserMapper {

    /**
     *  查询
     */
    public List<User> find();
}
