package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 课程信息业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程信息实体")
public class Course {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 课程名称 */
    @Schema(description = "课程名称")
    private String name;
    /** 适用人群 */
    @Schema(description = "适用人群")
    private String forUser;
    /** 课程分类 */
    @Schema(description = "课程分类")
    private Long courseTypeId;
    /** 课程等级名称 */
    @Schema(description = "课程等级名称")
    private String gradeName;
    /** 课程等级 */
    @Schema(description = "课程等级")
    private Long gradeId;
    /** 课程状态(下线:0、上线:1) */
    @Schema(description = "课程状态(下线:0、上线:1)")
    private Long status;
    /** 添加课程员工Id */
    @Schema(description = "添加课程员工Id")
    private Long loginId;
    /** 添加课程员工名称 */
    @Schema(description = "添加课程员工名称")
    private String loginUserName;
    /** 课程开课时间 */
    @Schema(description = "课程开课时间")
    private LocalDate startTime;
    /** 课程结课时间 */
    @Schema(description = "课程结课时间")
    private LocalDate endTime;
    /** 封面URL地址 */
    @Schema(description = "封面URL地址")
    private String pic;
    /** 时长(分钟) */
    @Schema(description = "时长(分钟)")
    private Long totalMinute;
    /** 上线时间 */
    @Schema(description = "上线时间")
    private LocalDate onlineTime;
    /** 章节数量 */
    @Schema(description = "章节数量")
    private Long chapterCount;
    /** 讲师(以,分隔多位) */
    @Schema(description = "讲师(以,分隔多位)")
    private String teacherNames;
}