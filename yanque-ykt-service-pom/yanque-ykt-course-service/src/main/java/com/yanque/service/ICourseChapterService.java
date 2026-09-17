package com.yanque.service;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.CourseChapter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 课程章节业务层接口
 *
 * @author cr
 */
public interface ICourseChapterService extends IService<CourseChapter> {

    ApiPageResponse<CourseChapter> pageList(BasicPageVo basicPageVo);
}
