package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 用户课程学习业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户课程学习实体")
public class CourseUserLearn {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 登录用户Id */
    @Schema(description = "登录用户Id")
    private Long loginId;
    /** 开始时间 */
    @Schema(description = "开始时间")
    private Long startTime;
    /** 结束时间 */
    @Schema(description = "结束时间")
    private Long endTime;
    /** 购买状态(0:已购买、1:未购买) */
    @Schema(description = "购买状态(0:已购买、1:未购买)")
    private Long state;
    /** 课程Id */
    @Schema(description = "课程Id")
    private Long courseId;
    /** 课程订单号 */
    @Schema(description = "课程订单号")
    private String courseOrderNo;
}