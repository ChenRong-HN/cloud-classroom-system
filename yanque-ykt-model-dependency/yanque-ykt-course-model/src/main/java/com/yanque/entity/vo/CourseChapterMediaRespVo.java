package com.yanque.entity.vo;

import com.yanque.entity.MediaFile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 课程章节(包含章节对应的媒体数据)Vo模型
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程章节(包含章节对应的媒体数据)Vo模型")
public class CourseChapterMediaRespVo {
    /**
     * 主键Id
     */
    @Schema(description = "主键Id")
    private Long id;
    /**
     * 章节名称
     */
    @Schema(description = "章节名称")
    private String name;
    /**
     * 章节编号
     */
    @Schema(description = "章节编号")
    private Long number;
    /**
     * 课程Id
     */
    @Schema(description = "课程Id")
    private Long courseId;
    /**
     * 课程名称
     */
    @Schema(description = "课程名称")
    private String courseName;
    /**
     * 章节包含媒体文件列表
     */
    @Schema(description = "章节包含媒体文件列表")
    private List<MediaFile> mediaFiles;
}
