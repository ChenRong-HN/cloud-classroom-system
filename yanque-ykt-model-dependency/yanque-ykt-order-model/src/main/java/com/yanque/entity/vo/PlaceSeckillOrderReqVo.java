package com.yanque.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 秒杀提交订单请求Vo模型
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "秒杀提交订单请求Vo模型")
public class PlaceSeckillOrderReqVo {
    @Schema(description = "预订单号")
    private String orderNo;
    @Schema(description = "支付类型")
    private Long payType;
    @Schema(description = "令牌")
    private String token;
}
