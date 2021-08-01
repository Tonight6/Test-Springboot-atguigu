package com.springboot.springboot_easycode.entity;

import java.io.Serializable;

/**
 * (TblUser)实体类
 *
 * @author lsc
 * @since 2021-06-05 13:01:25
 */
public class TblUser implements Serializable {
    private static final long serialVersionUID = 187141492427571347L;
    
    private Integer id;
    /**
    * 用户名
    */
    private String userName;
    /**
    * 密码
    */
    private String userPassword;
    
    private Integer userAge;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

}