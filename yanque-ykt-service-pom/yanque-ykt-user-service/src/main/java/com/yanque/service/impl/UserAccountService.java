package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.UserAccountMapper;
import com.yanque.entity.UserAccount;
import com.yanque.service.IUserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 会员账户信息业务层接口实现类
 *
 * @author cr
 */
@Service
public class UserAccountService extends ServiceImpl<UserAccountMapper,UserAccount> implements IUserAccountService {

    // 注入UserAccount持久层接口实现类
    @Resource
    private UserAccountMapper userAccountMapper;

}
