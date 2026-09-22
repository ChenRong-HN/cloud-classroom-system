package com.yanque.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础分页查询参数模型
 *
 * @author cr
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "基础分页查询参数模型")
public class BasicPageVo {
    @Schema(description = "查询关键字")
    private String keyword;
    @Schema(description = "目标页码")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码不能小于1")
    private Long page;
    @Schema(description = "每页数量")
    private Long rows;
}
