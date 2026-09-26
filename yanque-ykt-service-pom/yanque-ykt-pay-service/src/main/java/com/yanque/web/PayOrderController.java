package com.yanque.web;

import com.yanque.common.vo.ApiResponse;
import com.yanque.entity.PayOrder;
import com.yanque.service.IPayOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付订单控制层接口
 *
 * @author cr
 */
@Tag(name = "支付订单管理", description = "支付订单接口")
@RestController
@RequestMapping("/pay/payOrder")
@Slf4j
public class PayOrderController {

    // 注入支付订单服务层接口实现类
    @Resource
    private IPayOrderService payOrderService;

    /**
     * 查询支付订单列表
     *
     * @return 全局通用返回结果(支付订单所有数据})
     */
    @Operation(summary = "查询支付订单列表", description = "查询所有支付订单信息列表")
    @GetMapping("/list")
    public ApiResponse<List<PayOrder>> list() {
        // 调用支付订单服务层接口查询所有支付订单信息
        List<PayOrder> list = payOrderService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询支付订单
     *
     * @param id 支付订单主键Id
     * @return 全局通用返回结果(支付订单实体数据)
     */
    @Operation(summary = "根据Id查询支付订单", description = "根据主键Id查询支付订单详细信息")
    @GetMapping("/{id}")
    public ApiResponse<PayOrder> getById(@Parameter(description = "支付订单Id") @PathVariable("id") Long id) {
        // 调用支付订单服务层接口根据Id查询支付订单信息
        PayOrder entity = payOrderService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增支付订单
     *
     * @param payOrder 支付订单实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增支付订单", description = "新增支付订单信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody PayOrder payOrder) {
        // 调用支付订单服务层接口保存支付订单信息
        payOrderService.save(payOrder);
        return ApiResponse.success();
    }

    /**
     * 修改支付订单
     *
     * @param payOrder 支付订单实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改支付订单", description = "修改支付订单信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody PayOrder payOrder) {
        // 调用支付订单服务层接口修改支付订单信息
        payOrderService.updateById(payOrder);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除支付订单
     *
     * @param id 支付订单主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除支付订单", description = "根据Id删除支付订单信息")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@Parameter(description = "支付订单Id") @PathVariable("id") Long id) {
        // 调用支付订单服务层接口根据Id删除支付订单信息
        payOrderService.removeById(id);
        return ApiResponse.success();
    }
}