package com.yanque.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 角色权限业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色权限实体")
public class RolePermission {
    /** 角色Id */
    @Schema(description = "角色Id")
    private Long roleId;
    /** 权限Id */
    @Schema(description = "权限Id")
    private Long permissionId;
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
}