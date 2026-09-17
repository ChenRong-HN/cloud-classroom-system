package com.yanque.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "查询关键词")
    private String keyword;
    @Schema(name = "查询的目标页数")
    private Long page;
}
