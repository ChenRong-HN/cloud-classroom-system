package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 老师业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "老师实体")
public class Teacher {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 老师姓名 */
    @Schema(description = "老师姓名")
    private String name;
    /** 简介 */
    @Schema(description = "简介")
    private String intro;
    /** 技术栈 */
    @Schema(description = "技术栈")
    private String technology;
    /** 职位数据 */
    @Schema(description = "职位数据")
    private String position;
    /** 头像URL地址 */
    @Schema(description = "头像URL地址")
    private String headImg;
    /** 老师标签 */
    @Schema(description = "老师标签")
    private String tags;
}