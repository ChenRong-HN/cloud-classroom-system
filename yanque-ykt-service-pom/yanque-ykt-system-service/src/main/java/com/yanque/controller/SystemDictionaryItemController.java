package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.SystemDictionaryItem;
import com.yanque.service.ISystemDictionaryItemService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统字典选项控制层接口
 *
 * @author cr
 */
@Tag(name = "系统字典选项管理", description = "系统字典选项接口")
@RestController
@RequestMapping("/yanque/item")
public class SystemDictionaryItemController {

    // 注入系统字典选项服务层接口实现类
    @Resource
    private ISystemDictionaryItemService systemDictionaryItemService;

    /**
     * 查询系统字典选项列表
     *
     * @return 全局通用返回结果(系统字典选项所有数据})
     */
    @Operation(summary = "查询系统字典选项列表", description = "查询所有系统字典选项信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<SystemDictionaryItem>> list() {
        // 调用系统字典选项服务层接口查询所有系统字典选项信息
        List<SystemDictionaryItem> list = systemDictionaryItemService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询系统字典选项
     *
     * @param id 系统字典选项主键Id
     * @return 全局通用返回结果(系统字典选项实体数据)
     */
    @Operation(summary = "根据Id查询系统字典选项", description = "根据主键Id查询系统字典选项详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<SystemDictionaryItem> getById(@Parameter(description = "系统字典选项Id") @PathVariable("id") Long id) {
        // 调用系统字典选项服务层接口根据Id查询系统字典选项信息
        SystemDictionaryItem entity = systemDictionaryItemService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增系统字典选项
     *
     * @param systemDictionaryItem 系统字典选项实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增系统字典选项", description = "新增系统字典选项信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody SystemDictionaryItem systemDictionaryItem) {
        // 调用系统字典选项服务层接口保存系统字典选项信息
        systemDictionaryItemService.save(systemDictionaryItem);
        return ApiResponse.success();
    }

    /**
     * 修改系统字典选项
     *
     * @param systemDictionaryItem 系统字典选项实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改系统字典选项", description = "修改系统字典选项信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody SystemDictionaryItem systemDictionaryItem) {
        // 调用系统字典选项服务层接口修改系统字典选项信息
        systemDictionaryItemService.updateById(systemDictionaryItem);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除系统字典选项
     *
     * @param id 系统字典选项主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除系统字典选项", description = "根据Id删除系统字典选项信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "系统字典选项Id") @PathVariable("id") Long id) {
        // 调用系统字典选项服务层接口根据Id删除系统字典选项信息
        systemDictionaryItemService.removeById(id);
        return ApiResponse.success();
    }
}