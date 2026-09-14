package com.yanque.controller;

import java.util.List;

import com.yanque.entity.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.Login;
import com.yanque.service.ILoginService;
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
 * 登录数据控制层接口
 *
 * @author cr
 */
@Tag(name = "登录数据管理", description = "登录数据接口")
@RestController
@RequestMapping("/uaa/login")
public class LoginController {

    // 注入登录数据服务层接口实现类
    @Resource
    private ILoginService loginService;

    /**
     * 查询登录数据列表
     *
     * @return 全局通用返回结果(登录数据所有数据})
     */
    @Operation(summary = "查询登录数据列表", description = "查询所有登录数据信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<Login>> list() {
        // 调用登录数据服务层接口查询所有登录数据信息
        List<Login> list = loginService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询登录数据
     *
     * @param id 登录数据主键Id
     * @return 全局通用返回结果(登录数据实体数据)
     */
    @Operation(summary = "根据Id查询登录数据", description = "根据主键Id查询登录数据详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<Login> getById(@Parameter(description = "登录数据Id") @PathVariable("id") Long id) {
        // 调用登录数据服务层接口根据Id查询登录数据信息
        Login entity = loginService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增登录数据
     *
     * @param login 登录数据实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增登录数据", description = "新增登录数据信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody Login login) {
        // 调用登录数据服务层接口保存登录数据信息
        loginService.save(login);
        return ApiResponse.success();
    }

    /**
     * 修改登录数据
     *
     * @param login 登录数据实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改登录数据", description = "修改登录数据信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody Login login) {
        // 调用登录数据服务层接口修改登录数据信息
        loginService.updateById(login);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除登录数据
     *
     * @param id 登录数据主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除登录数据", description = "根据Id删除登录数据信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "登录数据Id") @PathVariable("id") Long id) {
        // 调用登录数据服务层接口根据Id删除登录数据信息
        loginService.removeById(id);
        return ApiResponse.success();
    }
}