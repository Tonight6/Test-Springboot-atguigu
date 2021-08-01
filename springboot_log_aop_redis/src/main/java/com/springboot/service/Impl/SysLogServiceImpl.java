package com.springboot.service.Impl;

import com.springboot.domain.SysLog;
import com.springboot.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ClassName: SysLogServiceImpl
 * Package: com.springboot.service
 *
 * @Description:
 * @Date: 2021/5/23 15:51
 * @author: 浪漫
 */
//@Service
@Slf4j
public class SysLogServiceImpl implements SysLogService {
    @Override
    public boolean save(SysLog sysLog) {
       // log.info(sysLog.getParams());
        return true;
    }
}
