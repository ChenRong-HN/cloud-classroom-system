package com.yanque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 会员基本信息业务层模型实体类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员基本信息实体")
public class UserBaseInfo {
    /** 主键Id */
    @Schema(description = "主键Id")
    private Long id;
    /** 注册渠道 */
    @Schema(description = "注册渠道")
    private Long regChannel;
    /** QQ */
    @Schema(description = "QQ")
    private String qq;
    /** 用户等级 */
    @Schema(description = "用户等级")
    private Long level;
    /** 成长值 */
    @Schema(description = "成长值")
    private Long growScore;
    /** 推荐人 */
    @Schema(description = "推荐人")
    private Long referId;
    /** 性别 */
    @Schema(description = "性别")
    private Long sex;
    /** 生日 */
    @Schema(description = "生日")
    private Long birthday;
    /** 区域编码 */
    @Schema(description = "区域编码")
    private String areaCode;
    /** 地址 */
    @Schema(description = "地址")
    private String address;
    /** 创建时间 */
    @Schema(description = "创建时间")
    private Long createTime;
    /** 更新时间 */
    @Schema(description = "更新时间")
    private Long updateTime;
}