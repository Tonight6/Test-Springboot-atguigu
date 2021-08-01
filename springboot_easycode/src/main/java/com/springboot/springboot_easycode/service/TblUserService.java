package com.springboot.springboot_easycode.service;

import com.springboot.springboot_easycode.entity.TblUser;
import java.util.List;

/**
 * (TblUser)表服务接口
 *
 * @author lsc
 * @since 2021-06-05 13:01:25
 */
public interface TblUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TblUser queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TblUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tblUser 实例对象
     * @return 实例对象
     */
    TblUser insert(TblUser tblUser);

    /**
     * 修改数据
     *
     * @param tblUser 实例对象
     * @return 实例对象
     */
    TblUser update(TblUser tblUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}