package com.yanque.service;

import com.yanque.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanque.entity.vo.UserReqVo;

/**
 * 会员登录账号业务层接口
 *
 * @author cr
 */
public interface IUserService extends IService<User> {

    void register(UserReqVo userReqVo);
}
