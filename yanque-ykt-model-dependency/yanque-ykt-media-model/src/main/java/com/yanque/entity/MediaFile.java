package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 课程媒体文件业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程媒体文件实体")
public class MediaFile {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 对象存储Key */
    @Schema(description = "对象存储Key")
    private String objectKey;
    /** 源文件名称 */
    @Schema(description = "源文件名称")
    private String fileOriginalName;
    /** 源文件文件类型 */
    @Schema(description = "源文件文件类型")
    private String fileType;
    /** 文件总大小(字节) */
    @Schema(description = "文件总大小(字节)")
    private Long fileSize;
    /** 上传时间 */
    @Schema(description = "上传时间")
    private LocalDateTime uploadTime;
    /** 所属章节Id */
    @Schema(description = "所属章节Id")
    private Long chapterId;
    /** 所属课程Id */
    @Schema(description = "所属课程Id")
    private Long courseId;
    /** 视频序号 */
    @Schema(description = "视频序号")
    private Long number;
    /** 视频名称 */
    @Schema(description = "视频名称")
    private String name;
    /** 课程名称 */
    @Schema(description = "课程名称")
    private String courseName;
    /** 章节名称 */
    @Schema(description = "章节名称")
    private String chapterName;
    /** 视频时长(单位:分钟) */
    @Schema(description = "视频时长(单位:分钟)")
    private Long timeMinute;
    /** 是否免费试看(0:否、1:是) */
    @Schema(description = "是否免费试看(0:否、1:是)")
    private Integer free;
}