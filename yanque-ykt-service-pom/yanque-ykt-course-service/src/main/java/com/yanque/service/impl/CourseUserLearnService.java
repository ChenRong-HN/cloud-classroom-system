package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseUserLearnMapper;
import com.yanque.entity.CourseUserLearn;
import com.yanque.service.ICourseUserLearnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 用户课程学习业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseUserLearnService extends ServiceImpl<CourseUserLearnMapper,CourseUserLearn> implements ICourseUserLearnService {

    // 注入CourseUserLearn持久层接口实现类
    @Resource
    private CourseUserLearnMapper courseUserLearnMapper;

}
