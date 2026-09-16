package com.yanque.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 响应数据类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeVo {
    // 树形节点Id
    private Long id;
    // 树形节点名称
    private String name;
    // 树形节点子节点List集合
    private List<TreeVo> children;
    // 📌 当前树形节点的父节点Id
    private Long pid;
}
