package com.yanque.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户端课程搜索条件Vo模型
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程搜索条件Vo模型")
public class CourseQueryVo {
    @Schema(description = "关键字(课程名称模糊搜索)")
    private String keyword;
    @Schema(description = "课程类型Id(精确匹配)")
    private Long courseTypeId;
    @Schema(description = "等级名称(精确匹配)")
    private String gradeName;
    @Schema(description = "收费名称(精确匹配)")
    private Long chargeName;
    @Schema(description = "价格下限(精确匹配)")
    private Double priceMin;
    @Schema(description = "价格上限(精确匹配)")
    private Double priceMax;
    @Schema(description = "排序字段")
    private String sortField;
    @Schema(description = "排序类型(升序、降序)")
    private String sortType;
    @Schema(description = "目标页码")
    @NotNull(message = "目标页码不能为空")
    private Long page;
    @Schema(description = "每页行数")
    @NotNull(message = "每页行数不能为空")
    private Long rows;
}
