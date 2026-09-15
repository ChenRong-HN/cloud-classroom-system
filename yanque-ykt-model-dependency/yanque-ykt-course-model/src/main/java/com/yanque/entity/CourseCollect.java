package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 课程收藏业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程收藏实体")
public class CourseCollect {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 登录用户 */
    @Schema(description = "登录用户")
    private Long userId;
    /** 课程Id */
    @Schema(description = "课程Id")
    private Long courseId;
}