package com.yanque.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *课程基础信息
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程基础信息数据模型")
public class CourseReqVo {
    @Schema(description = "课程名称")
    private String name;
    @Schema(description = "课程面向用户")
    private String forUser;
    @Schema(description = "课程类型Id")
    private Long courseTypeId;
    @Schema(description = "课程等级Id")
    private Long gradeId;
    @Schema(description = "课程等级名称")
    private String gradeName;
    @Schema(description = "课程图片URL地址")
    private String pic;
    @Schema(description = "课程开始时间")
    private LocalDate startTime;
    @Schema(description = "课程结束时间")
    private LocalDate endTime;
}
