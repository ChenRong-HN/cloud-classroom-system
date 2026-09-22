package com.yanque.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.SystemDictionary;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.service.ISystemDictionaryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.SystemDictionaryItemMapper;
import com.yanque.entity.SystemDictionaryItem;
import com.yanque.service.ISystemDictionaryItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

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

    @Resource
    private ISystemDictionaryService systemDictionaryService;

    @Override
    public List<SystemDictionaryItem> getItemsBySn(String sn) {
        // 基于sn查询系统字典信息
        SystemDictionary systemDictionary = systemDictionaryService.getOne(Wrappers.<SystemDictionary>lambdaQuery().eq(SystemDictionary::getSn, sn));
        Assert.notNull(systemDictionary,()->new BusinessException(BusinessErrorType.SYSTEM_DICTIONARY_NOT_EXISTS));
        // 基于系统字典信息的id查询字典选项信息集合
        return list(Wrappers.<SystemDictionaryItem>lambdaQuery().eq(SystemDictionaryItem::getParentId,systemDictionary.getId()));
    }

    @Override
    public ApiPageResponse<SystemDictionaryItem> pageList(BasicPageVo basicPageVo) {
        String key = basicPageVo.getKeyword();
        if (ObjUtil.isNull(basicPageVo.getPage()))
            basicPageVo.setPage(1L);
        long page = basicPageVo.getPage();

        // 构建分页参数对象
        Page<SystemDictionaryItem> systemDictionaryPage = new Page<>(page, 10L);

        // 构建查询条件参数对象
        LambdaQueryWrapper<SystemDictionaryItem> queryWrapper = Wrappers.<SystemDictionaryItem>lambdaQuery().like(StrUtil.isNotBlank(key), SystemDictionaryItem::getName, key);

        systemDictionaryPage = page(systemDictionaryPage, queryWrapper);
        return ApiPageResponse.<SystemDictionaryItem>builder().total(systemDictionaryPage.getTotal()).rows(systemDictionaryPage.getRecords()).build();
    }
}
