package com.yanque.entity;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;

    import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 订单详情业务层模型实体类
 *
 * @author x1angwan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单详情实体")
public class CourseOrderItem {
    /** 订单明细主键Id */
    @Schema(description = "订单明细主键Id")
    private Long id;
    /** 订单主键Id */
    @Schema(description = "订单主键Id")
    private Long orderId;
    /** 订单明细价格 */
    @Schema(description = "订单明细价格")
    private BigDecimal amount;
    /** 订单明细数量 */
    @Schema(description = "订单明细数量")
    private Long count;
    /** 课程Id */
    @Schema(description = "课程Id")
    private Long courseId;
    /** 课程名字 */
    @Schema(description = "课程名字")
    private String courseName;
    /** 课程封面 */
    @Schema(description = "课程封面")
    private String coursePic;
    /** 订单项版本号 */
    @Schema(description = "订单项版本号")
    private Long version;
    /** 订单编号 */
    @Schema(description = "订单编号")
    private String orderNo;
    /** 创建时间 */
    @Schema(description = "订单创建时间")
    private LocalDateTime createTime;
    /** 更新时间 */
    @Schema(description = "订单更新时间")
    private LocalDateTime updateTime;
}