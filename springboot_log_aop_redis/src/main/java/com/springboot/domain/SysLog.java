package com.springboot.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName: Log
 * Package: com.springboot.domain
 *
 * @Description:
 * @Date: 2021/5/23 15:31
 * @author: 浪漫
 */

// LocalDateTime 日期类型的  使用Date 需要使用SimpleDateFormat进行格式转换
// 而SimpleDateFormat是线程不安全的
@Data
public class SysLog{
//    /**
//     * 主键
//     */
//
//    private Integer id;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 操作类型   1 操作记录 -1异常记录
     */
    private Integer type;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 请求方法
     */
    private String actionMethod;

    /**
     * 请求url
     */
    private String actionUrl;

    /**
     * 请求参数
     */
    private String params;


    /**
     * 类路径
     */
    private String classPath;


    /**
     * 开始时间
     *
     * LocalDateTime
     */
    private Long startTime;

    /**
     * 完成时间
     */
    private Long finishTime;

    /**
     * 消耗时间
     */
    private Long consumingTime;

    /**
     * 异常详情信息 堆栈信息
     */
    private String exDetail;

    /**
     * 异常描述 e.getMessage
     */
    private String exDesc;
}
