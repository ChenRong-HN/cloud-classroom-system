package com.yanque.feign.client;

import com.yanque.common.vo.ApiResponse;
import com.yanque.entity.Login;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * uaa服务login的feign客户端
 *
 * @author cr
 */
@FeignClient(name = "yanque-ykt-uaa-service") // 使用feign客户端发送请求时，去nacos注册中心拉取yanque-ykt-uaa-service的服务实例
public interface UaaLoginFeignClient {

    /**
     * 新增登录数据
     *
     * @param login 登录数据实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增登录数据", description = "新增登录数据信息")
    @PostMapping("/uaa/login")
     ApiResponse<Long> save(@RequestBody Login login);
}
