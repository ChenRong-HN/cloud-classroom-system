package com.yanque.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 提交秒杀请求参数Vo模型
 *
 * @author cr
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "提交秒杀请求参数Vo模型")
public class KillReqVo {
    @NotNull(message = "秒杀课程Id不能为空")
    @Schema(description = "秒杀课程Id")
    private Long killCourseId;

    @NotNull(message = "秒杀活动Id不能为空")
    @Schema(description = "秒杀活动Id")
    private Long killActivityId;
}
