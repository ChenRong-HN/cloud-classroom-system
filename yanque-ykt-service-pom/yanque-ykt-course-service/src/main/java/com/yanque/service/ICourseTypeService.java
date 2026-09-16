package com.yanque.service;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.entity.CourseType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanque.entity.vo.TreeVo;

import java.util.List;
import java.util.Map;

/**
 * 课程分类业务层接口
 *
 * @author cr
 */
public interface ICourseTypeService extends IService<CourseType> {

    List<TreeVo> selectCourseTypeTreeData();

    ApiPageResponse<CourseType> selectPage(Map<String, Object> paramterMap);

    boolean save(CourseType courseType);
}
