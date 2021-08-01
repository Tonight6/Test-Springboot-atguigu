package com.springboot.service;

import com.springboot.domain.User;

import java.util.List;

/**
 * ClassName: UserService
 * Package: com.springboot.service
 *
 * @Description:
 * @Date: 2021/5/23 19:56
 * @author: 浪漫
 */
public interface UserService {
    public List<User> find();
}
