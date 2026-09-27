package com.yanque.service;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.KillCourse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 秒杀课程信息业务层接口
 *
 * @author cr
 */
public interface IKillCourseService extends IService<KillCourse> {

    ApiPageResponse<KillCourse> pagelist(BasicPageVo basicPageVo);
}
