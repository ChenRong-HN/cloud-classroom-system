package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseOrderMapper;
import com.yanque.entity.CourseOrder;
import com.yanque.service.ICourseOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 订单业务层接口实现类
 *
 * @author x1angwan
 */
@Service
public class CourseOrderService extends ServiceImpl<CourseOrderMapper,CourseOrder> implements ICourseOrderService {

    // 注入CourseOrder持久层接口实现类
    @Resource
    private CourseOrderMapper courseOrderMapper;

}
