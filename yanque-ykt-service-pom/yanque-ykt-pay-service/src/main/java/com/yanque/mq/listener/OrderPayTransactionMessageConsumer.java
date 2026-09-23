package com.yanque.mq.listener;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.yanque.common.constant.RocketMQConstant;
import com.yanque.entity.PayOrder;
import com.yanque.service.IPayOrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 支付单事务消息的消费者
 *
 * @author cr
 */
@Slf4j
@Component
@RocketMQMessageListener(
        consumerGroup = "order-pay-consumer-group", // 消费者组
        topic = RocketMQConstant.ORDER_PAY_TOPIC, // 监听主题名称
        selectorType = SelectorType.TAG, // 消息过滤方式:基于标签
        selectorExpression = RocketMQConstant.ORDER_PAY_TAG) // 标签的表达式
public class OrderPayTransactionMessageConsumer implements RocketMQListener<String> {

    // 注入支付单服务层接口实现类
    @Resource
    private IPayOrderService payOrderService;

    /**
     * 消息消费的处理逻辑
     *
     * @param message 消息内容
     */
    @Override
    public void onMessage(String message) {
        // 将message的Json格式字符串转换为Map集合
        Map<String, Object> messageMap = JSONUtil.toBean(message, Map.class);
        // 获取messageMap中封装的数据
        BigDecimal amount = Convert.toBigDecimal(messageMap.get("amount"));
        String orderNumber = Convert.toStr(messageMap.get("orderNo"));
        Long userId = Convert.toLong(messageMap.get("userId"));
        String subject = Convert.toStr(messageMap.get("subject"));

        // 构建支付单对象并保存
        PayOrder payOrder = PayOrder.builder().createTime(LocalDateTime.now()).updateTime(LocalDateTime.now())
                .amount(amount).payType(1L).relationId(Long.valueOf(orderNumber)).orderNo(orderNumber)
                .userId(userId).subject(subject).payStatus(0L).build();
        payOrderService.save(payOrder);
        log.info("支付单保存成功,支付单编号 {}", payOrder.getId());
    }
}
