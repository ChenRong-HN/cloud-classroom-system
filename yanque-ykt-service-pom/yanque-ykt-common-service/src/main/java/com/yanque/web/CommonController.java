package com.yanque.web;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.extra.mail.Mail;
import com.yanque.common.constant.RedisConstant;
import com.yanque.common.vo.ApiResponse;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.tool.MailTool;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

/**
 * 通用微服务的通用控制层
 *
 * @author cr
 */
@RestController
@Slf4j
@RequestMapping("/common/verifycode")
public class CommonController {

    @Resource
    private MailTool mailTool;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @GetMapping("/sendEmailRegisterVerifyCode/{targetEmail}")
    public ApiResponse<Void> sendEmailRegisterVerifyCode(@PathVariable String targetEmail) {
        // 判断今日邮件发送次数
        String sendCountKey = RedisConstant.MAIL_TODAY_SEND_COUNT_KEY.replace("{today}",
                LocalDateTimeUtil.format(LocalDate.now(), "yyyy-MM-dd")).concat(targetEmail);
        Integer sendCountValue = (Integer) redisTemplate.opsForValue().get(sendCountKey);

        // 若今日发送次数低于3次
        if (ObjUtil.isNull(sendCountValue) || sendCountValue < 3) {
            // 继续判断发送间隔时间是否小于一分钟
            if (redisTemplate.hasKey(RedisConstant.MAIL_LAST_SEND_FLAG_KEY.concat(targetEmail))) {
                throw new BusinessException(BusinessErrorType.MAIL_LAST_SEND_IN_1MIN);
            } else {
                // 记录本次发送邮件的标志
                // 唯一的作用：通过尝试获取判断是否为null判断是否过期
                redisTemplate.opsForValue().set(RedisConstant.MAIL_LAST_SEND_FLAG_KEY.concat(targetEmail), System.currentTimeMillis(), 1, TimeUnit.MINUTES);
                // 记录今日发送次数
                if (ObjUtil.isNull(redisTemplate.opsForValue().get(sendCountKey))) {
                    // 今日第一次发送
                    redisTemplate.opsForValue().set(sendCountKey, 1);
                } else {
                    // 不是今日第一次发送，则value自增
                    redisTemplate.opsForValue().increment(sendCountKey);
                }
                // 发送验证码
                String validateCode = mailTool.sendRegistryValidateCodeMail(targetEmail);
                log.info("发送邮件验证码成功,目标邮箱 {}, 验证码 {}", targetEmail, validateCode);
                redisTemplate.opsForValue().set(RedisConstant.MAIL_REGISTRY_CODE_KEY.concat(targetEmail), validateCode, 1, TimeUnit.MINUTES);
            }
        } else
            // 抛出异常
            throw new BusinessException(BusinessErrorType.MAIL_SEND_COUNT_GT3);
        return ApiResponse.success();
    }
}
