package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.LoginMapper;
import com.yanque.entity.Login;
import com.yanque.service.ILoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 登录数据业务层接口实现类
 *
 * @author cr
 */
@Service
public class LoginService extends ServiceImpl<LoginMapper,Login> implements ILoginService {

    // 注入Login持久层接口实现类
    @Resource
    private LoginMapper loginMapper;

}
