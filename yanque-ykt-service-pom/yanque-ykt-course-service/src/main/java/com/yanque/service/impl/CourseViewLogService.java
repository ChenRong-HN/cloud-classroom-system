package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseViewLogMapper;
import com.yanque.entity.CourseViewLog;
import com.yanque.service.ICourseViewLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 课程浏览记录业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseViewLogService extends ServiceImpl<CourseViewLogMapper,CourseViewLog> implements ICourseViewLogService {

    // 注入CourseViewLog持久层接口实现类
    @Resource
    private CourseViewLogMapper courseViewLogMapper;

}
