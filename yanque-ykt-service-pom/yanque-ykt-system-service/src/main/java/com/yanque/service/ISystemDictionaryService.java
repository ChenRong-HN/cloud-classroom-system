package com.yanque.service;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.SystemDictionary;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统字典业务层接口
 *
 * @author cr
 */
public interface ISystemDictionaryService extends IService<SystemDictionary> {

    ApiPageResponse<SystemDictionary> pageList(BasicPageVo basicPageVo);
}
