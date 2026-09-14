package com.yanque.controller;

import java.util.List;

import com.yanque.entity.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.UserRealInfo;
import com.yanque.service.IUserRealInfoService;
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
 * 会员实名资料控制层接口
 *
 * @author cr
 */
@Tag(name = "会员实名资料管理", description = "会员实名资料接口")
@RestController
@RequestMapping("/user/info")
public class UserRealInfoController {

    // 注入会员实名资料服务层接口实现类
    @Resource
    private IUserRealInfoService userRealInfoService;

    /**
     * 查询会员实名资料列表
     *
     * @return 全局通用返回结果(会员实名资料所有数据})
     */
    @Operation(summary = "查询会员实名资料列表", description = "查询所有会员实名资料信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<UserRealInfo>> list() {
        // 调用会员实名资料服务层接口查询所有会员实名资料信息
        List<UserRealInfo> list = userRealInfoService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询会员实名资料
     *
     * @param id 会员实名资料主键Id
     * @return 全局通用返回结果(会员实名资料实体数据)
     */
    @Operation(summary = "根据Id查询会员实名资料", description = "根据主键Id查询会员实名资料详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<UserRealInfo> getById(@Parameter(description = "会员实名资料Id") @PathVariable("id") Long id) {
        // 调用会员实名资料服务层接口根据Id查询会员实名资料信息
        UserRealInfo entity = userRealInfoService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增会员实名资料
     *
     * @param userRealInfo 会员实名资料实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增会员实名资料", description = "新增会员实名资料信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody UserRealInfo userRealInfo) {
        // 调用会员实名资料服务层接口保存会员实名资料信息
        userRealInfoService.save(userRealInfo);
        return ApiResponse.success();
    }

    /**
     * 修改会员实名资料
     *
     * @param userRealInfo 会员实名资料实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改会员实名资料", description = "修改会员实名资料信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody UserRealInfo userRealInfo) {
        // 调用会员实名资料服务层接口修改会员实名资料信息
        userRealInfoService.updateById(userRealInfo);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除会员实名资料
     *
     * @param id 会员实名资料主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除会员实名资料", description = "根据Id删除会员实名资料信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "会员实名资料Id") @PathVariable("id") Long id) {
        // 调用会员实名资料服务层接口根据Id删除会员实名资料信息
        userRealInfoService.removeById(id);
        return ApiResponse.success();
    }
}