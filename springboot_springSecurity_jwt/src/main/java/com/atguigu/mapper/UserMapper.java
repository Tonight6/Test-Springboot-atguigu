package com.atguigu.mapper;


import com.atguigu.entity.JwtUser;
import org.springframework.stereotype.Repository;

//@Repository :创建dao对象
@Repository
public interface UserMapper {

    int insertSysUser(JwtUser user);

    //根据账号名称，获取用户信息
    JwtUser selectSysUser(String username);
}
