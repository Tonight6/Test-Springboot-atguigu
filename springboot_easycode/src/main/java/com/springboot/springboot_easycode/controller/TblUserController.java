package com.springboot.springboot_easycode.controller;

import com.springboot.springboot_easycode.entity.TblUser;
import com.springboot.springboot_easycode.service.TblUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TblUser)表控制层
 *
 * @author lsc
 * @since 2021-06-05 13:01:25
 */
@RestController
@RequestMapping("tblUser")
public class TblUserController {
    /**
     * 服务对象
     */
    @Resource
    private TblUserService tblUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public TblUser selectOne(Integer id) {
        return this.tblUserService.queryById(id);
    }

}