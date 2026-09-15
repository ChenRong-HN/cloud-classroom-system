package com.yanque.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.SystemDictionaryItemMapper;
import com.yanque.entity.SystemDictionaryItem;
import com.yanque.service.ISystemDictionaryItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 系统字典选项业务层接口实现类
 *
 * @author cr
 */
@Service
public class SystemDictionaryItemService extends ServiceImpl<SystemDictionaryItemMapper,SystemDictionaryItem> implements ISystemDictionaryItemService {

    // 注入SystemDictionaryItem持久层接口实现类
    @Resource
    private SystemDictionaryItemMapper systemDictionaryItemMapper;

}
