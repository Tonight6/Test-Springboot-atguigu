package com.springboot.springboot_easycode.service.impl;

import com.springboot.springboot_easycode.entity.TblUser;
import com.springboot.springboot_easycode.dao.TblUserDao;
import com.springboot.springboot_easycode.service.TblUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TblUser)表服务实现类
 *
 * @author lsc
 * @since 2021-06-05 13:01:25
 */
@Service("tblUserService")
public class TblUserServiceImpl implements TblUserService {
    @Resource
    private TblUserDao tblUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TblUser queryById(Integer id) {
        return this.tblUserDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<TblUser> queryAllByLimit(int offset, int limit) {
        return this.tblUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tblUser 实例对象
     * @return 实例对象
     */
    @Override
    public TblUser insert(TblUser tblUser) {
        this.tblUserDao.insert(tblUser);
        return tblUser;
    }

    /**
     * 修改数据
     *
     * @param tblUser 实例对象
     * @return 实例对象
     */
    @Override
    public TblUser update(TblUser tblUser) {
        this.tblUserDao.update(tblUser);
        return this.queryById(tblUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tblUserDao.deleteById(id) > 0;
    }
}