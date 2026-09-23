package com.yanque.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页查询结果Vo类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiPageResponse<T> {
    // 满足本次查询条件的数据的总条数
    private Long total;
    // 满足本次查询条件的数据列表
    private List<T> rows;
}
