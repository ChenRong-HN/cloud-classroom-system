package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseMarketMapper;
import com.yanque.entity.CourseMarket;
import com.yanque.service.ICourseMarketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 课程销售信息业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseMarketService extends ServiceImpl<CourseMarketMapper,CourseMarket> implements ICourseMarketService {

    // 注入CourseMarket持久层接口实现类
    @Resource
    private CourseMarketMapper courseMarketMapper;

}
