package com.yanque.mapper;

import java.util.List;

import com.yanque.entity.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录记录持久层接口
 *
 * @author cr
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

}
