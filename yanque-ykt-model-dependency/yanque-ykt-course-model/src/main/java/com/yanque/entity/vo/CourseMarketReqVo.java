package com.yanque.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程销售信息
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程销售信息数据模型")
public class CourseMarketReqVo {
    @Schema(description = "课程收费信息(2:收费、1:免费)")
    private Long charge;
    @Schema(description = "课程咨询人QQ")
    private String qq;
    @Schema(description = "课程价格")
    private Double price;
    @Schema(description = "课程原始价格")
    private Double priceOld;
    @Schema(description = "课程有效期天数")
    private Long validDays;
}
