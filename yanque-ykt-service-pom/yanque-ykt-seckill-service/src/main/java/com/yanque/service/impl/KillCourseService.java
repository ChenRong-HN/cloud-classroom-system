package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.KillCourseMapper;
import com.yanque.entity.KillCourse;
import com.yanque.service.IKillCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 秒杀课程信息业务层接口实现类
 *
 * @author x1angwan
 */
@Service
public class KillCourseService extends ServiceImpl<KillCourseMapper,KillCourse> implements IKillCourseService {

    // 注入KillCourse持久层接口实现类
    @Resource
    private KillCourseMapper killCourseMapper;

}
