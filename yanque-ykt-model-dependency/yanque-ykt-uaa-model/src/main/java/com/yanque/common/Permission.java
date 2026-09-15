package com.yanque.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 权限业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "权限实体")
public class Permission {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 权限名称 */
    @Schema(description = "权限名称")
    private String name;
    /** 资源名称 */
    @Schema(description = "资源名称")
    private String resource;
    /** 权限状态 */
    @Schema(description = "权限状态")
    private Long state;
    /** 权限编号 */
    @Schema(description = "权限编号")
    private String sn;
}