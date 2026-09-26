package com.yanque.tool;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.yanque.entity.AlipayInfo;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.service.IAlipayInfoService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 支付宝支付工具类（容器启动读取，也可以参考<a href="https://gitee.com/b0w3n/yanque-ykt-pom/blob/master/yanque-ykt-service-pom/yanque-ykt-pay-service/src/main/java/com/yanque/tool/AliPayTool.java">...</a>实时读取）
 *
 * @author cr
 */
@Slf4j
@Component
public class AliPayTool {

    // 注入支付宝参数信息服务
    @Resource
    private IAlipayInfoService alipayInfoService;

    @Value("${default.pay.expire}")
    private Integer defaultPayExpireValue;

    // 支付宝参数对象
    private AlipayConfig alipayConfig;
    // 支付宝参数信息对象(本地)
    private AlipayInfo alipayInfo;

    @PostConstruct // 随容器启动（只）执行一次
    public void initAliPayConfig() {
        alipayInfo = alipayInfoService.getById(1L);
        alipayConfig = new AlipayConfig();
        alipayConfig.setAppId(alipayInfo.getAppId()); // 应用Id
        alipayConfig.setServerUrl(alipayInfo.getGatewayHost()); // 支付宝网关
        alipayConfig.setPrivateKey(alipayInfo.getMerchantPrivateKey()); // 应用私钥
        alipayConfig.setAlipayPublicKey(alipayInfo.getAlipayPublicKey()); // 支付宝公钥
        alipayConfig.setSignType(alipayInfo.getSignType()); // 签名类型
        alipayConfig.setCharset("UTF-8"); // 字符集
        alipayConfig.setFormat("json"); // 数据格式
    }

    /**
     * 创建支付请求
     *
     * @param tradeNumber 订单号
     * @param totalAmount 订单金额
     * @param subject     订单标题
     * @param returnURL   回调地址
     * @return 支付页面Html、JavaScript代码
     */
    public String createPayRequest(String tradeNumber, String totalAmount, String subject, String returnURL) throws AlipayApiException {
        // 初始化支付宝支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);

        // 构造请求参数用于调用接口
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();

        // 声明商户订单号
        model.setOutTradeNo(tradeNumber);
        // 声明订单总金额
        model.setTotalAmount(totalAmount);
        // 声明订单标题
        model.setSubject(subject);
        // 声明产品码
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        // 声明支付请求过期时间(超期未支付则支付宝自动关单) 30分钟内不支付支付宝自动关单（推荐使用TimeExpire，两者共存时，TimeExpire优先）
        // model.setTimeoutExpress("30m");
        // 声明支付超时时间
        model.setTimeExpire(DateUtil.formatLocalDateTime(LocalDateTime.now().plusMinutes(defaultPayExpireValue)));
        // 声明请求参数
        request.setBizModel(model);
        // 📌 声明同步回调
        request.setReturnUrl(ObjUtil.isNull(returnURL) ? alipayInfo.getReturnUrl() : returnURL);
        // 📌 声明异步回调
        request.setNotifyUrl(alipayInfo.getNotifyUrl());

        AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "POST");
        String pageRedirectionData = response.getBody();

        // 📌 此处会返回一段Html/JavaScript代码,用于页面跳转支付
        if (response.isSuccess()) {
            log.info("支付宝请求支付调用成功 订单Id {} , 订单金额 {} , 订单标题 {} , 回调地址 {}", tradeNumber, totalAmount, subject, returnURL);
            return pageRedirectionData;
        } else {
            log.error("支付宝请求支付调用失败 失败状态码 {}", response.getCode());
            throw new BusinessException(BusinessErrorType.ALI_PAY_REQUEST_ERROR);
        }
    }
}
