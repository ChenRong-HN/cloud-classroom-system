package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 支付订单业务层模型实体类
 *
 * @author x1angwan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "支付订单实体")
public class PayOrder {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 订单支付发生金额 */
    @Schema(description = "订单支付发生金额")
    private Long amount;
    /** 支付方式(0:余额支付、1:支付宝支付、2:微信支付、3:银联支付) */
    @Schema(description = "支付方式(0:余额支付、1:支付宝支付、2:微信支付、3:银联支付)")
    private Long payType;
    /** 业务关联Id */
    @Schema(description = "业务关联Id")
    private Long relationId;
    /** 订单号 */
    @Schema(description = "订单号")
    private String orderNo;
    /** 用户Id */
    @Schema(description = "用户Id")
    private Long userId;
    /** 订单扩展参数 */
    @Schema(description = "订单扩展参数")
    private String extParams;
    /** 订单描述 */
    @Schema(description = "订单描述")
    private String subject;
    /** 订单支付状态 */
    @Schema(description = "订单支付状态")
    private Long payStatus;
    /** 创建时间 */
    @Schema(description = "创建时间")
    private String createTime;
    /** 更新时间 */
    @Schema(description = "更新时间")
    private String updateTime;
}