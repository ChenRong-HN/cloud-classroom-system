package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.PermissionMapper;
import com.yanque.common.Permission;
import com.yanque.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 权限业务层接口实现类
 *
 * @author cr
 */
@Service
public class PermissionService extends ServiceImpl<PermissionMapper,Permission> implements IPermissionService {

    // 注入Permission持久层接口实现类
    @Resource
    private PermissionMapper permissionMapper;

}
