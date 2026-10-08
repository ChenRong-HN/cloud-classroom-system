package com.yanque.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 预订单Vo模型
 *
 * @author cr
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "预订单Vo模型")
public class PreSeckillOrderVo {
    @Schema(description = "订单号")
    private String orderNo;
    @Schema(description = "秒杀价格")
    private BigDecimal killPrice;
    @Schema(description = "购买数量")
    private Long quantity;
    @Schema(description = "用户Id")
    private Long userId;
    @Schema(description = "秒杀课程Id")
    private Long killCourseId;
    @Schema(description = "原始课程Id")
    private Long originCourseId;
    @Schema(description = "课程首页图片")
    private String coursePic;
    @Schema(description = "活动Id")
    private Long activityId;
}
