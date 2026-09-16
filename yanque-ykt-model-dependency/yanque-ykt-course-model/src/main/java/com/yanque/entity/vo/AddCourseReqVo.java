package com.yanque.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 新增课程-数据模型
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "添加课程信息数据模型")
public class AddCourseReqVo {
    @Schema(description = "课程信息")
    private CourseReqVo course;
    @Schema(description = "课程详情信息")
    private CourseDetailReqVo courseDetail;
    @Schema(description = "课程营销信息")
    private CourseMarketReqVo courseMarket;
    @Schema(description = "课程资源信息")
    private CourseResourceReqVo courseResource;
    @Schema(description = "课程教师信息")
    private List<Long> teacharIds;
}
