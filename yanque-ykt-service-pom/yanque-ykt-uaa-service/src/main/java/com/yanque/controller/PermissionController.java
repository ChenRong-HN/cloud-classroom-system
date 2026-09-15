package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.Permission;
import com.yanque.service.IPermissionService;
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
 * 权限控制层接口
 *
 * @author cr
 */
@Tag(name = "权限管理", description = "权限接口")
@RestController
@RequestMapping("/uaa/permission")
public class PermissionController {

    // 注入权限服务层接口实现类
    @Resource
    private IPermissionService permissionService;

    /**
     * 查询权限列表
     *
     * @return 全局通用返回结果(权限所有数据})
     */
    @Operation(summary = "查询权限列表", description = "查询所有权限信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<Permission>> list() {
        // 调用权限服务层接口查询所有权限信息
        List<Permission> list = permissionService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询权限
     *
     * @param id 权限主键Id
     * @return 全局通用返回结果(权限实体数据)
     */
    @Operation(summary = "根据Id查询权限", description = "根据主键Id查询权限详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<Permission> getById(@Parameter(description = "权限Id") @PathVariable("id") Long id) {
        // 调用权限服务层接口根据Id查询权限信息
        Permission entity = permissionService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增权限
     *
     * @param permission 权限实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增权限", description = "新增权限信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody Permission permission) {
        // 调用权限服务层接口保存权限信息
        permissionService.save(permission);
        return ApiResponse.success();
    }

    /**
     * 修改权限
     *
     * @param permission 权限实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改权限", description = "修改权限信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody Permission permission) {
        // 调用权限服务层接口修改权限信息
        permissionService.updateById(permission);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除权限
     *
     * @param id 权限主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除权限", description = "根据Id删除权限信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "权限Id") @PathVariable("id") Long id) {
        // 调用权限服务层接口根据Id删除权限信息
        permissionService.removeById(id);
        return ApiResponse.success();
    }
}