package com.yanque.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanque.common.constant.PageConstant;
import com.yanque.common.constant.PublishStatusConstant;
import com.yanque.common.constant.SeckillConstant;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.KillActivity;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.yanque.mapper.KillCourseMapper;
import com.yanque.entity.KillCourse;
import com.yanque.service.IKillCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 秒杀课程信息业务层接口实现类
 *
 * @author cr
 */
@Service
public class KillCourseService extends ServiceImpl<KillCourseMapper, KillCourse> implements IKillCourseService {

    // 注入KillCourse持久层接口实现类
    @Resource
    private KillCourseMapper killCourseMapper;

    @Resource
    @Lazy // 防止 KillCourseService 和 illActivityService 循环依赖
    private KillActivityService killActivityService;

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
}
