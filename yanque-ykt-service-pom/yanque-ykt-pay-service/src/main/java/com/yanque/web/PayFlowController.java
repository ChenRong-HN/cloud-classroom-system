package com.yanque.web;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.PayFlow;
import com.yanque.service.IPayFlowService;
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
 * 支付流水控制层接口
 *
 * @author cr
 */
@Tag(name = "支付流水管理", description = "支付流水接口")
@RestController
@RequestMapping("/payFlow")
public class PayFlowController {

    // 注入支付流水服务层接口实现类
    @Resource
    private IPayFlowService payFlowService;

    /**
     * 查询支付流水列表
     *
     * @return 全局通用返回结果(支付流水所有数据})
     */
    @Operation(summary = "查询支付流水列表", description = "查询所有支付流水信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<PayFlow>> list() {
        // 调用支付流水服务层接口查询所有支付流水信息
        List<PayFlow> list = payFlowService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询支付流水
     *
     * @param id 支付流水主键Id
     * @return 全局通用返回结果(支付流水实体数据)
     */
    @Operation(summary = "根据Id查询支付流水", description = "根据主键Id查询支付流水详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<PayFlow> getById(@Parameter(description = "支付流水Id") @PathVariable("id") Long id) {
        // 调用支付流水服务层接口根据Id查询支付流水信息
        PayFlow entity = payFlowService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增支付流水
     *
     * @param payFlow 支付流水实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增支付流水", description = "新增支付流水信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody PayFlow payFlow) {
        // 调用支付流水服务层接口保存支付流水信息
        payFlowService.save(payFlow);
        return ApiResponse.success();
    }

    /**
     * 修改支付流水
     *
     * @param payFlow 支付流水实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改支付流水", description = "修改支付流水信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody PayFlow payFlow) {
        // 调用支付流水服务层接口修改支付流水信息
        payFlowService.updateById(payFlow);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除支付流水
     *
     * @param id 支付流水主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除支付流水", description = "根据Id删除支付流水信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "支付流水Id") @PathVariable("id") Long id) {
        // 调用支付流水服务层接口根据Id删除支付流水信息
        payFlowService.removeById(id);
        return ApiResponse.success();
    }
}