package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 课程销售信息业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程销售信息实体")
public class CourseMarket {
    /** 课程Id */
    @Schema(description = "课程Id")
    private Long id;
    /** 收费规则(1:免费、2:收费) */
    @Schema(description = "收费规则(1:免费、2:收费)")
    private Long charge;
    /** 咨询QQ号码 */
    @Schema(description = "咨询QQ号码")
    private String qq;
    /** 当前价格 */
    @Schema(description = "当前价格")
    private BigDecimal price;
    /** 原始价格 */
    @Schema(description = "原始价格")
    private BigDecimal priceOld;
    /** 有效天数 */
    @Schema(description = "有效天数")
    private Long validDays;
}