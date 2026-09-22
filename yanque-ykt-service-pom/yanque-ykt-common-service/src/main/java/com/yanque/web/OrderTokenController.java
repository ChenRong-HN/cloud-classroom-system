package com.yanque.web;

import cn.hutool.core.lang.UUID;
import com.yanque.common.constant.RedisConstant;
import com.yanque.common.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 订单确认令牌控制层
 *
 * @author cr
 */
@RestController
@Slf4j
@RequestMapping("/common/token")
public class OrderTokenController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 创建订单确认令牌
     *
     * @param courseIds 课程id集合
     * @return 全局通用返回结果
     */
    @GetMapping("/createToken/{courseIds}")
    @Operation(summary = "创建订单确认令牌", description = "创建订单确认令牌")
    public ApiResponse<String> createToken(@PathVariable List<Long> courseIds) {
        // 生成redis的令牌key
        String tokenUUID = UUID.fastUUID().toString();
        // 模拟用户id 5L
        String redisTokenKey = RedisConstant.ORDER_CONFIRM_TOKEN_KEY.concat("5").concat(":").concat(tokenUUID);

        // 往redis中存入key和value，设置默认过期时间30分钟
        redisTemplate.opsForValue().set(redisTokenKey, courseIds, RedisConstant.ORDER_CONFIRM_DEFAULT_EXPIRE_TIME, TimeUnit.MINUTES);

        // 📌 只响应UUID部分
        return ApiResponse.success(tokenUUID);
    }
}
