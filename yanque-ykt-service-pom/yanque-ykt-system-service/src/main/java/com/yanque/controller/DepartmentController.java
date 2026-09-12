package com.yanque.controller;

import java.util.List;

import com.yanque.entity.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.Department;
import com.yanque.service.IDepartmentService;
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
 * 部门信息控制层接口
 *
 * @author cr
 */
@Tag(name = "部门信息管理", description = "部门信息接口")
@RestController
@RequestMapping("/yanque/department")
public class DepartmentController {

    // 注入部门信息服务层接口实现类
    @Resource
    private IDepartmentService departmentService;

    /**
     * 查询部门信息列表
     *
     * @return 全局通用返回结果(部门信息所有数据})
     */
    @Operation(summary = "查询部门信息列表", description = "查询所有部门信息信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<Department>> list() {
        // 调用部门信息服务层接口查询所有部门信息信息
        List<Department> list = departmentService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询部门信息
     *
     * @param id 部门信息主键Id
     * @return 全局通用返回结果(部门信息实体数据)
     */
    @Operation(summary = "根据Id查询部门信息", description = "根据主键Id查询部门信息详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<Department> getById(@Parameter(description = "部门信息Id") @PathVariable("id") Long id) {
        // 调用部门信息服务层接口根据Id查询部门信息信息
        Department entity = departmentService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增部门信息
     *
     * @param department 部门信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增部门信息", description = "新增部门信息信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody Department department) {
        // 调用部门信息服务层接口保存部门信息信息
        departmentService.save(department);
        return ApiResponse.success();
    }

    /**
     * 修改部门信息
     *
     * @param department 部门信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改部门信息", description = "修改部门信息信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody Department department) {
        // 调用部门信息服务层接口修改部门信息信息
        departmentService.updateById(department);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除部门信息
     *
     * @param id 部门信息主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除部门信息", description = "根据Id删除部门信息信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "部门信息Id") @PathVariable("id") Long id) {
        // 调用部门信息服务层接口根据Id删除部门信息信息
        departmentService.removeById(id);
        return ApiResponse.success();
    }
}