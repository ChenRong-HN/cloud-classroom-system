package com.yanque.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 会员账户流水业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员账户流水实体")
public class AccountFlow {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 流水金额 */
    @Schema(description = "流水金额")
    private Long amount;
    /** 账户Id */
    @Schema(description = "账户Id")
    private Long accountId;
    /** 流水类型：充值，提现，购买课程 */
    @Schema(description = "流水类型：充值，提现，购买课程")
    private Long businessType;
    /** 业务名称 */
    @Schema(description = "业务名称")
    private String businessName;
}