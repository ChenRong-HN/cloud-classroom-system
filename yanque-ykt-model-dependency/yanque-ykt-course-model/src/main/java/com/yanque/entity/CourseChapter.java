package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 课程章节业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程章节实体")
public class CourseChapter {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 章节名称 */
    @Schema(description = "章节名称")
    private String name;
    /** 章节编号 */
    @Schema(description = "章节编号")
    private Long number;
    /** 课程Id */
    @Schema(description = "课程Id")
    private Long courseId;
    /** 课程名称 */
    @Schema(description = "课程名称")
    private String courseName;
}