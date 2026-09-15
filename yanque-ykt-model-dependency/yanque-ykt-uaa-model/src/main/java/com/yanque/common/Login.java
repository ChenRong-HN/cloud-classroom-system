package com.yanque.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 登录数据业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "登录数据实体")
public class Login {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 登录用户名 */
    @Schema(description = "登录用户名")
    private String username;
    /** 登录密码 */
    @Schema(description = "登录密码")
    private String password;
    /** 登录类型(0:后台、1:前台) */
    @Schema(description = "登录类型(0:后台、1:前台)")
    private Integer type;
    /** 是否启用 */
    @Schema(description = "是否启用")
    private Integer enabled;
    /** 账户是否未过期 */
    @Schema(description = "账户是否未过期")
    private Integer accountNonExpired;
    /** 凭证是否未过期 */
    @Schema(description = "凭证是否未过期")
    private Integer credentialsNonExpired;
    /** 账户是否未锁定 */
    @Schema(description = "账户是否未锁定")
    private Integer accountNonLocked;
    /** 头像URL地址 */
    @Schema(description = "头像URL地址")
    private String avatar;
}