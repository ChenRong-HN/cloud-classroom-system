package com.yanque.exp;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义业务异常
 */
@Getter
public class BusinessException extends RuntimeException {
    // 异常类型
    private final BusinessErrorType businessErrorType;

    /**
     * 自定义业务异常有参构造,当基于此类型构建异常对象时必须传递异常类型参数
     */
    public BusinessException(BusinessErrorType businessErrorType) {
        super(businessErrorType.getMessage());
        this.businessErrorType = businessErrorType;
    }
}
