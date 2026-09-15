package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 系统字典选项业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "系统字典选项实体")
public class SystemDictionaryItem {
    /** 系统字段选项主键Id */
    @Schema(description = "系统字段选项主键Id")
    private Long id;
    /** 系统字典选项对应字典主键Id */
    @Schema(description = "系统字典选项对应字典主键Id")
    private Long parentId;
    /** 系统字典选项名称 */
    @Schema(description = "系统字典选项名称")
    private String name;
    /** 系统字段选项排序字段值 */
    @Schema(description = "系统字段选项排序字段值")
    private Long sequence;
    /** 系统字典选项介绍 */
    @Schema(description = "系统字典选项介绍")
    private String intro;
}