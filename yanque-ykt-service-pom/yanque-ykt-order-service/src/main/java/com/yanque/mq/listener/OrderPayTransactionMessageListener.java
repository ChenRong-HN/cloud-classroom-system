package com.yanque.mq.listener;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yanque.entity.CourseOrder;
import com.yanque.entity.vo.CourseOrderConfirmItemRespVo;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.service.ICourseOrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付单事务消息监听器
 *
 * @author cr
 */
@Slf4j
@RocketMQTransactionListener
public class OrderPayTransactionMessageListener implements RocketMQLocalTransactionListener {

    @Resource
    private ICourseOrderService courseOrderService;

    /**
     * 执行本地事务
     *
     * @param msg 消息
     * @param arg 参数
     * @return 本地事务状态
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            HashMap<String, Object> parameterMap = (HashMap<String, Object>) arg;
            Assert.notEmpty(parameterMap, () -> new BusinessException(BusinessErrorType.TRANSACTION_MESSAGE_ERROR));
            // 调用订单、订单详情的保存功能、并且基于功能执行过程中是否出现异常来明确是否发送事务消息
            CourseOrder courseOrder = (CourseOrder) parameterMap.get("courseOrder");
            List<CourseOrderConfirmItemRespVo> courseOrderConfirmRespVoItemList = (List<CourseOrderConfirmItemRespVo>) parameterMap.get("courseOrderConfirmItemRespVoList");
            courseOrderService.saveOrderAndOrderItem(courseOrder, courseOrderConfirmRespVoItemList);
            // 如果成功的保存了订单、订单详情数据、则发送事务消息
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("执行本地事务出现异常,异常原因 {}", e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 检查本地事务（当执行本地事务，返回状态UNKNOWN）
     *
     * @param msg 消息
     * @return 本地事务状态
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String messageMapJson = (String) msg.getPayload();
        Map<String,Object> messageMap = JSONUtil.toBean(messageMapJson, Map.class);
        String orderNumber = (String) messageMap.get("orderNo");
        // 检查course_order表中是否存在一条数据的订单编号是orderNumber
        CourseOrder r = courseOrderService.getOne(Wrappers.<CourseOrder>lambdaQuery().eq(CourseOrder::getOrderNo, orderNumber));
        // 如果查询到了对应事务,说明执行本地事务的时候成功保存了订单、订单详情数据
        return ObjUtil.isNull(r) ? RocketMQLocalTransactionState.ROLLBACK : RocketMQLocalTransactionState.COMMIT;
    }
}
