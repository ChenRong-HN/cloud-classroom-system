package com.yanque.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 参数配置业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "参数配置实体")
public class Config {
    /** 参数主键Id */
    @Schema(description = "参数主键Id")
    private Long id;
    /** 参数名称 */
    @Schema(description = "参数名称")
    private String configName;
    /** 参数键名 */
    @Schema(description = "参数键名")
    private String configKey;
    /** 参数键值 */
    @Schema(description = "参数键值")
    private String configValue;
    /** 系统内置(Y:是,N:否) */
    @Schema(description = "系统内置(Y:是,N:否)")
    private String configType;
}