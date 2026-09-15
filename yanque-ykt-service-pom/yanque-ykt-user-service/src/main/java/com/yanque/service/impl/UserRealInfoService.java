package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.UserRealInfoMapper;
import com.yanque.entity.UserRealInfo;
import com.yanque.service.IUserRealInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 会员实名资料业务层接口实现类
 *
 * @author cr
 */
@Service
public class UserRealInfoService extends ServiceImpl<UserRealInfoMapper,UserRealInfo> implements IUserRealInfoService {

    // 注入UserRealInfo持久层接口实现类
    @Resource
    private UserRealInfoMapper userRealInfoMapper;

}
