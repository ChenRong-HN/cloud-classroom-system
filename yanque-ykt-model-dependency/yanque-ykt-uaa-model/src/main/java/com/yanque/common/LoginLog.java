package com.yanque.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 登录记录业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "登录记录实体")
public class LoginLog {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 登录Id */
    @Schema(description = "登录Id")
    private Long loginId;
    /** Ip地址 */
    @Schema(description = "Ip地址")
    private String ip;
    /** 登录客户端 */
    @Schema(description = "登录客户端")
    private String clientInfo;
    /** 登录方式 */
    @Schema(description = "登录方式")
    private Long loginType;
    /** 登录结果 */
    @Schema(description = "登录结果")
    private Long success;
}