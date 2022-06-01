package com.atguigu.Enum;

/**
 * ClassName: LimitType
 * Package: com.atguigu.Enum
 *
 * @Date: 2022-05-26 10:42
 * @author: joey
 * @Description:
 */
public enum LimitType {
    /**
     * 默认策略全局限流
     */
    DEFAULT,
    /**
     * 根据请求者IP进行限流
     */
    IP
}
