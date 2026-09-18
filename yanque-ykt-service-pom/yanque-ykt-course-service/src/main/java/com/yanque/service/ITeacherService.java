package com.yanque.service;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 老师业务层接口
 *
 * @author cr
 */
public interface ITeacherService extends IService<Teacher> {

    ApiPageResponse<Teacher> pageList(BasicPageVo basicPageVo);
}
