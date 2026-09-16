package com.yanque.service;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanque.entity.vo.AddCourseReqVo;

import java.util.Map;

/**
 * 课程信息业务层接口
 *
 * @author cr
 */
public interface ICourseService extends IService<Course> {

    void saveCourse(AddCourseReqVo addCourseReqVo);

    ApiPageResponse<Course> pageList(Map<String, Object> parameterMap);
}
