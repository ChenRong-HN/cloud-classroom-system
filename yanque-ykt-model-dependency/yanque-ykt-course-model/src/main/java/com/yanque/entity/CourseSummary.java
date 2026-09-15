package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 课程统计业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程统计实体")
public class CourseSummary {
    /** 课程Id */
    @Schema(description = "课程Id")
    private Long id;
    /** 销量数数据 */
    @Schema(description = "销量数数据")
    private Long saleCount;
    /** 浏览量数据 */
    @Schema(description = "浏览量数据")
    private Long viewCount;
    /** 评论量数据 */
    @Schema(description = "评论量数据")
    private Long commentCount;
}