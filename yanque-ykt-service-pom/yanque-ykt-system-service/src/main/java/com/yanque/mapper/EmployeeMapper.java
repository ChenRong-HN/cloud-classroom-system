package com.yanque.mapper;

import com.yanque.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工信息持久层接口
 *
 * @author cr
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
