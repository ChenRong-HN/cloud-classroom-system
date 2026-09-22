package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.Config;
import com.yanque.service.IConfigService;
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
 * 参数配置控制层接口
 *
 * @author cr
 */
@Tag(name = "参数配置管理", description = "参数配置接口")
@RestController
@RequestMapping("/system/config")
public class ConfigController {

    // 注入参数配置服务层接口实现类
    @Resource
    private IConfigService configService;

    /**
     * 查询参数配置列表
     *
     * @return 全局通用返回结果(参数配置所有数据})
     */
    @Operation(summary = "查询参数配置列表", description = "查询所有参数配置信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<Config>> list() {
        // 调用参数配置服务层接口查询所有参数配置信息
        List<Config> list = configService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询参数配置
     *
     * @param id 参数配置主键Id
     * @return 全局通用返回结果(参数配置实体数据)
     */
    @Operation(summary = "根据Id查询参数配置", description = "根据主键Id查询参数配置详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<Config> getById(@Parameter(description = "参数配置Id") @PathVariable("id") Long id) {
        // 调用参数配置服务层接口根据Id查询参数配置信息
        Config entity = configService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增参数配置
     *
     * @param config 参数配置实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增参数配置", description = "新增参数配置信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody Config config) {
        // 调用参数配置服务层接口保存参数配置信息
        configService.save(config);
        return ApiResponse.success();
    }

    /**
     * 修改参数配置
     *
     * @param config 参数配置实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改参数配置", description = "修改参数配置信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody Config config) {
        // 调用参数配置服务层接口修改参数配置信息
        configService.updateById(config);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除参数配置
     *
     * @param id 参数配置主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除参数配置", description = "根据Id删除参数配置信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "参数配置Id") @PathVariable("id") Long id) {
        // 调用参数配置服务层接口根据Id删除参数配置信息
        configService.removeById(id);
        return ApiResponse.success();
    }
}