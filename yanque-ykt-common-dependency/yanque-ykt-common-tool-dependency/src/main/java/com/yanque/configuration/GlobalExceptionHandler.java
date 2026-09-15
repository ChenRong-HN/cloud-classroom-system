package com.yanque.configuration;

import com.yanque.common.vo.ApiResponse;
import com.yanque.exp.BusinessException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

/**
 * 自定义全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleException(BusinessException e){
        return ApiResponse.error(e.getBusinessErrorType().getCode(),e.getBusinessErrorType().getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception e){
        return ApiResponse.error("当前系统繁忙，请稍后重试");
    }

    /**
     * 处理HibernateValidator参数校验异常,需要将出现错误的参数的原因返回给前端
     * MethodArgumentNotValidException异常中除了继承了异常父类之外,内部还维护了异常的错误信息
     * 📌 MethodArgumentNotValidException出现的前提:当Java对象中的字段参数不符合校验规则,则默认抛出此异常
     *
     * @param e 异常对象
     * @return 表示错误统一返回结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> methodArgumentNotValidExceptionHandlerMethod(MethodArgumentNotValidException e) {
        log.error("全局异常处理器捕获到参数验证异常,异常类型 {} , 异常信息 {}", e.getClass(), e.getMessage());
        // 字符串拼接对象,可以用于拼接字符串
        StringBuilder stringBuilder = new StringBuilder("参数校验错误,错误原因:");
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            stringBuilder.append("[").append(fieldError.getDefaultMessage()).append("]");
        });
        // 将错误原因转换为字符串后后响应
        return ApiResponse.error(504, stringBuilder.toString());
    }

    /**
     * 统一异常处理方法,当程序中出现了任何未处理的MHandlerMethodValidationException异常,默认按照此方法进行兜底处理,默认将出现的异常对象作为实际参数传递给方法的形式参数
     * 📌 HandlerMethodValidationException出现的前提:当Web层方法是普通参数进行校验并且类上未标记@Validated,校验有问题则默认抛出此异常
     *
     * @param e 异常对象
     * @return 表示错误统一返回结果
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ApiResponse<Void> handlerMethodValidationExceptionHandler(HandlerMethodValidationException e) {
        log.error("全局异常处理器捕获到参数验证异常,异常类型 {} , 异常信息 {}", e.getClass(), e.getMessage());
        StringBuilder stringBuilder = new StringBuilder("参数校验错误,错误信息:");
        // 封装响应错误信息
        e.getAllValidationResults().forEach(result ->
                result.getResolvableErrors().forEach(error ->
                        stringBuilder.append("[").append(error.getDefaultMessage()).append("]")
                )
        );
        return ApiResponse.error(504, stringBuilder.toString());
    }

    /**
     * 统一异常处理方法,当程序中出现了任何未处理的ConstraintViolationException异常,默认按照此方法进行兜底处理,默认将出现的异常对象作为实际参数传递给方法的形式参数
     * 📌 ConstraintViolationException出现的前提:当Web层方法是普通参数进行校验并且类上标记@Validated,校验有问题则默认抛出此异常
     *
     * @param e 异常对象
     * @return 表示错误统一返回结果
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<Void> constraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error("全局异常处理器捕获到参数验证异常,异常类型 {} , 异常信息 {}", e.getClass(), e.getMessage());
        StringBuilder stringBuilder = new StringBuilder("参数校验错误,错误信息:");
        // 封装响应错误信息
        e.getConstraintViolations().forEach(violation ->
                stringBuilder.append("[").append(violation.getMessage()).append("]")
        );
        return ApiResponse.error(504, stringBuilder.toString());
    }
}
