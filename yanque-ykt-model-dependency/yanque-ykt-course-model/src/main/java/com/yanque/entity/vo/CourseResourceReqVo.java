package com.yanque.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程资源信息
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程课件资源信息模型")
public class CourseResourceReqVo {
    @Schema(description = "课程课件资源Oss访问地址")
    private String resources;
    @Schema(description = "课程课件资源类型(0:课件)")
    private String type;
}
