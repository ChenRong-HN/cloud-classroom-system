package com.yanque.mq.consumer;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yanque.common.constant.PayStatusConstant;
import com.yanque.common.constant.RocketMQConstant;
import com.yanque.entity.CourseOrder;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.service.ICourseOrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 *
 *
 * @author cr
 */
@Slf4j
@Component
@RocketMQMessageListener(
        consumerGroup = "order_status_pay_order_consumer_group", // 消费者组
        topic = RocketMQConstant.ORDER_STATUS_TOPIC, // 监听主题名称
        selectorType = SelectorType.TAG, // 基于标签进行消息过滤
        selectorExpression = RocketMQConstant.ORDER_STATUS_PAY_TAG // 标签过滤表达式
)
public class OrderStatusPayMessageConsumer implements RocketMQListener<String> {

    // 注入课程订单服务层接口实现类
    @Resource
    private ICourseOrderService courseOrderService;

    /**
     * 接收到消息的处理逻辑
     *
     * @param orderNo 订单编号
     */
    @Override
    public void onMessage(String orderNo) {
        // 查询指定编号的订单信息
        CourseOrder courseOrder = courseOrderService.getOne(Wrappers.<CourseOrder>lambdaQuery().eq(CourseOrder::getOrderNo, orderNo));
        Assert.notNull(courseOrder, () -> new BusinessException(BusinessErrorType.COURSE_ORDER_NOT_FOUND));
        if (courseOrder.getStatusOrder().equals(PayStatusConstant.UNPAY)) {
            // 如果订单的支付状态依然为UNPAY未支付,则修改订单的订单状态为PAY,并且更新时间
            courseOrder.setStatusOrder(PayStatusConstant.PAY_SUCCESS);
            courseOrder.setUpdateTime(LocalDateTime.now());
            courseOrderService.updateById(courseOrder); // 完成课程订单状态更新
            log.info("监听到MQ的订单状态支付成功、订单编号 {} 的课程订单状态已修改为支付成功", orderNo);
        }
    }
}
