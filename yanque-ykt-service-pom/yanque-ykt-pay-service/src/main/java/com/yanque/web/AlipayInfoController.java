package com.yanque.web;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.AlipayInfo;
import com.yanque.service.IAlipayInfoService;
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
 * 支付宝参数信息控制层接口
 *
 * @author cr
 */
@Tag(name = "支付宝参数信息管理", description = "支付宝参数信息接口")
@RestController
@RequestMapping("/pay/aliPayInfo")
public class AlipayInfoController {

    // 注入支付宝参数信息服务层接口实现类
    @Resource
    private IAlipayInfoService alipayInfoService;

    /**
     * 查询支付宝参数信息列表
     *
     * @return 全局通用返回结果(支付宝参数信息所有数据})
     */
    @Operation(summary = "查询支付宝参数信息列表", description = "查询所有支付宝参数信息信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<AlipayInfo>> list() {
        // 调用支付宝参数信息服务层接口查询所有支付宝参数信息信息
        List<AlipayInfo> list = alipayInfoService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询支付宝参数信息
     *
     * @param id 支付宝参数信息主键Id
     * @return 全局通用返回结果(支付宝参数信息实体数据)
     */
    @Operation(summary = "根据Id查询支付宝参数信息", description = "根据主键Id查询支付宝参数信息详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<AlipayInfo> getById(@Parameter(description = "支付宝参数信息Id") @PathVariable("id") Long id) {
        // 调用支付宝参数信息服务层接口根据Id查询支付宝参数信息信息
        AlipayInfo entity = alipayInfoService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增支付宝参数信息
     *
     * @param alipayInfo 支付宝参数信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增支付宝参数信息", description = "新增支付宝参数信息信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody AlipayInfo alipayInfo) {
        // 调用支付宝参数信息服务层接口保存支付宝参数信息信息
        alipayInfoService.save(alipayInfo);
        return ApiResponse.success();
    }

    /**
     * 修改支付宝参数信息
     *
     * @param alipayInfo 支付宝参数信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改支付宝参数信息", description = "修改支付宝参数信息信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody AlipayInfo alipayInfo) {
        // 调用支付宝参数信息服务层接口修改支付宝参数信息信息
        alipayInfoService.updateById(alipayInfo);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除支付宝参数信息
     *
     * @param id 支付宝参数信息主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除支付宝参数信息", description = "根据Id删除支付宝参数信息信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "支付宝参数信息Id") @PathVariable("id") Long id) {
        // 调用支付宝参数信息服务层接口根据Id删除支付宝参数信息信息
        alipayInfoService.removeById(id);
        return ApiResponse.success();
    }
}