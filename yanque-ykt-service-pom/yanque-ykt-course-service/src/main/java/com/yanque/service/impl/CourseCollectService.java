package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseCollectMapper;
import com.yanque.entity.CourseCollect;
import com.yanque.service.ICourseCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 课程收藏业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseCollectService extends ServiceImpl<CourseCollectMapper,CourseCollect> implements ICourseCollectService {

    // 注入CourseCollect持久层接口实现类
    @Resource
    private CourseCollectMapper courseCollectMapper;

}
