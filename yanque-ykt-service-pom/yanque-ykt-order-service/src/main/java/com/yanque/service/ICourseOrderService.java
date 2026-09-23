package com.yanque.service;

import com.yanque.entity.CourseOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanque.entity.vo.CourseOrderConfirmItemRespVo;
import com.yanque.entity.vo.PlaceOrderReqVo;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 订单业务层接口
 *
 * @author x1angwan
 */
public interface ICourseOrderService extends IService<CourseOrder> {

    /**
     * 提交订单
     * @param placeOrderReqVo 订单信息
     * @return 订单号
     */
    String placeOrder(@Valid PlaceOrderReqVo placeOrderReqVo);

    /**
     * 保存订单和订单项
     *
     * @param courseOrder                      订单
     * @param courseOrderConfirmItemRespVoList 订单项确认集合
     */
    void saveOrderAndOrderItem(CourseOrder courseOrder, List<CourseOrderConfirmItemRespVo> courseOrderConfirmItemRespVoList);
}
