package com.yanque.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 秒杀活动信息业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "秒杀活动信息实体")
public class KillActivity {
    /**
     * 秒杀活动主键Id
     */
    @Schema(description = "秒杀活动主键Id")
    private Long id;
    /**
     * 秒杀活动名字
     */
    @Schema(description = "秒杀活动名字")
    @NotNull(message = "活动名称不能为空")
    private String name;
    /**
     * 开始时间(字符串)
     */
    @Schema(description = "开始时间(字符串)")
    private String timeStr;
    /**
     * 秒杀活动开始时间
     */
    @Schema(description = "秒杀活动开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime beginTime;
    /**
     * 秒杀活动结束时间
     */
    @Schema(description = "秒杀活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;
    /**
     * 秒杀活动状态(0:活动待发布、1:活动已发布、2:活动取消、3:活动结束)
     */
    @Schema(description = "秒杀活动状态(0:活动待发布、1:活动已发布、2:活动取消、3:活动结束)")
    private Long publishStatus;
    /**
     * 秒杀活动发布时间
     */
    @Schema(description = "秒杀活动发布时间")
    private LocalDateTime publishTime;
}