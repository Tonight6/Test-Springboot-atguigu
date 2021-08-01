package com.springboot.Controller;

import com.springboot.JWTToken;
import com.springboot.Service.UserService;
import com.springboot.util.JWTUtil;
import com.springboot.util.Msg;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


/**
 * ClassName: UserController
 * Package: com.springboot.Controller
 *
 * @Description:
 * @Date: 2021/5/18 8:56
 * @author: 浪漫
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @ApiOperation(value="用户登陆")
    @GetMapping("/login")
    @ResponseBody
    public Msg login(@RequestParam("userName") String username,
                     @RequestParam("password") String password) {
        System.out.println("进入登录控制器");
        //获取subject信息
        Subject subject = SecurityUtils.getSubject();
        SimpleHash simpleHash=new SimpleHash("MD5", password, ByteSource.Util.bytes(username), 1);
        System.out.println(simpleHash.toString());
        //封装用户登录数据
        //UsernamePasswordToken UserToken = new UsernamePasswordToken(username, simpleHash.toString());
        String realPassword = userService.getPassword(username);
        if(!simpleHash.toString().equals(realPassword)){
            return  Msg.fail().add("info","密码错误！");
        }
        //获取token加密字符串
        String tokenStr =  jwtUtil.createToken(username);

        //封装token对象
        JWTToken token = new JWTToken(tokenStr);
        try {
            // 用户登录
            subject.login(token);

            return Msg.success().add("info","登录成功").add("token", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Msg.fail().add("info","登陆失败");
    }

    @ApiOperation(value="用户登陆标记")
    @GetMapping(path = "/flag")
    @ResponseBody
    public Msg flag()  {
        Subject subject = SecurityUtils.getSubject();
        boolean flag = subject.isAuthenticated();
        return Msg.success().add("flag",flag);
    }

    @ApiOperation(value="登陆页面跳转")
    @GetMapping("/index.html")
    public String index()  {
       return "index";
    }

    @ApiOperation(value="登陆成功跳转")
    @GetMapping("/success.html")
    public String success()  {
        return "success";
    }

}
