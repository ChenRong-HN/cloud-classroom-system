package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.DepartmentMapper;
import com.yanque.common.Department;
import com.yanque.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 部门信息业务层接口实现类
 *
 * @author cr
 */
@Service
public class DepartmentService extends ServiceImpl<DepartmentMapper,Department> implements IDepartmentService {

    // 注入Department持久层接口实现类
    @Resource
    private DepartmentMapper departmentMapper;

}
