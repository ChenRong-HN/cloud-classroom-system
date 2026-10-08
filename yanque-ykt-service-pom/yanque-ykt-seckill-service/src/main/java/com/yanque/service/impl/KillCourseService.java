package com.yanque.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.*;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanque.common.constant.PageConstant;
import com.yanque.common.constant.PublishStatusConstant;
import com.yanque.common.constant.RedisConstant;
import com.yanque.common.constant.SeckillConstant;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.KillActivity;
import com.yanque.entity.KillCourse;
import com.yanque.entity.vo.KillCourseRespVo;
import com.yanque.entity.vo.KillReqVo;
import com.yanque.entity.vo.PreSeckillOrderVo;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.mapper.KillCourseMapper;
import com.yanque.service.IKillCourseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀课程信息业务层接口实现类
 *
 * @author cr
 */
@Service
@Slf4j
public class KillCourseService extends ServiceImpl<KillCourseMapper, KillCourse> implements IKillCourseService {

    // 注入KillCourse持久层接口实现类
    @Resource
    private KillCourseMapper killCourseMapper;

    @Resource
    @Lazy // 防止 KillCourseService 和 illActivityService 循环依赖
    private KillActivityService killActivityService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public ApiPageResponse<KillCourse> pagelist(BasicPageVo basicPageVo) {
        if (ObjUtil.isNull(basicPageVo.getRows()))
            basicPageVo.setRows(PageConstant.DEFAULT_PAGE_DATA_COUNT);
        // 封装分页条件
        Page<KillCourse> page = new Page<>(basicPageVo.getPage(), basicPageVo.getRows());
        // 封装查询条件
        LambdaQueryWrapper<KillCourse> KillCourseLambdaQueryWrapper = Wrappers.<KillCourse>lambdaQuery().like(StrUtil.isNotBlank(basicPageVo.getKeyword()), KillCourse::getCourseName, basicPageVo.getKeyword());
        Page<KillCourse> pageR = page(page, KillCourseLambdaQueryWrapper);
        return ApiPageResponse.<KillCourse>builder().rows(pageR.getRecords()).total(pageR.getTotal()).build();
    }

    @Override
    public boolean save(KillCourse killCourse) {

        // 校验:对应秒杀活动的Id数据是否存在
        KillActivity killActivity = killActivityService.getById(killCourse.getActivityId());
        Assert.notNull(killActivity, () -> new BusinessException(BusinessErrorType.KILL_ACTIVITY_NOT_EXISTS));

        // 字段填充
        killCourse.setKillLimit(SeckillConstant.DEFAULT_KILL_LIMIT); // 默认秒杀数量
        killCourse.setKillSort(SeckillConstant.DEFAULT_KILL_SORT); // 默认秒杀排序字段
        killCourse.setPublishStatus(PublishStatusConstant.PUBLISH_STATUS_WAIT); // 待发布状态
        killCourse.setStartTime(killActivity.getBeginTime()); // 秒杀开始时间
        killCourse.setEndTime(killActivity.getEndTime()); // 秒杀结束时间
        killCourse.setCreateTime(LocalDateTime.now()); // 创建时间
        killCourse.setTimeStr(killActivity.getTimeStr()); // 开始时间字符串

        return super.save(killCourse);
    }

    @Override
    public KillCourseRespVo selectKillCourseRespVo(Long killCourseId) {
        // 获取原始秒杀课程信息
        KillCourse killCourse = killCourseMapper.selectById(killCourseId);
        Assert.notNull(killCourse, () -> new BusinessException(BusinessErrorType.KILL_COURSE_NOT_EXISTS));
        KillCourseRespVo killCourseRespVo = BeanUtil.copyProperties(killCourse, KillCourseRespVo.class);

        // 封装是否正在秒杀、是否未开始秒杀、时间差
        LocalDateTime now = LocalDateTime.now();
        if (!Objects.equals(killCourseRespVo.getPublishStatus(), PublishStatusConstant.PUBLISH_STATUS_SUCCESS) || now.isBefore(killCourseRespVo.getStartTime())) {
            // 情况1：未发布或者已发布但是秒杀未开始
            killCourseRespVo.setKilling(false); // 设置正在秒杀
            killCourseRespVo.setUnbegin(true); // 设置未开始秒杀
            killCourseRespVo.setTimeDiffMill(LocalDateTimeUtil.between(now, killCourse.getStartTime(), ChronoUnit.SECONDS)); // 设置时间差
        } else if (now.isAfter(killCourseRespVo.getStartTime()) && now.isBefore(killCourseRespVo.getEndTime())) {
            // 情况2：已发布并且秒杀进行中
            killCourseRespVo.setKilling(true); // 设置正在秒杀
            killCourseRespVo.setUnbegin(false); // 设置未开始秒杀
            killCourseRespVo.setTimeDiffMill(LocalDateTimeUtil.between(now, killCourseRespVo.getEndTime(), ChronoUnit.SECONDS)); // 设置时间差
        } else {
            // 情况3：已发布但是秒杀已经结束
            killCourseRespVo.setKilling(false); // 设置正在秒杀
            killCourseRespVo.setUnbegin(false); // 设置未开始秒杀
            killCourseRespVo.setTimeDiffMill(LocalDateTimeUtil.between(now, killCourseRespVo.getEndTime(), ChronoUnit.SECONDS)); // 设置时间差
        }

        return killCourseRespVo;
    }

    @Override
    public String killCourse(KillReqVo killReqVo) {
        // 模拟用户id
        Long userId = 5L;
        Long killActivityId = killReqVo.getKillActivityId();
        Long killCourseId = killReqVo.getKillCourseId();
        // 校验1：秒杀课程是否存在
        KillCourse killCourse = getById(killCourseId);
        Assert.notNull(killCourse, () -> new BusinessException(BusinessErrorType.KILL_COURSE_NOT_EXISTS));

        // 校验2：秒杀活动与秒杀课程是否匹配
        Assert.isTrue(killCourse.getActivityId().equals(killActivityId), () -> new BusinessException(BusinessErrorType.KILL_ACTIVITY_NOT_MATCH));

        // 校验3：秒杀活动状态和秒杀课程状态
        Assert.isTrue(killCourse.getPublishStatus().equals(PublishStatusConstant.PUBLISH_STATUS_SUCCESS), () -> new BusinessException(BusinessErrorType.KILL_ACTIVITY_STATUS_ERROR));
        Assert.isTrue(killCourse.getStartTime().isBefore(LocalDateTime.now()), () -> new BusinessException(BusinessErrorType.KILL_COURSE_NOT_START_ERROR));
        Assert.isTrue(killCourse.getEndTime().isAfter(LocalDateTime.now()), () -> new BusinessException(BusinessErrorType.KILL_COURSE_HAS_END_ERROR));

        // 保存用户开启了秒杀的flag，用于防止重复提交
        String userKillFlagKey = String.format(RedisConstant.USER_KILL_KEY, killActivityId, killCourseId, userId);
        // setnx 命令实现分布式锁，用于设置一个键值对，如果键不存在则设置成功，如果键存在则设置失败
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(userKillFlagKey, "LOCKED", 5, TimeUnit.MINUTES);
        Assert.isTrue(BooleanUtil.isTrue(locked), () -> new BusinessException(BusinessErrorType.KILL_COURSE_REPEAT_SUBMIT_ERROR));

        // 校验完成后可以进行秒杀，可以通过随机布尔值来排除一部分秒杀
        boolean canKill = RandomUtil.randomBoolean();
        Assert.isTrue(canKill, () -> new BusinessException(BusinessErrorType.KILL_COURSE_ERROR));

        // 获取秒杀课程的库存信号量key
        String killActivityCourseStockSemaphoreKey = String.format(RedisConstant.KILL_ACTIVITY_COURSE_STOCK_SEMAPHORE_KEY, killActivityId, killCourseId);
        RSemaphore semaphore = redissonClient.getSemaphore(killActivityCourseStockSemaphoreKey);
        // 尝试获取库存信号量
        boolean isAcquired = semaphore.tryAcquire();
        // 如果未获取到信号量说明已经被其他用户获取,结束秒杀
        if (!isAcquired) {
            redisTemplate.delete(userKillFlagKey);
            throw new BusinessException(BusinessErrorType.KILL_COURSE_STOCK_ERROR);
        }

        // 获取到信号量继续进行秒杀（预生成订单号）
        try {
            String orderNo = IdUtil.getSnowflakeNextIdStr();
            PreSeckillOrderVo preSeckillOrderVo = PreSeckillOrderVo.builder()
                    .activityId(killActivityId)
                    .killCourseId(killCourseId)
                    .originCourseId(killCourseId)
                    .killPrice(killCourse.getKillPrice())
                    .coursePic(killCourse.getCoursePic())
                    .quantity(1L)
                    .orderNo(orderNo)
                    .userId(userId)
                    .build();

            // 封装预秒杀订单key
            String redisPreKillOrderKey = String.format(RedisConstant.PRE_SECKILL_ORDER, userId, orderNo);
            redisTemplate.opsForValue().set(redisPreKillOrderKey, JSONUtil.toJsonStr(preSeckillOrderVo), 5, TimeUnit.MINUTES);
            // 返回预秒杀订单编号
            return orderNo;
        } catch (Exception e) {
            log.error("秒杀课程出现异常,错误原因 {}", e.getMessage());
            semaphore.release(); // 释放信号量
            redisTemplate.delete(userKillFlagKey); // 删除用户开始秒杀的🔒Key
            throw e; // 继续抛出异常
        }
    }
}
