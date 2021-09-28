package com.atguigu.entity;

/**
 * ClassName: Role
 * Package: com.atguigu.entity
 *
 * @Date: 2021/9/15 8:50
 * @author: 浪漫
 * @Description:
 */
public class Role {
    private Integer id;
    private String name;
    private String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "SysRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
