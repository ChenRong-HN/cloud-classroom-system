package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.UserAddressMapper;
import com.yanque.entity.UserAddress;
import com.yanque.service.IUserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 会员收货地址业务层接口实现类
 *
 * @author cr
 */
@Service
public class UserAddressService extends ServiceImpl<UserAddressMapper,UserAddress> implements IUserAddressService {

    // 注入UserAddress持久层接口实现类
    @Resource
    private UserAddressMapper userAddressMapper;

}
