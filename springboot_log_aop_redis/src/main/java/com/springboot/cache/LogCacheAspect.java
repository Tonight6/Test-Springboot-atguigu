package com.springboot.cache;

import com.springboot.annotation.Syslog;
import com.springboot.domain.SysLog;
import com.springboot.service.SysLogService;
import com.springboot.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ClassName: LogCacheAspect
 * Package: com.springboot.cache
 *
 * @Description:
 * @Date: 2021/5/23 15:30
 * @author: 浪漫
 */
@Aspect
@Component
@EnableAspectJAutoProxy
@Slf4j
public class LogCacheAspect {

    private static  String logKey="log-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd "));;

    private SysLogService sysLogService;

    @Autowired
    private RedisUtil redisUtil;

    private Map<Long,SysLog> logMap = new HashMap<>();

    /**
     * 这里我们使用注解的形式
     * 当然，我们也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method
     * 切点表达式:   execution(...)
     */
    //@Pointcut("@within(com.springboot.annotation.Syslog)")
    // + "execution(* com.springboot.controller.*.*(..))"
    @Pointcut("@annotation(com.springboot.annotation.Syslog)")
    public void logPointCut() {}



    /**
     * 保存日志
     * @param joinPoint
     */
    @Before("logPointCut()")
    private void saveLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Syslog sysLog = method.getAnnotation(Syslog.class);

        //获取servlet请求对象---因为这不是控制器，这里不能注入HttpServletRequest，但springMVC本身提供ServletRequestAttributes可以拿到
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        SysLog log2 = new SysLog();

        // URL
        log2.setActionUrl(request.getRequestURL().toString());
        // ip
        log2.setIp(request.getRemoteAddr());
        // 开始时间
        // System.currentTimeMillis()
        Long beginTime = Instant.now().toEpochMilli();
        log2.setStartTime(beginTime);
        if(sysLog != null){
            //注解上的描述
            log2.setDescription(sysLog.value());
        }
        //请求的方法
        log2.setActionMethod(joinPoint.getSignature().getName());
        // 类名
        log2.setClassPath(joinPoint.getTarget().getClass().getName());
        //请求的参数
        Object[] args = joinPoint.getArgs();

        // 结束时间
        Long endTime = Instant.now().toEpochMilli();
        log2.setFinishTime(endTime);
        //消耗时间
        log2.setConsumingTime(endTime - beginTime);

        log2.setType(1);


        try{
            List<String> list = new ArrayList<String>();
            for (Object o : args) {
                list.add((String) o);
            }
            log2.setParams(list.toString());
            // 执行切面方法
            //joinPoint.proceed();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        // 保存日志信息
        logMap.put(Thread.currentThread().getId(), log2);


        System.out.println(log2.getConsumingTime()+"++++++++");

        redisUtil.sSet(logKey,logMap);

    }

    // AfterRetruning含有返回值时，不能和Around指示符使用同一个pointcut。返回值会为null
    @AfterReturning(pointcut="logPointCut()",returning = "res")
    public Object afterRunning(Object res) throws Throwable{
        System.out.println("======afterRunning=======");
        System.out.println(logMap);
        return  res;
    }

    @AfterThrowing(pointcut="logPointCut()",throwing="ex")
    public void afterThrowing(Exception ex){
        SysLog sysLog = logMap.get(Thread.currentThread().getId());
        sysLog.setType(2);
        sysLog.setExDesc(ex.getMessage());
        System.out.println("发生异常！！！================");

    }





}
