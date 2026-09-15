package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.LoginLogMapper;
import com.yanque.common.LoginLog;
import com.yanque.service.ILoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 登录记录业务层接口实现类
 *
 * @author cr
 */
@Service
public class LoginLogService extends ServiceImpl<LoginLogMapper,LoginLog> implements ILoginLogService {

    // 注入LoginLog持久层接口实现类
    @Resource
    private LoginLogMapper loginLogMapper;

}
