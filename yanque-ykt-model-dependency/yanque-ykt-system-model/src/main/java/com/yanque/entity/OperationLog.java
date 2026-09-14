package com.yanque.entity;

    import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 操作日志记录业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "操作日志记录实体")
public class OperationLog {
    /** 日志主键Id */
    @Schema(description = "日志主键Id")
    private Long id;
    /** 模块标题 */
    @Schema(description = "模块标题")
    private String title;
    /** 业务类型(0:其它、1:新增、2:修改、3:删除) */
    @Schema(description = "业务类型(0:其它、1:新增、2:修改、3:删除)")
    private Long businessType;
    /** 方法名称 */
    @Schema(description = "方法名称")
    private String method;
    /** 请求方式 */
    @Schema(description = "请求方式")
    private String requestMethod;
    /** 操作类别(0:其他、1:后台用户、2:手机端用户) */
    @Schema(description = "操作类别(0:其他、1:后台用户、2:手机端用户)")
    private Long operatorType;
    /** 操作人员 */
    @Schema(description = "操作人员")
    private String operName;
    /** 部门名称 */
    @Schema(description = "部门名称")
    private String deptName;
    /** 请求URL */
    @Schema(description = "请求URL")
    private String operUrl;
    /** 主机地址 */
    @Schema(description = "主机地址")
    private String operIp;
    /** 操作地点 */
    @Schema(description = "操作地点")
    private String operLocation;
    /** 请求参数 */
    @Schema(description = "请求参数")
    private String operParam;
    /** 返回参数 */
    @Schema(description = "返回参数")
    private String jsonResult;
    /** 操作状态(0:正常、1:异常) */
    @Schema(description = "操作状态(0:正常、1:异常)")
    private Long status;
    /** 错误消息 */
    @Schema(description = "错误消息")
    private String errorMsg;
    /** 操作时间 */
    @Schema(description = "操作时间")
    private LocalDate operTime;
}