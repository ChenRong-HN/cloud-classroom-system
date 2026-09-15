package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseTypeMapper;
import com.yanque.entity.CourseType;
import com.yanque.service.ICourseTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 课程分类业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseTypeService extends ServiceImpl<CourseTypeMapper,CourseType> implements ICourseTypeService {

    // 注入CourseType持久层接口实现类
    @Resource
    private CourseTypeMapper courseTypeMapper;

}
