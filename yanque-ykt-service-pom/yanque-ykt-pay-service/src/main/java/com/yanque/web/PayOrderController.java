package com.yanque.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.yanque.common.vo.ApiResponse;
import com.yanque.entity.vo.AliPayNotifyRepVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yanque.entity.PayOrder;
import com.yanque.service.IPayOrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付订单控制层接口
 *
 * @author cr
 */
@Tag(name = "支付订单管理", description = "支付订单接口")
@RestController
@RequestMapping("/pay/payOrder")
@Slf4j
public class PayOrderController {

    // 注入支付订单服务层接口实现类
    @Resource
    private IPayOrderService payOrderService;

    /**
     * 查询支付订单列表
     *
     * @return 全局通用返回结果(支付订单所有数据})
     */
    @Operation(summary = "查询支付订单列表", description = "查询所有支付订单信息列表")
    @GetMapping("/list")
    public ApiResponse<List<PayOrder>> list() {
        // 调用支付订单服务层接口查询所有支付订单信息
        List<PayOrder> list = payOrderService.list();
        return ApiResponse.success(list);
    }

    /**
     * 根据id查询支付订单
     *
     * @param id 支付订单主键Id
     * @return 全局通用返回结果(支付订单实体数据)
     */
    @Operation(summary = "根据Id查询支付订单", description = "根据主键Id查询支付订单详细信息")
    @GetMapping("/{id}")
    public ApiResponse<PayOrder> getById(@Parameter(description = "支付订单Id") @PathVariable("id") Long id) {
        // 调用支付订单服务层接口根据Id查询支付订单信息
        PayOrder entity = payOrderService.getById(id);
        return ApiResponse.success(entity);
    }

    /**
     * 新增支付订单
     *
     * @param payOrder 支付订单实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "新增支付订单", description = "新增支付订单信息")
    @PostMapping
    public ApiResponse<Void> save(@RequestBody PayOrder payOrder) {
        // 调用支付订单服务层接口保存支付订单信息
        payOrderService.save(payOrder);
        return ApiResponse.success();
    }

    /**
     * 修改支付订单
     *
     * @param payOrder 支付订单实体对象
     * @return 全局通用返回结果
     */
    @Operation(summary = "修改支付订单", description = "修改支付订单信息")
    @PutMapping
    public ApiResponse<Void> update(@RequestBody PayOrder payOrder) {
        // 调用支付订单服务层接口修改支付订单信息
        payOrderService.updateById(payOrder);
        return ApiResponse.success();
    }

    /**
     * 基于主键Id删除支付订单
     *
     * @param id 支付订单主键
     * @return 全局通用返回结果
     */
    @Operation(summary = "删除支付订单", description = "根据Id删除支付订单信息")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@Parameter(description = "支付订单Id") @PathVariable("id") Long id) {
        // 调用支付订单服务层接口根据Id删除支付订单信息
        payOrderService.removeById(id);
        return ApiResponse.success();
    }

    /**
     * 处理支付宝异步回调通知（完成支付操作后由支付宝发起请求）
     * 正常情况返回字符串success表示成功
     * 📌 请求参数不是在请求体中的Json,而是在表单中KEY=VALUE的方式提交,因此不需要加@RequestBody
     *
     * @param aliPayNotifyRepVo 将支付宝回调请求的请求参数封装到aliPayNotifyRepVo对象中(aliPayNotifyRepVo中的字段和请求参数的Key是一一对应的)
     */
    @PostMapping("/notify")
    @Operation(summary = "支付订单通知", description = "支付订单通知")
    public String alipayNotify(AliPayNotifyRepVo aliPayNotifyRepVo) {
        try {
            Map<String, String> paramterMap = JSONUtil.toBean(JSONUtil.toJsonStr(aliPayNotifyRepVo), Map.class);
            // 基于官网提供的验签工具进行验签
            boolean r = AlipaySignature.rsaCertCheckV1(paramterMap,
                    "支付宝公钥",
                    "UTF-8",
                    "RSA2"
            );
            log.info("支付宝异步通知签名验证结果：{}，用户支付成功的订单号：{}，用户支付金额：{}元", r, aliPayNotifyRepVo.getOut_trade_no(), aliPayNotifyRepVo.getTotal_amount());
            // 支付宝只关心返回的结果是不是”success“，不是就表示失败，支付宝会继续回调；success代表成功，支付宝不会继续回调
            return r ? "success" : "fail";
        } catch (AlipayApiException e) {
            log.error("支付宝异步通知签名验证失败，失败原因：{}", e.getMessage());
            return "fail";
        }
    }
}