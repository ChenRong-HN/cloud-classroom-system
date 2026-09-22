package com.yanque.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
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

    @Override
    public ApiPageResponse<SystemDictionary> pageList(BasicPageVo basicPageVo) {
        String key = basicPageVo.getKeyword();
        if (ObjUtil.isNull(basicPageVo.getPage()))
            basicPageVo.setPage(1L);
        long page = basicPageVo.getPage();

        // 构建分页参数对象
        Page<SystemDictionary> systemDictionaryPage = new Page<>(page, 10L);

        // 构建查询条件参数对象
        LambdaQueryWrapper<SystemDictionary> queryWrapper = Wrappers.<SystemDictionary>lambdaQuery().like(StrUtil.isNotBlank(key), SystemDictionary::getName, key);

        systemDictionaryPage = page(systemDictionaryPage, queryWrapper);
        return ApiPageResponse.<SystemDictionary>builder().total(systemDictionaryPage.getTotal()).rows(systemDictionaryPage.getRecords()).build();
    }
}
