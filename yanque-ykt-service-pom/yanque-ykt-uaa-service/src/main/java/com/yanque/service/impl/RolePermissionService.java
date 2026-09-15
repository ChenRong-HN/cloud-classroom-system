package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.RolePermissionMapper;
import com.yanque.common.RolePermission;
import com.yanque.service.IRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 角色权限业务层接口实现类
 *
 * @author cr
 */
@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper,RolePermission> implements IRolePermissionService {

    // 注入RolePermission持久层接口实现类
    @Resource
    private RolePermissionMapper rolePermissionMapper;

}
