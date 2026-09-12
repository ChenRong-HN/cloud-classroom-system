package com.yanque.mapper;

import java.util.List;

import com.yanque.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门信息持久层接口
 *
 * @author cr
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

}
