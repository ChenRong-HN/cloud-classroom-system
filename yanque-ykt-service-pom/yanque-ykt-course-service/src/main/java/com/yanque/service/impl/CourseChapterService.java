package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseChapterMapper;
import com.yanque.entity.CourseChapter;
import com.yanque.service.ICourseChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 课程章节业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseChapterService extends ServiceImpl<CourseChapterMapper,CourseChapter> implements ICourseChapterService {

    // 注入CourseChapter持久层接口实现类
    @Resource
    private CourseChapterMapper courseChapterMapper;

}
