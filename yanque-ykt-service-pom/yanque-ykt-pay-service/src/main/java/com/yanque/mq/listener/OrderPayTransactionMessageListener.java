package com.yanque.mq.listener;

import com.yanque.entity.vo.AliPayNotifyReqVo;
import com.yanque.service.IPayOrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * 订单支付事务消息监听器
 *
 * @author cr
 */
@Slf4j
@RocketMQTransactionListener
public class OrderPayTransactionMessageListener implements RocketMQLocalTransactionListener {

    // 注入支付单的服务层接口实现类
    @Resource
    private IPayOrderService payOrderService;

    /**
     * 执行本地事务
     *
     * @param msg 消息(监听到本次发送的消息)
     * @param arg 参数(发送事务消息时额外携带的参数)
     * @return 本地事务状态
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            // 将参数转型为AliPayNotifyReqVo对象
            AliPayNotifyReqVo aliPayNotifyReqVo = (AliPayNotifyReqVo) arg;
            // 调用PayOrderService中的更新支付单状态、保存支付流水的事务方法
            payOrderService.updateStatusAndSavePayFlow(aliPayNotifyReqVo);
            log.info("更新订单号码 {} 支付单状态、添加支付流水成功、事务消息发送", aliPayNotifyReqVo.getOut_trade_no());
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("更新支付单状态、添加支付流水时出现异常、错误原因 {}", e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        return null;
    }
}
