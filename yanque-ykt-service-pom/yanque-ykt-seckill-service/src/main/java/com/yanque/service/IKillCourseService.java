package com.yanque.service;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.KillCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanque.entity.vo.KillCourseRespVo;
import com.yanque.entity.vo.KillReqVo;
import jakarta.validation.Valid;

/**
 * 秒杀课程信息业务层接口
 *
 * @author cr
 */
public interface IKillCourseService extends IService<KillCourse> {

    ApiPageResponse<KillCourse> pagelist(BasicPageVo basicPageVo);

    KillCourseRespVo selectKillCourseRespVo(Long killCourseId);

    String killCourse(@Valid KillReqVo killReqVo);
}
