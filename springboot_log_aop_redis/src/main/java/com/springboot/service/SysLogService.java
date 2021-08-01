package com.springboot.service;

import com.springboot.domain.SysLog;

/**
 * ClassName: SysLogService
 * Package: com.springboot
 *
 * @Description:
 * @Date: 2021/5/23 15:50
 * @author: 浪漫
 */
public interface SysLogService {

    public boolean save(SysLog log);
}
