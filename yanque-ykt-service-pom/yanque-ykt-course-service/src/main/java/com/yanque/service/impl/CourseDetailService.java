package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseDetailMapper;
import com.yanque.entity.CourseDetail;
import com.yanque.service.ICourseDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 课程详情业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseDetailService extends ServiceImpl<CourseDetailMapper,CourseDetail> implements ICourseDetailService {

    // 注入CourseDetail持久层接口实现类
    @Resource
    private CourseDetailMapper courseDetailMapper;

}
