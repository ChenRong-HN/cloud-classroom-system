package com.yanque.entity.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于接受前端相关注册参数的类
 *
 * @author cr
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReqVo {
    @Email
    @NotNull(message = "邮箱不能为空")
    private String email;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "邮箱验证码不能为空")
    private String emailCode;
    @NotNull(message = "注册渠道不能为空")
    private Long regChannel;
}
