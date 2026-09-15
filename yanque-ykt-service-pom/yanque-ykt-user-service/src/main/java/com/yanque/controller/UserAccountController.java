package com.yanque.controller;

import java.util.List;

import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.common.UserAccount;
import com.yanque.service.IUserAccountService;
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
 * 会员账户信息控制层接口
 *
 * @author cr
 */
@Tag(name = "会员账户信息管理", description = "会员账户信息接口")
@RestController
@RequestMapping("/user/account")
public class UserAccountController {

    // 注入会员账户信息服务层接口实现类
    @Resource
    private IUserAccountService userAccountService;

    /**
     * 查询会员账户信息列表
     *
     * @return 全局通用返回结果(会员账户信息所有数据})
     */
    @Operation(summary = "查询会员账户信息列表", description = "查询所有会员账户信息信息列表")
    @GetMapping("/list" )
    public ApiResponse<List<UserAccount>> list() {
        // 调用会员账户信息服务层接口查询所有会员账户信息信息
        List<UserAccount> list = userAccountService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询会员账户信息
     *
     * @param id 会员账户信息主键Id
     * @return 全局通用返回结果(会员账户信息实体数据)
     */
    @Operation(summary = "根据Id查询会员账户信息", description = "根据主键Id查询会员账户信息详细信息")
    @GetMapping("/{id}" )
    public ApiResponse<UserAccount> getById(@Parameter(description = "会员账户信息Id") @PathVariable("id") Long id) {
        // 调用会员账户信息服务层接口根据Id查询会员账户信息信息
        UserAccount entity = userAccountService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增会员账户信息
     *
     * @param userAccount 会员账户信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增会员账户信息", description = "新增会员账户信息信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody UserAccount userAccount) {
        // 调用会员账户信息服务层接口保存会员账户信息信息
        userAccountService.save(userAccount);
        return ApiResponse.success();
    }

    /**
     * 修改会员账户信息
     *
     * @param userAccount 会员账户信息实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改会员账户信息", description = "修改会员账户信息信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody UserAccount userAccount) {
        // 调用会员账户信息服务层接口修改会员账户信息信息
        userAccountService.updateById(userAccount);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除会员账户信息
     *
     * @param id 会员账户信息主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除会员账户信息", description = "根据Id删除会员账户信息信息")
    @DeleteMapping("/{id}" )
    public ApiResponse<Void> delete(@Parameter(description = "会员账户信息Id") @PathVariable("id") Long id) {
        // 调用会员账户信息服务层接口根据Id删除会员账户信息信息
        userAccountService.removeById(id);
        return ApiResponse.success();
    }
}