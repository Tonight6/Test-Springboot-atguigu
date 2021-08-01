package com.springboot.Controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ClassName: UserContrrller
 * Package: com.springboot.Controller
 *
 * @Description:
 * @Date: 2021/3/11 14:12
 * @author: 浪漫
 */
@Controller
@Slf4j
public class UserController {

    @RequestMapping("/index")
   public String toHello(){

      return "index";
   }

//    @RequestMapping("/success")
//    public String success(){
//
//        return "success";
//    }

    @RequestMapping("/logout")
    public String logout(Model model, HttpServletResponse response){
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.getPrincipal());
        subject.logout();
        Cookie cookie = new Cookie("rememberMe", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String toSuccess(@RequestParam("username") String username, @RequestParam("password") String password, Model model){
        //创建一个shiro的Subject对象，利用这个对象来完成用户的登录认证
        Subject subject= SecurityUtils.getSubject();
        //判断当前用户是否已经认证过，如果已经认证过着不需要认证如果没有认证过则进入if完成认证
        if(!subject.isAuthenticated()){
            //创建一个用户账号和密码的Token对象，并设置用户输入的账号和面
            //这个对象将在Shiro中被获取
            UsernamePasswordToken token=new UsernamePasswordToken(username,password);
            try {
                //例如账号不存在或密码错误等等，我们需要根据不同的异常类型来判断用户的登录状态并给与友好的信息提示
                //调用login后Shiro就会自动执行我们自定义的Realm中的认证方法
                subject.login(token);
                System.out.println("通过token.....");

                //退出登录
                //subject.logout();
            } catch (UnknownAccountException e) {
//进入catch 表示用户的账号错误，这个异常是我们在后台抛出的
                System.out.println("---------------账号不存在");
                model.addAttribute("errorMessage","账号不存在");
                return "login";
            }catch (LockedAccountException e){
//进入catch 表示用户的账号被锁定，这个异常是我们在后台抛出的
                System.out.println("===============账号被锁定");
                model.addAttribute("errorMessage","账号被冻结");
                return "login";
            }catch (IncorrectCredentialsException e){
//进入catch 表示用户的密码，这个异常是shiro在认证密码时抛出
                System.out.println("***************密码不匹配");
                model.addAttribute("errorMessage","密码错误");
                return "login";
            }
        }
        return "success";
    }




    @RequestMapping("/")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/go")
    public String login(){
        return "success";
    }



    /**
     *@RequiresRoles 这个注解是Shiro提供的 用于标签类或当前当前在访问是必须需要什么样的角色
     * 属性
     *   value 取值为String 数组类型 用于指定访问时所需要的一个或多个角色名
     *   logical 取值为Logical.AND或Logical.OR，当指定多个角色时可以使用AND或OR来表示并且和或的意思默认值为AND
     *           表示当前用户必须同时拥有多个角色才可以访问这个方法
     *
     * 注意：Shiro中出列基于配置权限验证以及注解的权限验证意外还支持基于方法调用的权限验证例如
     * Subject subject=SecurityUtils.getSubject();
     *  String[] roles={""};
     *  subject.checkRoles(roles);//验证当前用户是否拥有指定的角色
     *  String[] permissions={""};
     *  subject.checkPermissions(permissions);//验证当前用户是否拥有指定的权限
     */
    @RequiresRoles(value = {"admin"})
    @RequestMapping("/admin/test")
    public String test(){
        Subject subject= SecurityUtils.getSubject();
        System.out.println(subject.getPrincipal());
        return "nice";
    }
    @RequiresRoles(value = {"admin","user"},logical = Logical.AND)
    @RequestMapping("/admin/test01")
    public String test01(){
        Subject subject= SecurityUtils.getSubject();
        System.out.println(subject.getPrincipal());
        return "nice";
    }

    /**
     * @RequiresPermissions 用于判断当前用户是否有指定的一个或多个权限用法与RequiresRoles相同
     */
    @RequiresPermissions(value={"admin:add"})
    @RequiresRoles(value = {"admin"})
    @RequestMapping("/admin/add")
    public String add(){
        Subject subject= SecurityUtils.getSubject();
        System.out.println(subject.getPrincipal());
        return "nice";
    }

    @RequiresPermissions(value={"user:add","admin:add"},logical = Logical.OR)
    @RequestMapping("/user/test")
    public String Usertest(){
        Subject subject= SecurityUtils.getSubject();
        System.out.println(subject.getPrincipal());
        return "nice";
    }
    @RequiresRoles(value = {"user"})
    @RequestMapping("/user/test01")
    public String Usertest01(){
        Subject subject= SecurityUtils.getSubject();
        System.out.println(subject.getPrincipal());
        return "nice";
    }


    /**
     * 配置自定义的异常拦截，需要拦截AuthorizationException 异常或ShiroException异常
     * 注意：当前Shiro出现权限验证失败以后会抛出异常，因此必须要写一个自定义的异常拦截
     * 否则我发正常的转型到我们的错误页面
     * @return
     */
    @ExceptionHandler(value={AuthorizationException.class})
    public String permissionError(Throwable throwable){
        //转向到没有权限的视图页面，可以利用参数throwable将错误信息写入浏览器中
        //实际工作工作中应该根据参数的类型来判断具体是什么异常，然后根据同的异常来为用户提供不同的
        //提示信息
        return "error";
    }






    @PostMapping("/user/loginTest")
    public String login(String username, String password, Boolean rememberMe, Model model) {
        //判断用户名和密码为空
        if (StringUtils.isEmpty(username)|| StringUtils.isEmpty(password) || StringUtils.isEmpty(rememberMe)){
            model.addAttribute("msg","用户名和密码不能为空！");
            return "redirect:/";
        }

        try {

                //获取当前的用户
                Subject subject = SecurityUtils.getSubject();
                //封装用户的登录数据
                UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);
                //进行认证，认证时需要将用户名和密码封闭为token
                subject.login(token);
            System.out.println("进入。。。。");
                return "redirect:/go";

        } catch (UnknownAccountException e) { //用户名不存在
            model.addAttribute("msg", "用户名错误");
            return "redirect:/";
        } catch (IncorrectCredentialsException e) { //用户名不存在
            model.addAttribute("msg", "密码错误");
            return "redirect:/";
        }catch (LockedAccountException e){//账户锁定
            model.addAttribute("msg","账户被锁定！");
            return "redirect:/";
        }
    }

    @RequestMapping("/rem")
    public String toRememberMe(){

        return "rememberMe";
    }


}
