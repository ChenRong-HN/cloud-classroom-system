package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseOrderItemMapper;
import com.yanque.entity.CourseOrderItem;
import com.yanque.service.ICourseOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 订单详情业务层接口实现类
 *
 * @author x1angwan
 */
@Service
public class CourseOrderItemService extends ServiceImpl<CourseOrderItemMapper,CourseOrderItem> implements ICourseOrderItemService {

    // 注入CourseOrderItem持久层接口实现类
    @Resource
    private CourseOrderItemMapper courseOrderItemMapper;

}
