package com.yanque.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 保存订单参数vo
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "下单请求参数Vo模型")
public class PlaceOrderReqVo {
    @NotEmpty(message = "课程Id列表不能为空")
    @Schema(description = "课程Id列表")
    private List<Long> courseIds;

    @NotNull(message = "支付方式不能为空")
    @Schema(description = "支付方式")
    private Long payType;

    @NotNull(message = "订单类型不能为空")
    @Schema(description = "订单类型")
    private Integer type;

    @NotEmpty(message = "防止重复提交Token不能为空")
    @Schema(description = "防止重复提交Token")
    private String token;
}
