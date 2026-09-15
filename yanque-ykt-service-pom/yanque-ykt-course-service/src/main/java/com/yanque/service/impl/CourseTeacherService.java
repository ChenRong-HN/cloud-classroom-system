package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseTeacherMapper;
import com.yanque.entity.CourseTeacher;
import com.yanque.service.ICourseTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 课程老师中间业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseTeacherService extends ServiceImpl<CourseTeacherMapper,CourseTeacher> implements ICourseTeacherService {

    // 注入CourseTeacher持久层接口实现类
    @Resource
    private CourseTeacherMapper courseTeacherMapper;

}
