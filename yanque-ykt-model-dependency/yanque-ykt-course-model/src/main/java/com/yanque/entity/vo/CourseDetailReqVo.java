package com.yanque.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程详情信息数据模型
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程详情信息数据模型")
public class CourseDetailReqVo {
    @Schema(description = "课程简介信息")
    private String description;
    @Schema(description = "课程简介详情信息")
    private String intro;
}
