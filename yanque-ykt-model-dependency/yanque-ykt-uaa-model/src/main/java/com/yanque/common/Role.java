package com.yanque.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 角色业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色实体")
public class Role {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 角色名称 */
    @Schema(description = "角色名称")
    private String name;
    /** 角色编号 */
    @Schema(description = "角色编号")
    private String sn;
}