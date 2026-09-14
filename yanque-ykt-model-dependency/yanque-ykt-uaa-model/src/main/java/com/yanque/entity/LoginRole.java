package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 用户角色中间业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户角色中间实体")
public class LoginRole {
    /** 登录Id */
    @Schema(description = "登录Id")
    private Long loginId;
    /** 角色Id */
    @Schema(description = "角色Id")
    private Long roleId;
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
}