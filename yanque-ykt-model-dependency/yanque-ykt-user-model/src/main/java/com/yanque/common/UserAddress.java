package com.yanque.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 会员收货地址业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员收货地址实体")
public class UserAddress {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 登录用户 */
    @Schema(description = "登录用户")
    private Long userId;
    /** 收货人 */
    @Schema(description = "收货人")
    private String reciver;
    /** 区域 */
    @Schema(description = "区域")
    private String areaCode;
    /** 详细地址 */
    @Schema(description = "详细地址")
    private String address;
    /** 全地址 */
    @Schema(description = "全地址")
    private String fullAddress;
    /** 手机号码 */
    @Schema(description = "手机号码")
    private String phone;
    /** 备用手机号 */
    @Schema(description = "备用手机号")
    private String phoneBack;
    /** 固定电话 */
    @Schema(description = "固定电话")
    private String tel;
    /** 邮编 */
    @Schema(description = "邮编")
    private String postCode;
    /** 电子邮件 */
    @Schema(description = "电子邮件")
    private String email;
    /** 是否默认 */
    @Schema(description = "是否默认")
    private Long defaultAddress;
}