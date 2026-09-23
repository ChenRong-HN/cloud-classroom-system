package com.yanque.common.constant;

/**
 * RocketMQ常量类
 *
 * @author cr
 */
public class RocketMQConstant {
    // 支付单MQ主题
    public static final String ORDER_PAY_TOPIC = "order_pay_topic";
    // 支付单MQ标签
    public static final String ORDER_PAY_TAG = "create";

    /**
     * 构建消息目的地字符串(将目标主题、标签拼接为指定格式字符串 主题:标签)
     *
     * @param topic 主题
     * @param tag   标签
     * @return 消息目的地字符串
     */
    public static String buildDestination(String topic, String tag) {
        return topic + ":" + tag;
    }
}
