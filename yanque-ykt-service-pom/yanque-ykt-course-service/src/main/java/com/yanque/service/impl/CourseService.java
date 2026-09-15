package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseMapper;
import com.yanque.entity.Course;
import com.yanque.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 课程信息业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseService extends ServiceImpl<CourseMapper,Course> implements ICourseService {

    // 注入Course持久层接口实现类
    @Resource
    private CourseMapper courseMapper;

}
