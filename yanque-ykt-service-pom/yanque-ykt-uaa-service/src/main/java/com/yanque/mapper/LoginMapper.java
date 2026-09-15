package com.yanque.mapper;

import com.yanque.entity.Login;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录数据持久层接口
 *
 * @author cr
 */
@Mapper
public interface LoginMapper extends BaseMapper<Login> {

}
