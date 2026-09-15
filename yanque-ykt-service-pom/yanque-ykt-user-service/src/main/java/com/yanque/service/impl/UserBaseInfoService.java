package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.UserBaseInfoMapper;
import com.yanque.common.UserBaseInfo;
import com.yanque.service.IUserBaseInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 会员基本信息业务层接口实现类
 *
 * @author cr
 */
@Service
public class UserBaseInfoService extends ServiceImpl<UserBaseInfoMapper,UserBaseInfo> implements IUserBaseInfoService {

    // 注入UserBaseInfo持久层接口实现类
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;

}
