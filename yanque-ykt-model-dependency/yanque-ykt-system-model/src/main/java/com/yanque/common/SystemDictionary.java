package com.yanque.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 系统字典业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "系统字典实体")
public class SystemDictionary {
    /** 系统字典主键Id */
    @Schema(description = "系统字典主键Id")
    private Long id;
    /** 系统字典编号 */
    @Schema(description = "系统字典编号")
    private String sn;
    /** 系统字典名称 */
    @Schema(description = "系统字典名称")
    private String name;
    /** 介绍描述 */
    @Schema(description = "介绍描述")
    private String intro;
    /** 系统字典状态(0:禁用、1:启用) */
    @Schema(description = "系统字典状态(0:禁用、1:启用)")
    private Long state;
}