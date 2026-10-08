package com.yanque.entity.vo;

import com.yanque.entity.KillCourse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 秒杀课程响应VO模型
 * 在原有的秒杀课程的基础上添加了是否正在秒杀、是否未开始秒杀、距离开启秒杀时间相差多少秒字段
 *
 * @author cr
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "秒杀课程响应Vo响应模型")
public class KillCourseRespVo extends KillCourse {
    @Schema(description = "是否正在秒杀")
    private Boolean killing;
    @Schema(description = "是否未开始秒杀")
    private Boolean unbegin;
    @Schema(description = "距离开启秒杀时间差值")
    private Long timeDiffMill;
}
