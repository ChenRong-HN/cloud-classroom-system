package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseUserShowListMapper;
import com.yanque.entity.CourseUserShowList;
import com.yanque.service.ICourseUserShowListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 用户端课程展示列业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseUserShowListService extends ServiceImpl<CourseUserShowListMapper,CourseUserShowList> implements ICourseUserShowListService {

    // 注入CourseUserShowList持久层接口实现类
    @Resource
    private CourseUserShowListMapper courseUserShowListMapper;

}
