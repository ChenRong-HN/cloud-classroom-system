package com.yanque.entity.vo;

import com.yanque.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 课程详情数据Vo模型
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程详情数据Vo模型")
public class CourseDetailRespVo {
    @Schema(description = "课程基本信息")
    private Course course;
    @Schema(description = "课程销售信息")
    private CourseMarket courseMarket;
    @Schema(description = "课程简介信息")
    private CourseDetail courseDetail;
    @Schema(description = "课程统计信息")
    private CourseSummary courseSummary;
    @Schema(description = "课程讲师信息集合")
    private List<Teacher> teachers;
    @Schema(description = "课程章节信息(包含媒体数据)集合")
    private List<CourseChapterMediaRespVo> courseChapters;
}
