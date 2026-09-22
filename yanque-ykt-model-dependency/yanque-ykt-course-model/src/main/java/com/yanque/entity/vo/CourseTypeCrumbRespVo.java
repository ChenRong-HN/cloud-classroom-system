package com.yanque.entity.vo;

import com.yanque.entity.CourseType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 面包屑导航响应Vo模型
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "面包屑导航响应Vo模型")
public class CourseTypeCrumbRespVo {
    @Schema(description = "当前分类(面包屑主展示)")
    private CourseType ownerProductType;
    @Schema(description = "其他分类(面包屑辅助展示)")
    private List<CourseType> otherProductTypes;
}
