package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 课程分类业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程分类实体")
public class CourseType {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 课程分类名称 */
    @Schema(description = "课程分类名称")
    private String name;
    /** 父Id */
    @Schema(description = "父Id")
    private Long pid;
    /** 课程分类图标 */
    @Schema(description = "课程分类图标")
    private String logo;
    /** 课程分类描述 */
    @Schema(description = "课程分类描述")
    private String description;
    /** 排序索引 */
    @Schema(description = "排序索引")
    private Long sortIndex;
    /** 课程分类路径 */
    @Schema(description = "课程分类路径")
    private String path;
    /** 课程分类包含课程数量 */
    @Schema(description = "课程分类包含课程数量")
    private Long totalCount;
}