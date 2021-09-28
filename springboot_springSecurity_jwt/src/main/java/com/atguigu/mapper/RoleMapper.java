package com.atguigu.mapper;



import com.atguigu.entity.Role;

import java.util.List;

public interface RoleMapper {

   List<Role> selectRoleByUser(Integer userId);
}
