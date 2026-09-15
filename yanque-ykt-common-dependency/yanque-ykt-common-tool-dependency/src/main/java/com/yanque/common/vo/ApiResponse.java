package com.yanque.common.vo;

import lombok.Data;

/**
 * 全局通用返回结果Vo类
 *
 * @author x1angw@N
 */
@Data
public class ApiResponse<T> {
    // 响应状态码
    private Integer code;
    // 响应信息
    private String message;
    // 响应数据
    private T data;
    // 响应结果
    private Boolean success;

    /**
     * 快速获取一个带有返回数据的表示成功的返回结果
     *
     * @param data 返回数据
     * @param <T>  返回数据的数据类型
     * @return 表示成功带有响应数据的对象
     */
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setCode(200);
        r.setMessage("操作成功");
        r.setData(data);
        r.setSuccess(true);
        return r;
    }

    /**
     * 快速获取一个表示成功的返回结果
     *
     * @return 表示成功的对象
     */
    public static ApiResponse<Void> success() {
        ApiResponse<Void> r = new ApiResponse<>();
        r.setCode(200);
        r.setMessage("操作成功");
        r.setSuccess(true);
        return r;
    }

    /**
     * 快速获取一个表示失败的返回结果
     *
     * @param message 失败的提示信息
     * @return 表示失败的对象
     */
    public static ApiResponse<Void> error(String message) {
        return error(500, message);
    }

    /**
     * 快速获取一个表示失败的返回结果
     *
     * @param code    失败的状态码
     * @param message 失败的提示信息
     * @return 表示失败的对象
     */
    public static ApiResponse<Void> error(Integer code, String message) {
        ApiResponse<Void> r = new ApiResponse<>();
        r.setCode(code);
        r.setMessage(message);
        r.setSuccess(false);
        return r;
    }
}
