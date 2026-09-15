package com.yanque.mapper;

import com.yanque.common.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员登录账号持久层接口
 *
 * @author cr
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
