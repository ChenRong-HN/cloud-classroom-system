package com.yanque.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.yanque.common.constant.RedisConstant;
import com.yanque.common.constant.RocketMQConstant;
import com.yanque.entity.CourseOrderItem;
import com.yanque.entity.vo.CourseOrderConfirmItemRespVo;
import com.yanque.entity.vo.CourseOrderConfirmRespVo;
import com.yanque.entity.vo.PlaceOrderReqVo;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.feign.client.CourseFeignClient;
import com.yanque.mapper.CourseOrderItemMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseOrderMapper;
import com.yanque.entity.CourseOrder;
import com.yanque.service.ICourseOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单业务层接口实现类
 *
 * @author x1angwan
 */
@Service
@Slf4j
public class CourseOrderService extends ServiceImpl<CourseOrderMapper, CourseOrder> implements ICourseOrderService {

    // 注入CourseOrder持久层接口实现类
    @Resource
    private CourseOrderMapper courseOrderMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private CourseFeignClient courseFeignClient;

    @Resource
    private CourseOrderItemService courseOrderItemService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public String placeOrder(PlaceOrderReqVo placeOrderReqVo) {

        // 模拟登录用户 5L
        Long userId = 5L;

        String targetKey = RedisConstant.ORDER_CONFIRM_TOKEN_KEY.concat(String.valueOf(userId)).concat(":").concat(placeOrderReqVo.getToken());
        Boolean deleteR = redisTemplate.delete(targetKey);
        Assert.isTrue(deleteR, () -> new BusinessException(BusinessErrorType.ORDER_REPEAT_SUBMIT));

        // 查询订单确认信息
        CourseOrderConfirmRespVo courseOrderConfirmRespVo = courseFeignClient.orderConfirm(placeOrderReqVo.getCourseIds()).getData();
        Assert.notNull(courseOrderConfirmRespVo, () -> new BusinessException(BusinessErrorType.ORDER_CONFIRM_ERROR));

        // 生成雪花id
        String orderNo = IdUtil.getSnowflakeNextIdStr();
        LocalDateTime now = LocalDateTime.now();
        BigDecimal totalAmount = courseOrderConfirmRespVo.getTotalAmount();
        // 获取订单中每个课程信息
        List<CourseOrderConfirmItemRespVo> orderConfirmRespVoItems = courseOrderConfirmRespVo.getItems();
        // 生成订单标题
        StringBuilder orderTitleBuilder = new StringBuilder("用户Id ").append(userId).append(" 下单购买课程 ");
        for (CourseOrderConfirmItemRespVo orderConfirmRespVoItem : orderConfirmRespVoItems) {
            orderTitleBuilder.append("[").append(orderConfirmRespVoItem.getCourse().getName()).append("]");
        }

        // 构建订单表数据
        CourseOrder courseOrder = CourseOrder.builder()
                .createTime(now)
                .orderNo(orderNo)
                .totalAmount(totalAmount)
                .totalCount((long) orderConfirmRespVoItems.size())
                .statusOrder(0L)
                .userId(userId)
                .title(orderTitleBuilder.toString())
                .payType(placeOrderReqVo.getPayType())
                .build();

        // 封装Message消息对象
        HashMap<String, Object> messageMap = new HashMap<>();
        messageMap.put("amount", courseOrder.getTotalAmount()); // 订单支付金额
        messageMap.put("orderNo", orderNo); // 订单编号
        messageMap.put("userId", userId); // 订单用户Id
        messageMap.put("subject", courseOrder.getTitle()); // 订单标题
        Message<String> message = MessageBuilder.withPayload(JSONUtil.toJsonStr(messageMap)).build();

        // 封装向事务消息监听器发送的数据
        HashMap<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("courseOrder", courseOrder);
        parameterMap.put("courseOrderConfirmItemRespVoList", orderConfirmRespVoItems);

        // 📌 发送事务消息
        rocketMQTemplate.sendMessageInTransaction(RocketMQConstant.buildDestination(RocketMQConstant.ORDER_PAY_TOPIC, RocketMQConstant.ORDER_PAY_TAG),
                message, parameterMap);

        log.warn("订单创建成功、订单的主键Id {} 订单编号 {}", courseOrder.getId(), courseOrder.getOrderNo());
        return orderNo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrderAndOrderItem(CourseOrder courseOrder, List<CourseOrderConfirmItemRespVo> courseOrderConfirmItemRespVoList) {
        // 保存课程订单数据
        save(courseOrder); // 主键回显
        // 封装订单详情表信息
        List<CourseOrderItem> courseOrderItemList = courseOrderConfirmItemRespVoList.stream().map(courseOrderConfirmItemRespVo -> {
            // 将每一个保存了课程基本信息和销售信息的courseOrderConfirmItemRespVo转换为CourseOrderItem对象
            return CourseOrderItem.builder().orderId(courseOrder.getId())
                    .amount(courseOrderConfirmItemRespVo.getCourseMarket().getPrice()) // 课程单独价格
                    .count(1L) // 购买数量
                    .createTime(LocalDateTime.now()) // 创建时间
                    .updateTime(LocalDateTime.now()) // 最后更新时间
                    .courseId(courseOrderConfirmItemRespVo.getCourse().getId()) // 课程Id
                    .coursePic(courseOrderConfirmItemRespVo.getCourse().getPic()) // 课程图片地址
                    .courseName(courseOrderConfirmItemRespVo.getCourse().getName()) // 课程名称
                    .orderNo(courseOrder.getOrderNo()).build(); // 课程编号
        }).toList();
        // 批量保存订单详情数据
        courseOrderItemService.saveBatch(courseOrderItemList);
    }
}
