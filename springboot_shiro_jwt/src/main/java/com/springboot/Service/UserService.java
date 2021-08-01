package com.springboot.Service;

import com.springboot.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserService
 * Package: com.springboot.Service
 *
 * @Description:
 * @Date: 2021/5/18 8:51
 * @author: 浪漫
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public String getPassword(String username){
        return  userMapper.getPassword(username);
    }

    public int checkUserBanStatus(String username){
        return userMapper.checkUserBanStatus(username);
    }

    public String getRole(String username){
        return userMapper.getRole(username);
    }

    public String getRolePermission(String username){
        return userMapper.getRolePermission(username);
    }

    public String getPermission(String username){
        return userMapper.getPermission(username);
    }
}
