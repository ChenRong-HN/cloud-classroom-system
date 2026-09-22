package com.yanque.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.yanque.common.constant.RedisConstant;
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
import org.springframework.data.redis.core.RedisTemplate;
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
    private CourseOrderItemMapper courseOrderItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
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
        StringBuilder orderTitleBuilder = new StringBuilder("用户Id ").append(userId).append(" 下单购买课程 【");
        orderConfirmRespVoItems.forEach(item -> orderTitleBuilder.append(item.getCourse().getName()).append(" "));
        orderTitleBuilder.append("】");

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
        save(courseOrder);

        // 添加订单明细表
        for (CourseOrderConfirmItemRespVo item : orderConfirmRespVoItems) {
            CourseOrderItem orderItem = CourseOrderItem.builder()
                    .orderId(courseOrder.getId())
                    .orderNo(orderNo)
                    .courseId(item.getCourse().getId())
                    .courseName(item.getCourse().getName())
                    .coursePic(item.getCourse().getPic())
                    .amount(item.getCourseMarket().getPrice())
                    .count(1L)
                    .version(0L)
                    .createTime(now)
                    .build();
            courseOrderItemMapper.insert(orderItem);
        }

        log.info("订单创建成功, orderNo={}, userId={}, totalAmount={}", orderNo, userId, totalAmount);
        return orderNo;
    }
}
