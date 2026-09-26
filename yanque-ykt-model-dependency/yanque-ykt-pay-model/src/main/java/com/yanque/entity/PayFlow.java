package com.yanque.entity;

    import java.time.LocalDate;
    import java.time.LocalDateTime;

    import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 支付流水业务层模型实体类
 *
 * @author x1angwan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "支付流水实体")
public class PayFlow {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 交易时间 */
    @Schema(description = "交易时间")
    private LocalDateTime notifyTime;
    /** 标题 */
    @Schema(description = "标题")
    private String subject;
    /** 交易号(订单号) */
    @Schema(description = "交易号(订单号)")
    private String outTradeNo;
    /** 交易金额 */
    @Schema(description = "交易金额")
    private Long totalAmount;
    /** 交易状态 */
    @Schema(description = "交易状态")
    private String tradeStatus;
    /** 错误码 */
    @Schema(description = "错误码")
    private String code;
    /** 错误信息 */
    @Schema(description = "错误信息")
    private String msg;
    /** 回传参数 */
    @Schema(description = "回传参数")
    private String passbackParams;
    /** 是否支付成功 */
    @Schema(description = "是否支付成功")
    private Integer paySuccess;
    /** 结果描述 */
    @Schema(description = "结果描述")
    private String resultDesc;
}