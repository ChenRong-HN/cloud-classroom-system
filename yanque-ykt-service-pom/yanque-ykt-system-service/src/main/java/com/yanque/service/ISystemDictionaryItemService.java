package com.yanque.service;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.SystemDictionaryItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统字典选项业务层接口
 *
 * @author cr
 */
public interface ISystemDictionaryItemService extends IService<SystemDictionaryItem> {

    List<SystemDictionaryItem> getItemsBySn(String sn);

    ApiPageResponse<SystemDictionaryItem> pageList(BasicPageVo basicPageVo);
}
