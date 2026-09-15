package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 课件信息业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课件信息实体")
public class CourseResource {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 课程Id */
    @Schema(description = "课程Id")
    private Long courseId;
    /** 文件云地址 */
    @Schema(description = "文件云地址")
    private String resources;
    /** 资料类型(0课件,1其他) */
    @Schema(description = "资料类型(0课件,1其他)")
    private Long type;
}