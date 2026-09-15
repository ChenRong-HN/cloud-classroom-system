package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 会员账户信息业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员账户信息实体")
public class UserAccount {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 可用金额 */
    @Schema(description = "可用金额")
    private Long usableAmount;
    /** 冻结金额 */
    @Schema(description = "冻结金额")
    private Long frozenAmount;
    /** 支付密码 */
    @Schema(description = "支付密码")
    private String password;
    /** 创建时间 */
    @Schema(description = "创建时间")
    private Long createTime;
    /** 更新时间 */
    @Schema(description = "更新时间")
    private Long updateTime;
}