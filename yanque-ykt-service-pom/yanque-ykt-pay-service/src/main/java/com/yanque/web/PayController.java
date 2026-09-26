package com.yanque.web;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yanque.common.constant.PayTypeConstant;
import com.yanque.common.constant.RocketMQConstant;
import com.yanque.common.vo.ApiResponse;
import com.yanque.entity.PayOrder;
import com.yanque.entity.vo.AliPayNotifyReqVo;
import com.yanque.entity.vo.PayReqVo;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.service.IPayOrderService;
import com.yanque.service.IPayService;
import com.yanque.tool.AliPayTool;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 支付服务控制层接口
 *
 * @author cr
 */
@RestController
@Slf4j
@RequestMapping("/pay/pay")
public class PayController {

    @Resource
    private IPayService payService;

    @Resource
    private IPayOrderService payOrderService;

    @Resource
    private AliPayTool aliPayTool;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 根据订单编号检查支付订单是否存在
     *
     * @param orderNumber 订单编号
     * @return 全局通用返回结果（是否存在支付订单数据）
     */
    @Operation(summary = "检查支付订单是否存在", description = "根据订单编号检查支付订单是否存在")
    @GetMapping("/checkPayOrder/{orderNumber}")
    public ApiResponse<Boolean> checkPayOrder(@PathVariable String orderNumber) {
        long count = payOrderService.count(Wrappers.<PayOrder>lambdaQuery().eq(PayOrder::getOrderNo, orderNumber));
        return ApiResponse.success(count == 1L);
    }

    /**
     * 发起支付请求
     *
     * @param payReqVo 订单编号
     * @return 全局通用返回结果（支付请求html代码片段）
     */
    @Operation(summary = "支付请求", description = "根据订单信息支付请求")
    @PostMapping("/apply")
    public ApiResponse<String> applyPay(@RequestBody PayReqVo payReqVo) throws AlipayApiException {
        // 检验支付方式
        Assert.equals(payReqVo.getPayType(), PayTypeConstant.ALIPAY, () -> new BusinessException(BusinessErrorType.PAY_TYPE_ERROR));
        // 获取支付订单信息
        PayOrder payOrder = payOrderService.getOne(Wrappers.<PayOrder>lambdaQuery().eq(PayOrder::getOrderNo, payReqVo.getOrderNo()));
        Assert.notNull(payOrder, () -> new BusinessException(BusinessErrorType.PAY_ORDER_NOT_FOUND));
        // 发起支付请求
        String htmlData = aliPayTool.createPayRequest(
                payOrder.getOrderNo(),
                String.valueOf(payOrder.getAmount()),
                payOrder.getSubject(),
                StrUtil.isBlank(payReqVo.getCallUrl()) ? null : payReqVo.getCallUrl()
        );
        return ApiResponse.success(htmlData);
    }

    /**
     * 处理支付宝异步回调通知（完成支付操作后由支付宝发起请求）
     * 正常情况返回字符串success表示成功
     * 📌 请求参数不是在请求体中的Json,而是在表单中KEY=VALUE的方式提交,因此不需要加@RequestBody
     *
     * @param aliPayNotifyReqVo 将支付宝回调请求的请求参数封装到aliPayNotifyRepVo对象中(aliPayNotifyRepVo中的字段和请求参数的Key是一一对应的)
     */
    @PostMapping("/notify")
    @Operation(summary = "处理支付宝异步通知", description = "处理支付宝异步通知")
    public String aliPayNotify(AliPayNotifyReqVo aliPayNotifyReqVo) {
        log.warn("接收到支付宝服务器发来的异步回调请求、开始处理异步回调请求、回调请求的数据 {}", aliPayNotifyReqVo);
        // 支付宝验证签名的目的是明确本地请求是支付宝官方发起的请求,而不是别人模拟的请求 核心逻辑:对请求参数中的sign进行解密 sign是支付宝官方提供私钥加密的,本地保存了支付宝公钥
        try {
            Map<String, String> parameterMap = JSONUtil.toBean(JSONUtil.toJsonStr(aliPayNotifyReqVo), Map.class);
            // 基于官网提供的验签工具进行签名验证
            boolean r = AlipaySignature.rsaCheckV1(parameterMap,
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjdlzlIwcbuDAJlZzg1l6KO5wQxDtLEbjYz71kSyTDxzIryr02l6v8gjHKmCzsM3bmqKsR3uC8uBxxKTIS55z+dGavo1X49t6gnUL/InVWzirGi1YlX20Elo69vqua5V4yujCwMz+Piea95dGMYN2+yHLPFKrQcu0RKSTsLlmEc21d+3joWCwiVvKMO94o7iTqyyJ9x7BNw+NJMkz/NFSsjiCj8u7FHVcEPoP3NMJMIXfpb2jao3sMMKUgmadQFb5H2o2j4DkujkvCvU+OmIGAsqZTJBksf32K9TWUESoU6lgNQgTwkQhCf0zag/V0B5jqbnGekoNbL0BbFwlO7T4+wIDAQAB",
                    "UTF-8",
                    "RSA2");
            log.info("支付宝异步通知签名验证结果 {} , 用户支付成功的订单号 {} , 用户支付金额 {}", r, aliPayNotifyReqVo.getOut_trade_no(), aliPayNotifyReqVo.getTotal_amount());
            // 如果签名验证通过 说明本次的异步回调是支付宝发起 进行事务消息的发送
            if (r) {
                // 发送事务消息
                Message<String> message = MessageBuilder.withPayload(aliPayNotifyReqVo.getOut_trade_no()).build();
                rocketMQTemplate.sendMessageInTransaction(RocketMQConstant.buildDestination(RocketMQConstant.ORDER_STATUS_TOPIC, RocketMQConstant.ORDER_STATUS_PAY_TAG)
                        , message, aliPayNotifyReqVo);
                log.info("支付宝异步回调的订单 {} 状态修改的事务消息已发送", aliPayNotifyReqVo.getOut_trade_no());
                return "success";
            }
            return "fail";
        } catch (AlipayApiException e) {
            log.error("支付宝异步通知签名验证失败 失败原因 {}", e.getMessage());
            return "fail";
        }
    }
}
