package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.UserMapper;
import com.yanque.entity.User;
import com.yanque.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 会员登录账号业务层接口实现类
 *
 * @author cr
 */
@Service
public class UserService extends ServiceImpl<UserMapper,User> implements IUserService {

    // 注入User持久层接口实现类
    @Resource
    private UserMapper userMapper;

}
