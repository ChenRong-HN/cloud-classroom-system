package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.SystemDictionary;
import com.yanque.service.ISystemDictionaryService;
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
 * 系统字典控制层接口
 *
 * @author cr
 */
@Tag(name = "系统字典管理", description = "系统字典接口")
@RestController
@RequestMapping("/yanque/dictionary")
public class SystemDictionaryController {

    // 注入系统字典服务层接口实现类
    @Resource
    private ISystemDictionaryService systemDictionaryService;

    /**
     * 查询系统字典列表
     *
     * @return 全局通用返回结果(系统字典所有数据})
     */
    @Operation(summary = "查询系统字典列表", description = "查询所有系统字典信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<SystemDictionary>> list() {
        // 调用系统字典服务层接口查询所有系统字典信息
        List<SystemDictionary> list = systemDictionaryService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询系统字典
     *
     * @param id 系统字典主键Id
     * @return 全局通用返回结果(系统字典实体数据)
     */
    @Operation(summary = "根据Id查询系统字典", description = "根据主键Id查询系统字典详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<SystemDictionary> getById(@Parameter(description = "系统字典Id") @PathVariable("id") Long id) {
        // 调用系统字典服务层接口根据Id查询系统字典信息
        SystemDictionary entity = systemDictionaryService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增系统字典
     *
     * @param systemDictionary 系统字典实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增系统字典", description = "新增系统字典信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody SystemDictionary systemDictionary) {
        // 调用系统字典服务层接口保存系统字典信息
        systemDictionaryService.save(systemDictionary);
        return ApiResponse.success();
    }

    /**
     * 修改系统字典
     *
     * @param systemDictionary 系统字典实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改系统字典", description = "修改系统字典信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody SystemDictionary systemDictionary) {
        // 调用系统字典服务层接口修改系统字典信息
        systemDictionaryService.updateById(systemDictionary);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除系统字典
     *
     * @param id 系统字典主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除系统字典", description = "根据Id删除系统字典信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "系统字典Id") @PathVariable("id") Long id) {
        // 调用系统字典服务层接口根据Id删除系统字典信息
        systemDictionaryService.removeById(id);
        return ApiResponse.success();
    }
}