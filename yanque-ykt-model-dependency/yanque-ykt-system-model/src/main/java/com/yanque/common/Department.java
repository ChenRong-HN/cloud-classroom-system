package com.yanque.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 部门信息业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "部门信息实体")
public class Department {
    /** 部门主键Id */
    @Schema(description = "部门主键Id")
    private Long id;
    /** 部门编号 */
    @Schema(description = "部门编号")
    private String sn;
    /** 部门名称 */
    @Schema(description = "部门名称")
    private String name;
    /** 部门的上级分类层级Id */
    @Schema(description = "部门的上级分类层级Id")
    private String dirPath;
    /** 部门状态(0:正常、1:禁用) */
    @Schema(description = "部门状态(0:正常、1:禁用)")
    private Long state;
    /** 部门管理员,关联Employee表Id */
    @Schema(description = "部门管理员,关联Employee表Id")
    private Long managerId;
    /** 上级部门 */
    @Schema(description = "上级部门")
    private Long parentId;
    /** 部门所属机构(租户) */
    @Schema(description = "部门所属机构(租户)")
    private Long tenantId;
}