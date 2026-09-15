package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseResourceMapper;
import com.yanque.entity.CourseResource;
import com.yanque.service.ICourseResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 课件信息业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseResourceService extends ServiceImpl<CourseResourceMapper,CourseResource> implements ICourseResourceService {

    // 注入CourseResource持久层接口实现类
    @Resource
    private CourseResourceMapper courseResourceMapper;

}
