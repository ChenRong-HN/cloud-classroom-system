package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 会员实名资料业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员实名资料实体")
public class UserRealInfo {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 登录用户 */
    @Schema(description = "登录用户")
    private Long userId;
    /** 真实姓名 */
    @Schema(description = "真实姓名")
    private String realName;
    /** 身份证号 */
    @Schema(description = "身份证号")
    private String idCardNo;
    /** 身份证正面 */
    @Schema(description = "身份证正面")
    private String idCardFront;
    /** 身份证反面 */
    @Schema(description = "身份证反面")
    private String idCardBack;
    /** 手持身份证 */
    @Schema(description = "手持身份证")
    private String idCardHand;
    /** 审核状态 */
    @Schema(description = "审核状态")
    private Long state;
    /** 提交时间 */
    @Schema(description = "提交时间")
    private Long applyTime;
    /** 审核时间 */
    @Schema(description = "审核时间")
    private Long auditTime;
    /** 审核人 */
    @Schema(description = "审核人")
    private String auditUser;
}