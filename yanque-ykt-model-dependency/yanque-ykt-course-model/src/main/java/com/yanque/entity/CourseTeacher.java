package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 课程老师中间业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程老师中间实体")
public class CourseTeacher {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 老师Id */
    @Schema(description = "老师Id")
    private Long teacherId;
    /** 课程Id */
    @Schema(description = "课程Id")
    private Long courseId;
}