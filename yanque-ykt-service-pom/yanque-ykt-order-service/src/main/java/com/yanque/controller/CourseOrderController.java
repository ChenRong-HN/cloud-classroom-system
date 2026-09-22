package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import com.yanque.entity.vo.PlaceOrderReqVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.CourseOrder;
import com.yanque.service.ICourseOrderService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制层接口
 *
 * @author x1angwan
 */
@Tag(name = "订单管理", description = "订单接口")
@RestController
@RequestMapping("/order/courseOrder")
public class CourseOrderController {

    // 注入订单服务层接口实现类
    @Resource
    private ICourseOrderService courseOrderService;

    /**
     * 查询订单列表
     *
     * @return 全局通用返回结果(订单所有数据)
     */
    @Operation(summary = "查询订单列表", description = "查询所有订单信息列表")
    @GetMapping("/list")
    public ApiResponse<List<CourseOrder>> list() {
        // 调用订单服务层接口查询所有订单信息
        List<CourseOrder> list = courseOrderService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询订单
     *
     * @param id 订单主键Id
     * @return 全局通用返回结果(订单实体数据)
     */
    @Operation(summary = "根据Id查询订单", description = "根据主键Id查询订单详细信息")
    @GetMapping("/{id}")
    public ApiResponse<CourseOrder> getById(@Parameter(description = "订单Id") @PathVariable("id") Long id) {
        // 调用订单服务层接口根据Id查询订单信息
        CourseOrder entity = courseOrderService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增订单
     *
     * @param courseOrder 订单实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增订单", description = "新增订单信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody CourseOrder courseOrder) {
        // 调用订单服务层接口保存订单信息
        courseOrderService.save(courseOrder);
        return ApiResponse.success();
    }

    /**
     * 修改订单
     *
     * @param courseOrder 订单实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改订单", description = "修改订单信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody CourseOrder courseOrder) {
        // 调用订单服务层接口修改订单信息
        courseOrderService.updateById(courseOrder);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除订单
     *
     * @param id 订单主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除订单", description = "根据Id删除订单信息")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@Parameter(description = "订单Id") @PathVariable("id") Long id) {
        // 调用订单服务层接口根据Id删除订单信息
        courseOrderService.removeById(id);
        return ApiResponse.success();
    }

    /**
     * 保存订单
     *
     * @param placeOrderReqVo 保存订单请求参数
     * @return 全局通用返回结果
     */
    @Operation(summary = "保存订单", description = "保存订单")
    @PostMapping("/placeOrder")
    public ApiResponse<String> placeOrder(@Valid @RequestBody PlaceOrderReqVo placeOrderReqVo) {
        String orderNo = courseOrderService.placeOrder(placeOrderReqVo);
        return ApiResponse.success(orderNo);
    }
}