package com.yanque.entity.vo;

import com.yanque.entity.Course;
import com.yanque.entity.CourseMarket;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程订单确认项响应Vo模型
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程订单确认项响应Vo模型")
public class CourseOrderConfirmItemRespVo {
    @Schema(description = "课程信息")
    private Course course;
    @Schema(description = "课程销售信息")
    private CourseMarket courseMarket;
}
