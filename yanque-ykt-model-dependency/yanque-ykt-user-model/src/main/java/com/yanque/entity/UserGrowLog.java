package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 会员成长值记录业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员成长值记录实体")
public class UserGrowLog {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 登录用户 */
    @Schema(description = "登录用户")
    private Long userId;
    /** 来源 */
    @Schema(description = "来源")
    private String fromReason;
    /** 成长值 */
    @Schema(description = "成长值")
    private Long score;
}