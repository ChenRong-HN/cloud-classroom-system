package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.EmployeeMapper;
import com.yanque.entity.Employee;
import com.yanque.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 员工信息业务层接口实现类
 *
 * @author cr
 */
@Service
public class EmployeeService extends ServiceImpl<EmployeeMapper,Employee> implements IEmployeeService {

    // 注入Employee持久层接口实现类
    @Resource
    private EmployeeMapper employeeMapper;

}
