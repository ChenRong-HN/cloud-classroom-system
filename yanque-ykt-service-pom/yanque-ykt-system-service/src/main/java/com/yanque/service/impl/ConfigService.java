package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.ConfigMapper;
import com.yanque.entity.Config;
import com.yanque.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 参数配置业务层接口实现类
 *
 * @author cr
 */
@Service
public class ConfigService extends ServiceImpl<ConfigMapper,Config> implements IConfigService {

    // 注入Config持久层接口实现类
    @Resource
    private ConfigMapper configMapper;

}
