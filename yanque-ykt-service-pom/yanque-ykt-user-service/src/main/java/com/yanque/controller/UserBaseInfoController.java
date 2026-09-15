package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.UserBaseInfo;
import com.yanque.service.IUserBaseInfoService;
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
 * 会员基本信息控制层接口
 *
 * @author cr
 */
@Tag(name = "会员基本信息管理", description = "会员基本信息接口")
@RestController
@RequestMapping("/user/baseInfo")
public class UserBaseInfoController {

    // 注入会员基本信息服务层接口实现类
    @Resource
    private IUserBaseInfoService userBaseInfoService;

    /**
     * 查询会员基本信息列表
     *
     * @return 全局通用返回结果(会员基本信息所有数据})
     */
    @Operation(summary = "查询会员基本信息列表", description = "查询所有会员基本信息信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<UserBaseInfo>> list() {
        // 调用会员基本信息服务层接口查询所有会员基本信息信息
        List<UserBaseInfo> list = userBaseInfoService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询会员基本信息
     *
     * @param id 会员基本信息主键Id
     * @return 全局通用返回结果(会员基本信息实体数据)
     */
    @Operation(summary = "根据Id查询会员基本信息", description = "根据主键Id查询会员基本信息详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<UserBaseInfo> getById(@Parameter(description = "会员基本信息Id") @PathVariable("id") Long id) {
        // 调用会员基本信息服务层接口根据Id查询会员基本信息信息
        UserBaseInfo entity = userBaseInfoService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增会员基本信息
     *
     * @param userBaseInfo 会员基本信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增会员基本信息", description = "新增会员基本信息信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody UserBaseInfo userBaseInfo) {
        // 调用会员基本信息服务层接口保存会员基本信息信息
        userBaseInfoService.save(userBaseInfo);
        return ApiResponse.success();
    }

    /**
     * 修改会员基本信息
     *
     * @param userBaseInfo 会员基本信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改会员基本信息", description = "修改会员基本信息信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody UserBaseInfo userBaseInfo) {
        // 调用会员基本信息服务层接口修改会员基本信息信息
        userBaseInfoService.updateById(userBaseInfo);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除会员基本信息
     *
     * @param id 会员基本信息主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除会员基本信息", description = "根据Id删除会员基本信息信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "会员基本信息Id") @PathVariable("id") Long id) {
        // 调用会员基本信息服务层接口根据Id删除会员基本信息信息
        userBaseInfoService.removeById(id);
        return ApiResponse.success();
    }
}