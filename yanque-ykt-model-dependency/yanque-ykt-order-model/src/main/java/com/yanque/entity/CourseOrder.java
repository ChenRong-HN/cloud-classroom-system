package com.yanque.entity;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;

    import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 订单业务层模型实体类
 *
 * @author x1angwan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单实体")
public class CourseOrder {
    /** 订单主键id */
    @Schema(description = "订单主键Id")
    private Long id;
    /** 订单编号 */
    @Schema(description = "订单编号")
    private String orderNo;
    /** 订单支付总金额 */
    @Schema(description = "订单支付总金额")
    private BigDecimal totalAmount;
    /** 订单课程数量 */
    @Schema(description = "订单课程数量")
    private Long totalCount;
    /** 订单状态(0:下单成功待支付、1:支付完成订单完成、2:用户手动取消订单、3:支付失败、4:超时自动取消订单) */
    @Schema(description = "订单状态(0:下单成功待支付、1:支付完成订单完成、2:用户手动取消订单、3:支付失败、4:超时自动取消订单)")
    private Long statusOrder;
    /** 下单用户Id */
    @Schema(description = "下单用户Id")
    private Long userId;
    /** 订单标题 */
    @Schema(description = "订单标题")
    private String title;
    /** 订单版本号 */
    @Schema(description = "订单版本号")
    private Long version;
    /** 支付方式(0:账户余额直接、1:支付宝、2:微信、3:银联) */
    @Schema(description = "支付方式(0:账户余额直接、1:支付宝、2:微信、3:银联)")
    private Long payType;
    /** 创建时间 */
    @Schema(description = "订单创建时间")
    private LocalDateTime createTime;
    /** 更新时间 */
    @Schema(description = "订单更新时间")
    private LocalDateTime updateTime;
}