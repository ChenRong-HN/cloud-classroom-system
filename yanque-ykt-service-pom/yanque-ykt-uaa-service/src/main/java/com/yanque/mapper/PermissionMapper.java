package com.yanque.mapper;

import com.yanque.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限持久层接口
 *
 * @author cr
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

}
