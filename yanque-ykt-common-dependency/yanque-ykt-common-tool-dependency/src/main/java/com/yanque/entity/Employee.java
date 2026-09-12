package com.yanque.entity;

    import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 员工信息业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "员工信息实体")
public class Employee {
    /** 员工主键Id */
    @Schema(description = "员工主键Id")
    private Long id;
    /** 姓名 */
    @Schema(description = "姓名")
    private String realName;
    /** 电话 */
    @Schema(description = "电话")
    private String tel;
    /** 邮箱 */
    @Schema(description = "邮箱")
    private String email;
    /** 创建时间 */
    @Schema(description = "创建时间")
    private LocalDate inputTime;
    /** 状态(0:正常、1:锁定、2:注销) */
    @Schema(description = "状态(0:正常、1:锁定、2:注销)")
    private Long state;
    /** 部门Id */
    @Schema(description = "部门Id")
    private Long deptId;
    /** 员工类型(1:平台普通员工、2:平台客服人员、3:平台管理员、4:机构员工、5：机构管理员或其他) */
    @Schema(description = "员工类型(1:平台普通员工、2:平台客服人员、3:平台管理员、4:机构员工、5：机构管理员或其他)")
    private Integer type;
    /** 员工登录Id */
    @Schema(description = "员工登录Id")
    private Long loginId;
}