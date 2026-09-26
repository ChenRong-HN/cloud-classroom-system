package com.yanque.entity;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.time.LocalDateTime;

    import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 秒杀课程信息业务层模型实体类
 *
 * @author x1angwan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "秒杀课程信息实体")
public class KillCourse {
    /** 秒杀课程Id */
    @Schema(description = "秒杀课程Id")
    private Long id;
    /** 秒杀课程名字 */
    @Schema(description = "秒杀课程名字")
    private String courseName;
    /** 秒杀课程Id */
    @Schema(description = "秒杀课程Id")
    private Long courseId;
    /** 秒杀课程价格 */
    @Schema(description = "秒杀课程价格")
    private BigDecimal killPrice;
    /** 秒杀课程库存 */
    @Schema(description = "秒杀课程库存")
    private Long killCount;
    /** 秒杀课程默认可购买数量 */
    @Schema(description = "秒杀课程默认可购买数量")
    private Long killLimit;
    /** 秒杀课程排序字段 */
    @Schema(description = "秒杀课程排序字段")
    private Long killSort;
    /** 秒杀状态(0:待发布、1:秒杀中、2:秒杀结束) */
    @Schema(description = "秒杀状态(0:待发布、1:秒杀中、2:秒杀结束)")
    private Long publishStatus;
    /** 秒杀课程封面 */
    @Schema(description = "秒杀课程封面")
    private String coursePic;
    /** 秒杀开始时间 */
    @Schema(description = "秒杀开始时间")
    private LocalDateTime startTime;
    /** 秒杀结束时间 */
    @Schema(description = "秒杀结束时间")
    private LocalDateTime endTime;
    /** 发布到Redis的时间 */
    @Schema(description = "发布到Redis的时间")
    private LocalDateTime publishTime;
    /** 课程老师名称(使用,隔开) */
    @Schema(description = "课程老师名称(使用,隔开)")
    private String teacherNames;
    /** 秒杀课程下线时间 */
    @Schema(description = "秒杀课程下线时间")
    private LocalDateTime offlineTime;
    /** 秒杀活动Id */
    @Schema(description = "秒杀活动Id")
    private Long activityId;
    /** 开始时间(字符串) */
    @Schema(description = "开始时间(字符串)")
    private String timeStr;
    /** 创建时间 */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}