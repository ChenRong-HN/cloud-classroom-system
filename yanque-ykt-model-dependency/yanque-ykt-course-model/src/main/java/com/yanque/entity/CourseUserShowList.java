package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户端课程展示列业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户端课程展示列实体")
public class CourseUserShowList {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 课程Id */
    @Schema(description = "课程Id")
    private Long courseId;
    /** 课程名称 */
    @Schema(description = "课程名称")
    private String name;
    /** 课程价格 */
    @Schema(description = "课程价格")
    private BigDecimal price;
    /** 课程图片 */
    @Schema(description = "课程图片")
    private String pic;
    /** 适用人群 */
    @Schema(description = "适用人群")
    private String forUser;
    /** 课程分类Id */
    @Schema(description = "课程分类Id")
    private Long courseTypeId;
    /** 课程等级名称 */
    @Schema(description = "课程等级名称")
    private String gradeName;
    /** 课程发布时间 */
    @Schema(description = "课程发布时间")
    private LocalDate onlineTime;
    /** 讲师名称，多个讲师用逗号分隔 */
    @Schema(description = "讲师名称，多个讲师用逗号分隔")
    private String teacherNames;
    /** 销量数据 */
    @Schema(description = "销量数据")
    private Long saleCount;
    /** 浏览量数据 */
    @Schema(description = "浏览量数据")
    private Long viewCount;
    /** 评论数数据 */
    @Schema(description = "评论数数据")
    private Long commentCount;
    /** 收费规则(1:免费、2:收费) */
    @Schema(description = "收费规则(1:免费、2:收费)")
    private Long charge;
    /** 课程原价 */
    @Schema(description = "课程原价")
    private BigDecimal priceOld;
}