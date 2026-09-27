package com.yanque.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanque.common.constant.PublishStatusConstant;
import com.yanque.common.constant.RedisConstant;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.KillActivity;
import com.yanque.entity.KillCourse;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.mapper.KillActivityMapper;
import com.yanque.service.IKillActivityService;
import jakarta.annotation.Resource;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 秒杀活动信息业务层接口实现类
 *
 * @author cr
 */
@Service
public class KillActivityService extends ServiceImpl<KillActivityMapper, KillActivity> implements IKillActivityService {

    // 注入KillActivity持久层接口实现类
    @Resource
    private KillActivityMapper killActivityMapper;

    @Resource
    @Lazy // 防止 KillCourseService 和 illActivityService 循环依赖
    private KillCourseService killCourseService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public ApiPageResponse<KillActivity> pagelist(BasicPageVo basicPageVo) {
        // 封装分页条件
        Page<KillActivity> page = new Page<>(basicPageVo.getPage(), basicPageVo.getRows());
        // 封装查询条件
        LambdaQueryWrapper<KillActivity> killActivityLambdaQueryWrapper = Wrappers.<KillActivity>lambdaQuery().like(StrUtil.isNotBlank(basicPageVo.getKeyword()), KillActivity::getName, basicPageVo.getKeyword());
        Page<KillActivity> pageR = page(page, killActivityLambdaQueryWrapper);
        return ApiPageResponse.<KillActivity>builder().rows(pageR.getRecords()).total(pageR.getTotal()).build();
    }

    @Override
    public boolean save(KillActivity killActivity) {
        // 校验1：秒杀活动名称不能与已存在的活动名称重复
        long count = count(Wrappers.<KillActivity>lambdaQuery().eq(KillActivity::getName, killActivity.getName()));
        Assert.isTrue(count == 0L, () -> new BusinessException(BusinessErrorType.KILL_ACTIVITY_EXISTS));

        // 校验2：秒杀的活动开始时间必须早于结束时间
        Assert.isTrue(killActivity.getBeginTime().isBefore(killActivity.getEndTime()), () -> new BusinessException(BusinessErrorType.KILL_ACTIVITY_TIME_ERROR));

        // 校验3：秒杀活动的开始时间必须晚于当前时间
        Assert.isTrue(killActivity.getBeginTime().isAfter(LocalDateTime.now()), () -> new BusinessException(BusinessErrorType.KILL_ACTIVITY_TIME_ERROR));

        // 数据填充（时间字符串、发布状态）
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
        String timeStr = killActivity.getBeginTime().format(dateTimeFormatter);
        killActivity.setTimeStr(timeStr);
        killActivity.setPublishStatus(PublishStatusConstant.PUBLISH_STATUS_WAIT);

        return super.save(killActivity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(Long killActivityId) {
        // 校验是否存在相同名称的活动
        KillActivity killActivity = getById(killActivityId);
        Assert.notNull(killActivity, () -> new BusinessException(BusinessErrorType.KILL_ACTIVITY_NOT_EXISTS));
        // 校验当前状态是否可发布
        Assert.isTrue(killActivity.getPublishStatus() == 0L, () -> new BusinessException(BusinessErrorType.KILL_ACTIVITY_STATUS_ERROR));
        List<KillCourse> killCourseList = killCourseService.list(Wrappers.<KillCourse>lambdaQuery().eq(KillCourse::getActivityId, killActivityId));
        // 校验秒杀活动下是否有课程信息
        Assert.isTrue(ObjUtil.isNotEmpty(killCourseList), () -> new BusinessException(BusinessErrorType.KILL_ACTIVITY_COURSE_NOT_EXISTS));
        // 更新秒杀活动信息
        killActivity.setPublishStatus(PublishStatusConstant.PUBLISH_STATUS_SUCCESS);
        killActivity.setPublishTime(LocalDateTime.now());
        updateById(killActivity);
        // 更新秒杀课程信息
        for (KillCourse killCourse : killCourseList) {
            killCourse.setPublishStatus(1L);
            killCourse.setPublishTime(LocalDateTime.now());
            killCourseService.updateById(killCourse);
        }
        // 将本次活动缓存到redis中
        redisTemplate.opsForHash().put(RedisConstant.KILL_ACTIVITY_KEY, killActivityId.toString(), JSONUtil.toJsonStr(killActivity));
        String killActivityCourseKey = RedisConstant.KILL_ACTIVITY_COURSE_KEY.concat(killActivityId.toString());
        // 缓存活动中的课程信息
        for (KillCourse killCourse : killCourseList) {
            redisTemplate.opsForHash().put(killActivityCourseKey, killCourse.getCourseId().toString(), JSONUtil.toJsonStr(killCourse));
        }
        // 完成课程库存在缓存中的预热
        ArrayList<String> alreadyExistSemaphoreKeyList = new ArrayList<>();
        for (KillCourse killCourse : killCourseList) {
            String killCourseStockSemaphoreKey = String.format(RedisConstant.KILL_ACTIVITY_COURSE_STOCK_SEMAPHORE_KEY, killActivity, killCourse.getCourseId());
            RSemaphore semaphore = redissonClient.getSemaphore(killCourseStockSemaphoreKey);
            // 设置许可证数量
            boolean r = semaphore.trySetPermits(Integer.parseInt(String.valueOf(killCourse.getKillCount())));
            // 设置失败，说明redis中有同名key的数据
            if (!r) {
                // 删除该数据
                semaphore.delete();
                // 重新设置许可证数量
                boolean rr = semaphore.trySetPermits(Integer.parseInt(String.valueOf(killCourse.getKillCount())));
                if (!rr) {
                    // 回滚本次操作的全部数据
                    // 删除redis中的秒杀活动信息
                    redisTemplate.opsForHash().delete(RedisConstant.KILL_ACTIVITY_KEY, killActivityId.toString());
                    // 删除redis中的活动课程信息
                    redisTemplate.delete(killActivityCourseKey);
                    // 删除已经设置好的信号量
                    for (String alreadyExistSemaphoreKey : alreadyExistSemaphoreKeyList) {
                        redissonClient.getSemaphore(alreadyExistSemaphoreKey).delete();
                    }
                    // 上面操作完成redis回滚，然后抛出异常让数据库回滚
                    throw new BusinessException(BusinessErrorType.KILL_ACTIVITY_PUBLISH_ERROR);
                }
            }
            // 信号量添加成功，则将key保存到list中
            alreadyExistSemaphoreKeyList.add(killCourseStockSemaphoreKey);
        }
    }
}
