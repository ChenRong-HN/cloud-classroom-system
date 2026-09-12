package com.yanque.controller;

import java.util.List;

import com.yanque.entity.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.OperationLog;
import com.yanque.service.IOperationLogService;
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
 * 操作日志记录控制层接口
 *
 * @author cr
 */
@Tag(name = "操作日志记录管理", description = "操作日志记录接口")
@RestController
@RequestMapping("/yanque/log")
public class OperationLogController {

    // 注入操作日志记录服务层接口实现类
    @Resource
    private IOperationLogService operationLogService;

    /**
     * 查询操作日志记录列表
     *
     * @return 全局通用返回结果(操作日志记录所有数据})
     */
    @Operation(summary = "查询操作日志记录列表", description = "查询所有操作日志记录信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<OperationLog>> list() {
        // 调用操作日志记录服务层接口查询所有操作日志记录信息
        List<OperationLog> list = operationLogService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询操作日志记录
     *
     * @param id 操作日志记录主键Id
     * @return 全局通用返回结果(操作日志记录实体数据)
     */
    @Operation(summary = "根据Id查询操作日志记录", description = "根据主键Id查询操作日志记录详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<OperationLog> getById(@Parameter(description = "操作日志记录Id") @PathVariable("id") Long id) {
        // 调用操作日志记录服务层接口根据Id查询操作日志记录信息
        OperationLog entity = operationLogService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增操作日志记录
     *
     * @param operationLog 操作日志记录实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增操作日志记录", description = "新增操作日志记录信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody OperationLog operationLog) {
        // 调用操作日志记录服务层接口保存操作日志记录信息
        operationLogService.save(operationLog);
        return ApiResponse.success();
    }

    /**
     * 修改操作日志记录
     *
     * @param operationLog 操作日志记录实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改操作日志记录", description = "修改操作日志记录信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody OperationLog operationLog) {
        // 调用操作日志记录服务层接口修改操作日志记录信息
        operationLogService.updateById(operationLog);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除操作日志记录
     *
     * @param id 操作日志记录主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除操作日志记录", description = "根据Id删除操作日志记录信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "操作日志记录Id") @PathVariable("id") Long id) {
        // 调用操作日志记录服务层接口根据Id删除操作日志记录信息
        operationLogService.removeById(id);
        return ApiResponse.success();
    }
}