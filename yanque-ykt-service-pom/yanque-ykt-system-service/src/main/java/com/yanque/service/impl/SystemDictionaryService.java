package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.SystemDictionaryMapper;
import com.yanque.entity.SystemDictionary;
import com.yanque.service.ISystemDictionaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 系统字典业务层接口实现类
 *
 * @author cr
 */
@Service
public class SystemDictionaryService extends ServiceImpl<SystemDictionaryMapper,SystemDictionary> implements ISystemDictionaryService {

    // 注入SystemDictionary持久层接口实现类
    @Resource
    private SystemDictionaryMapper systemDictionaryMapper;

}
