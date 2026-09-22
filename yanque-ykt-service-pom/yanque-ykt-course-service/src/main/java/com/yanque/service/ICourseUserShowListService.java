package com.yanque.service;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.entity.CourseUserShowList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanque.entity.vo.CourseQueryVo;

/**
 * 用户端课程展示列业务层接口
 *
 * @author cr
 */
public interface ICourseUserShowListService extends IService<CourseUserShowList> {

    ApiPageResponse<CourseUserShowList> pageList(CourseQueryVo courseQueryVo);
}
