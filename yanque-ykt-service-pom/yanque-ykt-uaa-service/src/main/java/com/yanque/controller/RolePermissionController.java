package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.RolePermission;
import com.yanque.service.IRolePermissionService;
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
 * 角色权限控制层接口
 *
 * @author cr
 */
@Tag(name = "角色权限管理", description = "角色权限接口")
@RestController
@RequestMapping("/uaa/rolePermission")
public class RolePermissionController {

    // 注入角色权限服务层接口实现类
    @Resource
    private IRolePermissionService rolePermissionService;

    /**
     * 查询角色权限列表
     *
     * @return 全局通用返回结果(角色权限所有数据})
     */
    @Operation(summary = "查询角色权限列表", description = "查询所有角色权限信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<RolePermission>> list() {
        // 调用角色权限服务层接口查询所有角色权限信息
        List<RolePermission> list = rolePermissionService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询角色权限
     *
     * @param id 角色权限主键Id
     * @return 全局通用返回结果(角色权限实体数据)
     */
    @Operation(summary = "根据Id查询角色权限", description = "根据主键Id查询角色权限详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<RolePermission> getById(@Parameter(description = "角色权限Id") @PathVariable("id") Long id) {
        // 调用角色权限服务层接口根据Id查询角色权限信息
        RolePermission entity = rolePermissionService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增角色权限
     *
     * @param rolePermission 角色权限实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增角色权限", description = "新增角色权限信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody RolePermission rolePermission) {
        // 调用角色权限服务层接口保存角色权限信息
        rolePermissionService.save(rolePermission);
        return ApiResponse.success();
    }

    /**
     * 修改角色权限
     *
     * @param rolePermission 角色权限实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改角色权限", description = "修改角色权限信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody RolePermission rolePermission) {
        // 调用角色权限服务层接口修改角色权限信息
        rolePermissionService.updateById(rolePermission);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除角色权限
     *
     * @param id 角色权限主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除角色权限", description = "根据Id删除角色权限信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "角色权限Id") @PathVariable("id") Long id) {
        // 调用角色权限服务层接口根据Id删除角色权限信息
        rolePermissionService.removeById(id);
        return ApiResponse.success();
    }
}