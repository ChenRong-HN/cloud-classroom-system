package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.UserGrowLogMapper;
import com.yanque.entity.UserGrowLog;
import com.yanque.service.IUserGrowLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 会员成长值记录业务层接口实现类
 *
 * @author cr
 */
@Service
public class UserGrowLogService extends ServiceImpl<UserGrowLogMapper,UserGrowLog> implements IUserGrowLogService {

    // 注入UserGrowLog持久层接口实现类
    @Resource
    private UserGrowLogMapper userGrowLogMapper;

}
