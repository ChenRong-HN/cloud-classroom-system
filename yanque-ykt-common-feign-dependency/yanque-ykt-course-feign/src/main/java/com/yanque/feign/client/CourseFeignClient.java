package com.yanque.feign.client;

import com.yanque.common.vo.ApiResponse;
import com.yanque.entity.MediaFile;
import com.yanque.entity.vo.CourseOrderConfirmRespVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * course服务login的feign客户端
 *
 * @author cr
 */
@FeignClient(name = "yanque-ykt-course-service") // 使用feign客户端发送请求时，去nacos注册中心拉取yanque-ykt-course-service的服务实例
public interface CourseFeignClient {

    /**
     * 确认订单查询课程信息
     *
     * @param courseIds 课程id
     * @return 全局通用返回结果
     */
    @Operation(summary = "查询课程信息", description = "根据Id查询课程信息")
    @GetMapping("/course/course/info/{courseIds}") // 当路径参数是通过“,”分割，则可以通过集合来自动接收（其他符号不行）
    public ApiResponse<CourseOrderConfirmRespVo> orderConfirm(@PathVariable List<Long> courseIds);
}
