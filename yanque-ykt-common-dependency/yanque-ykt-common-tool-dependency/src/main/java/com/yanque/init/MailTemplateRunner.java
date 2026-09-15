package com.yanque.init;

import com.yanque.common.constant.RedisConstant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 邮件模板初始化，随着引入该模块的项目的ioc容器启动而运行，执行邮件模板数据初始化
 *
 * @author cr
 */
@Slf4j
@Component
public class MailTemplateRunner implements CommandLineRunner {

    // 注入Redis操作模板Bean
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 当IOC容器初始化完毕,自动执行该方法
     */
    @Override
    public void run(String... args) throws Exception {
        // 存储邮件模板数据到Redis
        redisTemplate.opsForValue().set(RedisConstant.MAIL_REGISTRY_CODE_TEMPLATE_KEY, """
                <!DOCTYPE html>
                <html lang="zh-CN">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>验证码</title>
                </head>
                <body style="margin:0; padding:0; background-color:#f4f6f9; font-family:-apple-system,BlinkMacSystemFont,'Segoe UI','PingFang SC','Hiragino Sans GB','Microsoft YaHei',sans-serif;">
                
                <!-- 外层背景 -->
                <table role="presentation" width="100%" cellpadding="0" cellspacing="0" border="0"
                       style="background-color:#f4f6f9; padding:40px 16px;">
                    <tr>
                        <td align="center">
                
                            <!-- 主卡片 -->
                            <table role="presentation" width="600" cellpadding="0" cellspacing="0" border="0"
                                   style="max-width:600px; width:100%; background-color:#ffffff; border-radius:12px;
                                              overflow:hidden; box-shadow:0 4px 16px rgba(0,0,0,0.06);">
                
                                <!-- 顶部渐变条 -->
                                <tr>
                                    <td style="height:6px; background:linear-gradient(90deg,#4f7cff 0%,#7b5cff 50%,#b14cff 100%);
                                                   background-color:#4f7cff; line-height:6px; font-size:0;">&nbsp;</td>
                                </tr>
                
                                <!-- 品牌区 -->
                                <tr>
                                    <td style="padding:36px 40px 8px 40px;">
                                        <table role="presentation" width="100%" cellpadding="0" cellspacing="0" border="0">
                                            <tr>
                                                <td style="font-size:18px; font-weight:600; color:#1f2d3d; letter-spacing:0.5px;">
                                                    Yanque 云课堂
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                
                                <!-- 标题 -->
                                <tr>
                                    <td style="padding:8px 40px 0 40px;">
                                        <h1 style="margin:0; font-size:22px; font-weight:600; color:#1f2d3d; line-height:1.4;">
                                            邮箱验证码
                                        </h1>
                                    </td>
                                </tr>
                
                                <!-- 说明文字 -->
                                <tr>
                                    <td style="padding:16px 40px 0 40px;">
                                        <p style="margin:0; font-size:14px; color:#5a6b7c; line-height:1.7;">
                                            您好，您正在申请邮箱验证。请在页面中输入以下验证码完成操作：
                                        </p>
                                    </td>
                                </tr>
                
                                <!-- 验证码区域 -->
                                <tr>
                                    <td style="padding:28px 40px 8px 40px;">
                                        <table role="presentation" width="100%" cellpadding="0" cellspacing="0" border="0">
                                            <tr>
                                                <td align="center"
                                                    style="background-color:#f0f4ff; border:1px dashed #b8c8ff;
                                                               border-radius:10px; padding:22px 16px;">
                                                        <span style="font-size:34px; font-weight:700; letter-spacing:10px;
                                                                     color:#3b5bdb; font-family:'Courier New',Courier,monospace;
                                                                     display:inline-block; padding-left:10px;">
                                                            {{code}}
                                                        </span>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <!-- 有效期提示 -->
                                <tr>
                                    <td style="padding:14px 40px 0 40px;">
                                        <p style="margin:0; font-size:13px; color:#8a99a8; line-height:1.6;">
                                            验证码 <strong style="color:#e8590c;">{{expireMinutes}} 分钟</strong> 内有效，请勿泄露给他人。
                                        </p>
                                    </td>
                                </tr>
                
                                <!-- 分割线 -->
                                <tr>
                                    <td style="padding:28px 40px 0 40px;">
                                        <div style="height:1px; background-color:#eef1f5; line-height:1px; font-size:0;">&nbsp;</div>
                                    </td>
                                </tr>
                
                                <!-- 安全提示 -->
                                <tr>
                                    <td style="padding:20px 40px 0 40px;">
                                        <p style="margin:0; font-size:13px; color:#8a99a8; line-height:1.7;">
                                            如果这不是您本人的操作，请忽略此邮件，您的账号不会被影响。
                                        </p>
                                    </td>
                                </tr>
                
                                <!-- 底部留白 -->
                                <tr>
                                    <td style="padding:32px 40px 0 40px;">&nbsp;</td>
                                </tr>
                
                            </table>
                            <!-- /主卡片 -->
                
                            <!-- 页脚 -->
                            <table role="presentation" width="600" cellpadding="0" cellspacing="0" border="0"
                                   style="max-width:600px; width:100%;">
                                <tr>
                                    <td style="padding:24px 16px 8px 16px; text-align:center;">
                                        <p style="margin:0; font-size:12px; color:#9aa7b4; line-height:1.8;">
                                            此邮件由系统自动发送，请勿直接回复。<br>
                                            © 2026 燕雀云课堂 · 保留所有权利
                                        </p>
                                    </td>
                                </tr>
                            </table>
                
                        </td>
                    </tr>
                </table>
                
                </body>
                </html>
                """);
        redisTemplate.opsForValue().set(RedisConstant.MAIL_REGISTRY_CODE_SUBJECT_KEY, RedisConstant.MAIL_REGISTRY_CODE_DEFAULT_SUBJECT_VALUE);
        redisTemplate.opsForValue().set(RedisConstant.MAIL_REGISTRY_CODE_EXPIRE_MINUTES_KEY, RedisConstant.MAIL_REGISTRY_CODE_DEFAULT_EXPIRE_MINUTES_VALUE);
        log.info("邮件模板数据初始化完成");
    }
}
