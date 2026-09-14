package com.yanque.controller;

import java.util.List;

import com.yanque.entity.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.LoginRole;
import com.yanque.service.ILoginRoleService;
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
 * 用户角色中间控制层接口
 *
 * @author cr
 */
@Tag(name = "用户角色中间管理", description = "用户角色中间接口")
@RestController
@RequestMapping("/uaa/role")
public class LoginRoleController {

    // 注入用户角色中间服务层接口实现类
    @Resource
    private ILoginRoleService loginRoleService;

    /**
     * 查询用户角色中间列表
     *
     * @return 全局通用返回结果(用户角色中间所有数据})
     */
    @Operation(summary = "查询用户角色中间列表", description = "查询所有用户角色中间信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<LoginRole>> list() {
        // 调用用户角色中间服务层接口查询所有用户角色中间信息
        List<LoginRole> list = loginRoleService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询用户角色中间
     *
     * @param id 用户角色中间主键Id
     * @return 全局通用返回结果(用户角色中间实体数据)
     */
    @Operation(summary = "根据Id查询用户角色中间", description = "根据主键Id查询用户角色中间详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<LoginRole> getById(@Parameter(description = "用户角色中间Id") @PathVariable("id") Long id) {
        // 调用用户角色中间服务层接口根据Id查询用户角色中间信息
        LoginRole entity = loginRoleService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增用户角色中间
     *
     * @param loginRole 用户角色中间实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增用户角色中间", description = "新增用户角色中间信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody LoginRole loginRole) {
        // 调用用户角色中间服务层接口保存用户角色中间信息
        loginRoleService.save(loginRole);
        return ApiResponse.success();
    }

    /**
     * 修改用户角色中间
     *
     * @param loginRole 用户角色中间实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改用户角色中间", description = "修改用户角色中间信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody LoginRole loginRole) {
        // 调用用户角色中间服务层接口修改用户角色中间信息
        loginRoleService.updateById(loginRole);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除用户角色中间
     *
     * @param id 用户角色中间主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除用户角色中间", description = "根据Id删除用户角色中间信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "用户角色中间Id") @PathVariable("id") Long id) {
        // 调用用户角色中间服务层接口根据Id删除用户角色中间信息
        loginRoleService.removeById(id);
        return ApiResponse.success();
    }
}