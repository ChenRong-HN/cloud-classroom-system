package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.LoginRoleMapper;
import com.yanque.entity.LoginRole;
import com.yanque.service.ILoginRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 用户角色中间业务层接口实现类
 *
 * @author cr
 */
@Service
public class LoginRoleService extends ServiceImpl<LoginRoleMapper,LoginRole> implements ILoginRoleService {

    // 注入LoginRole持久层接口实现类
    @Resource
    private LoginRoleMapper loginRoleMapper;

}
