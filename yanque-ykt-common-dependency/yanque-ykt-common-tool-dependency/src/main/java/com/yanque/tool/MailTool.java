package com.yanque.tool;

import cn.hutool.extra.mail.MailUtil;
import com.yanque.common.constant.RedisConstant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * 发送邮件的工具类
 *
 * @author cr
 */
@Component
@Slf4j
public class MailTool {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 给邮箱发送注册验证码邮件
     * @param targetMail 目标邮箱
     * @return 验证码
     */
    public String sendRegistryValidateCodeMail(String targetMail) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String expireMinutes = String.valueOf(valueOperations.get(RedisConstant.MAIL_REGISTRY_CODE_EXPIRE_MINUTES_KEY));
        String mailSubject = String.valueOf(valueOperations.get(RedisConstant.MAIL_REGISTRY_CODE_SUBJECT_KEY));
        String mailContent = String.valueOf(valueOperations.get(RedisConstant.MAIL_REGISTRY_CODE_TEMPLATE_KEY));
        // 生成六位数的验证码
        String validateCode = ValidateCodeGenerateTool.generateValidateCode(6);
        // 替换邮件模板中的验证码和过期时间
        mailContent = mailContent.replace("{{code}}",validateCode).replace("{{expireMinutes}}",expireMinutes);

        // 通过hutool提供的发送邮件工具类发送邮件
//        String messageId = MailUtil.send(targetMail, mailSubject, mailContent, true);
        log.info("发送给 {} 的注册验证码邮件成功,验证码 {}", targetMail, validateCode);
        return validateCode;
    }

}
