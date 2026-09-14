package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 会员登录账号业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员登录账号实体")
public class User {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 三方登录名 */
    @Schema(description = "三方登录名")
    private String thirdUid;
    /** 手机号 */
    @Schema(description = "手机号")
    private String phone;
    /** 邮箱 */
    @Schema(description = "邮箱")
    private String email;
    /** 昵称 */
    @Schema(description = "昵称")
    private String nickName;
    /** 用户状态 */
    @Schema(description = "用户状态")
    private Long bitState;
    /** 安全级别 */
    @Schema(description = "安全级别")
    private Long secLevel;
    /** 登录Id */
    @Schema(description = "登录Id")
    private Long loginId;
}