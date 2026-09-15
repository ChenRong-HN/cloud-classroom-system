package com.yanque.mapper;

import com.yanque.common.UserAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员账户信息持久层接口
 *
 * @author cr
 */
@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccount> {

}
