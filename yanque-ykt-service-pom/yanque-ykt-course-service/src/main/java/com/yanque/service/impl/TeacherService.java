package com.yanque.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.TeacherMapper;
import com.yanque.entity.Teacher;
import com.yanque.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 老师业务层接口实现类
 *
 * @author cr
 */
@Service
public class TeacherService extends ServiceImpl<TeacherMapper,Teacher> implements ITeacherService {

    // 注入Teacher持久层接口实现类
    @Resource
    private TeacherMapper teacherMapper;

}
