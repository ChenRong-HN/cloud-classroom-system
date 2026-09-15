package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.Role;
import com.yanque.service.IRoleService;
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
 * 角色控制层接口
 *
 * @author cr
 */
@Tag(name = "角色管理", description = "角色接口")
@RestController
@RequestMapping("/uaa/role")
public class RoleController {

    // 注入角色服务层接口实现类
    @Resource
    private IRoleService roleService;

    /**
     * 查询角色列表
     *
     * @return 全局通用返回结果(角色所有数据})
     */
    @Operation(summary = "查询角色列表", description = "查询所有角色信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<Role>> list() {
        // 调用角色服务层接口查询所有角色信息
        List<Role> list = roleService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询角色
     *
     * @param id 角色主键Id
     * @return 全局通用返回结果(角色实体数据)
     */
    @Operation(summary = "根据Id查询角色", description = "根据主键Id查询角色详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<Role> getById(@Parameter(description = "角色Id") @PathVariable("id") Long id) {
        // 调用角色服务层接口根据Id查询角色信息
        Role entity = roleService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增角色
     *
     * @param role 角色实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增角色", description = "新增角色信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody Role role) {
        // 调用角色服务层接口保存角色信息
        roleService.save(role);
        return ApiResponse.success();
    }

    /**
     * 修改角色
     *
     * @param role 角色实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改角色", description = "修改角色信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody Role role) {
        // 调用角色服务层接口修改角色信息
        roleService.updateById(role);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除角色
     *
     * @param id 角色主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除角色", description = "根据Id删除角色信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "角色Id") @PathVariable("id") Long id) {
        // 调用角色服务层接口根据Id删除角色信息
        roleService.removeById(id);
        return ApiResponse.success();
    }
}