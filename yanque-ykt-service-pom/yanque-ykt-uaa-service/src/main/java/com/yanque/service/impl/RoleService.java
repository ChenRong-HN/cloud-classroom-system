package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.RoleMapper;
import com.yanque.entity.Role;
import com.yanque.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 角色业务层接口实现类
 *
 * @author cr
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper,Role> implements IRoleService {

    // 注入Role持久层接口实现类
    @Resource
    private RoleMapper roleMapper;

}
