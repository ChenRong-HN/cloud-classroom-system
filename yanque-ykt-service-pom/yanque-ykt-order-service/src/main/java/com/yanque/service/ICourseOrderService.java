package com.yanque.service;

import com.yanque.entity.CourseOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanque.entity.vo.PlaceOrderReqVo;
import jakarta.validation.Valid;

/**
 * 订单业务层接口
 *
 * @author x1angwan
 */
public interface ICourseOrderService extends IService<CourseOrder> {

    String placeOrder(@Valid PlaceOrderReqVo placeOrderReqVo);
}
