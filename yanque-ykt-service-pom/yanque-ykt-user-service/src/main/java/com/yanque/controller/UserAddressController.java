package com.yanque.controller;

import java.util.List;

import com.yanque.entity.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.UserAddress;
import com.yanque.service.IUserAddressService;
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
 * 会员收货地址控制层接口
 *
 * @author cr
 */
@Tag(name = "会员收货地址管理", description = "会员收货地址接口")
@RestController
@RequestMapping("/user/address")
public class UserAddressController {

    // 注入会员收货地址服务层接口实现类
    @Resource
    private IUserAddressService userAddressService;

    /**
     * 查询会员收货地址列表
     *
     * @return 全局通用返回结果(会员收货地址所有数据})
     */
    @Operation(summary = "查询会员收货地址列表", description = "查询所有会员收货地址信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<UserAddress>> list() {
        // 调用会员收货地址服务层接口查询所有会员收货地址信息
        List<UserAddress> list = userAddressService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询会员收货地址
     *
     * @param id 会员收货地址主键Id
     * @return 全局通用返回结果(会员收货地址实体数据)
     */
    @Operation(summary = "根据Id查询会员收货地址", description = "根据主键Id查询会员收货地址详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<UserAddress> getById(@Parameter(description = "会员收货地址Id") @PathVariable("id") Long id) {
        // 调用会员收货地址服务层接口根据Id查询会员收货地址信息
        UserAddress entity = userAddressService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增会员收货地址
     *
     * @param userAddress 会员收货地址实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增会员收货地址", description = "新增会员收货地址信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody UserAddress userAddress) {
        // 调用会员收货地址服务层接口保存会员收货地址信息
        userAddressService.save(userAddress);
        return ApiResponse.success();
    }

    /**
     * 修改会员收货地址
     *
     * @param userAddress 会员收货地址实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改会员收货地址", description = "修改会员收货地址信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody UserAddress userAddress) {
        // 调用会员收货地址服务层接口修改会员收货地址信息
        userAddressService.updateById(userAddress);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除会员收货地址
     *
     * @param id 会员收货地址主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除会员收货地址", description = "根据Id删除会员收货地址信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "会员收货地址Id") @PathVariable("id") Long id) {
        // 调用会员收货地址服务层接口根据Id删除会员收货地址信息
        userAddressService.removeById(id);
        return ApiResponse.success();
    }
}