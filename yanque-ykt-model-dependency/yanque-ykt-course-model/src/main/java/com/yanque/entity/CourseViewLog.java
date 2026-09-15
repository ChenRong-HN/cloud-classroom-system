package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 课程浏览记录业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程浏览记录实体")
public class CourseViewLog {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 课程Id */
    @Schema(description = "课程Id")
    private Long courseId;
    /** 用户Id */
    @Schema(description = "用户Id")
    private Long userId;
}