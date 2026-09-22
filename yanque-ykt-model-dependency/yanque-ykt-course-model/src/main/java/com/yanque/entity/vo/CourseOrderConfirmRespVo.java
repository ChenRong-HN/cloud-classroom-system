package com.yanque.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 课程订单确认响应Vo模型
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程订单确认响应Vo模型")
public class CourseOrderConfirmRespVo {
    @Schema(description = "课程订单确认项列表")
    private List<CourseOrderConfirmItemRespVo> items;
    @Schema(description = "订单总金额")
    private BigDecimal totalAmount;
}
