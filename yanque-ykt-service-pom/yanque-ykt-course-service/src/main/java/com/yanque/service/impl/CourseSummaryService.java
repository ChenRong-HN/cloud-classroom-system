package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseSummaryMapper;
import com.yanque.entity.CourseSummary;
import com.yanque.service.ICourseSummaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 课程统计业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseSummaryService extends ServiceImpl<CourseSummaryMapper,CourseSummary> implements ICourseSummaryService {

    // 注入CourseSummary持久层接口实现类
    @Resource
    private CourseSummaryMapper courseSummaryMapper;

}
