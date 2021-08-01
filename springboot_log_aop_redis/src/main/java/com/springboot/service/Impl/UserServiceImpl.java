package com.springboot.service.Impl;

import com.springboot.domain.User;
import com.springboot.mapper.UserMapper;
import com.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: UserServiceImpl
 * Package: com.springboot.service.Impl
 *
 * @Description:
 * @Date: 2021/5/23 19:56
 * @author: 浪漫
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<User> find() {
        List<User> list = userMapper.find();
        return list;
    }
}
