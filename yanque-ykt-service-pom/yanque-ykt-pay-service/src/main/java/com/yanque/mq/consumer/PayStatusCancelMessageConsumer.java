package com.yanque.mq.consumer;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yanque.common.constant.PayStatusConstant;
import com.yanque.common.constant.RocketMQConstant;
import com.yanque.entity.PayOrder;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.service.IPayOrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 订单状态取消消息消费者 注意:接收到消息不代表一定要把订单的状态修改为4(超时未支付)
 *
 * @author cr
 */
@Slf4j
@Component
@RocketMQMessageListener(
        consumerGroup = "order_status_cancel_pay_consumer_group", // 消费者组
        topic = RocketMQConstant.ORDER_STATUS_TOPIC, // 监听主题名称
        selectorType = SelectorType.TAG, // 基于标签进行消息过滤
        selectorExpression = RocketMQConstant.ORDER_STATUS_CANCEL_TAG // 标签过滤表达式
)
public class PayStatusCancelMessageConsumer implements RocketMQListener<String> {

    @Resource
    private IPayOrderService payOrderService;

    /**
     * 接收到消息的处理逻辑
     *
     * @param orderNo 消息对象（订单编号）
     */
    @Override
    public void onMessage(String orderNo) {
        PayOrder payOrder = payOrderService.getOne(Wrappers.<PayOrder>lambdaQuery().eq(PayOrder::getOrderNo, orderNo));
        Assert.notNull(payOrder, () -> new BusinessException(BusinessErrorType.PAY_ORDER_NOT_FOUND));
        // 校验查询到的课程订单的状态是否为待支付
        if (ObjUtil.equals(payOrder.getPayStatus(), PayStatusConstant.UNPAY)) {
            // 更新订单状态为超时未支付
            payOrder.setPayStatus(PayStatusConstant.PAY_TIMEOUT);
            // 更新修改时间
            payOrder.setUpdateTime(LocalDateTime.now());
            payOrderService.updateById(payOrder);
            log.info("监听到MQ中的订单支付状态过期检查消息、订单编号 {} 的状态已修改为超时未支付", orderNo);
        }
    }
}
