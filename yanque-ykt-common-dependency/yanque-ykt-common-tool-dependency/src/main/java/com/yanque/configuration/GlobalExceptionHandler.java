package com.yanque.configuration;

import com.yanque.entity.vo.ApiResponse;
import com.yanque.exp.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleException(BusinessException e){
        return ApiResponse.error(e.getBusinessErrorType().getCode(),e.getBusinessErrorType().getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception e){
        return ApiResponse.error("当前系统繁忙，请稍后重试");
    }

}
