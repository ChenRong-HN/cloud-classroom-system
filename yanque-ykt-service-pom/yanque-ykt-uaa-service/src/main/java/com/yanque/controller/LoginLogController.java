package com.yanque.controller;

import java.util.List;

import com.yanque.entity.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.LoginLog;
import com.yanque.service.ILoginLogService;
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
 * 登录记录控制层接口
 *
 * @author cr
 */
@Tag(name = "登录记录管理", description = "登录记录接口")
@RestController
@RequestMapping("/uaa/log")
public class LoginLogController {

    // 注入登录记录服务层接口实现类
    @Resource
    private ILoginLogService loginLogService;

    /**
     * 查询登录记录列表
     *
     * @return 全局通用返回结果(登录记录所有数据})
     */
    @Operation(summary = "查询登录记录列表", description = "查询所有登录记录信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<LoginLog>> list() {
        // 调用登录记录服务层接口查询所有登录记录信息
        List<LoginLog> list = loginLogService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询登录记录
     *
     * @param id 登录记录主键Id
     * @return 全局通用返回结果(登录记录实体数据)
     */
    @Operation(summary = "根据Id查询登录记录", description = "根据主键Id查询登录记录详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<LoginLog> getById(@Parameter(description = "登录记录Id") @PathVariable("id") Long id) {
        // 调用登录记录服务层接口根据Id查询登录记录信息
        LoginLog entity = loginLogService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增登录记录
     *
     * @param loginLog 登录记录实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增登录记录", description = "新增登录记录信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody LoginLog loginLog) {
        // 调用登录记录服务层接口保存登录记录信息
        loginLogService.save(loginLog);
        return ApiResponse.success();
    }

    /**
     * 修改登录记录
     *
     * @param loginLog 登录记录实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改登录记录", description = "修改登录记录信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody LoginLog loginLog) {
        // 调用登录记录服务层接口修改登录记录信息
        loginLogService.updateById(loginLog);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除登录记录
     *
     * @param id 登录记录主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除登录记录", description = "根据Id删除登录记录信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "登录记录Id") @PathVariable("id") Long id) {
        // 调用登录记录服务层接口根据Id删除登录记录信息
        loginLogService.removeById(id);
        return ApiResponse.success();
    }
}