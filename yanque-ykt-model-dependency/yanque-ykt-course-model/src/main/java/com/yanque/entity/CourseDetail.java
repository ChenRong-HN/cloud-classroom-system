package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 课程详情业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程详情实体")
public class CourseDetail {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 课程详情 */
    @Schema(description = "课程详情")
    private String description;
    /** 课程简介 */
    @Schema(description = "课程简介")
    private String intro;
}