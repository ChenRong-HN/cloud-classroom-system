package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 支付宝参数信息业务层模型实体类
 *
 * @author x1angwan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "支付宝参数信息实体")
public class AlipayInfo {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 应用私钥 */
    @Schema(description = "应用私钥")
    private String merchantPrivateKey;
    /** 应用AppId */
    @Schema(description = "应用AppId")
    private String appId;
    /** 支付宝公钥 */
    @Schema(description = "支付宝公钥")
    private String alipayPublicKey;
    /** 协议类型 */
    @Schema(description = "协议类型")
    private String protocol;
    /** 支付宝网关地址 */
    @Schema(description = "支付宝网关地址")
    private String gatewayHost;
    /** 签名类型 */
    @Schema(description = "签名类型")
    private String signType;
    /** 异步通知回调地址 */
    @Schema(description = "异步通知回调地址")
    private String notifyUrl;
    /** 同步跳转回调地址 */
    @Schema(description = "同步跳转回调地址")
    private String returnUrl;
    /** 创建时间 */
    @Schema(description = "创建时间")
    private String createTime;
    /** 更新时间 */
    @Schema(description = "更新时间")
    private String updateTime;
}